package com.zuul.gateway.filters;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.zuul.gateway.feign.AuthFeignClient;
import com.zuul.gateway.model.User;
import com.zuul.gateway.model.UserWithToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Component
public class PreRequestLogFilter extends ZuulFilter {
	private static final Logger log = LoggerFactory.getLogger(PreRequestLogFilter.class);

	private static final String SECRET = "a-very-long-secret-key-that-is-at-least-64-bytes-long................";

	@Autowired
	private AuthFeignClient authFeignClient;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest req = ctx.getRequest();
		String path = req.getRequestURI();
		return !path.startsWith("/fosys/auth");
	}

	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest req = ctx.getRequest();

		String authHeader = req.getHeader("Authorization");

		// Block if no auth header
		if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer ")) {
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			ctx.getResponse().setContentType("application/json");
			ctx.setResponseBody("{\"status\": \"failure\",\"data\": {\"message\": \"Missing Authorization header\"}}");
			return null;
		}
		String token = authHeader.substring(7);

		if (!isValidToken(token)) {
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			ctx.getResponse().setContentType("application/json");
			ctx.setResponseBody("{\"status\": \"failure\",\"data\": {\"message\": \"Token Expired\"}}");
			return null;
		}

		UserWithToken validateToken = authFeignClient.validateToken(token);
		User user = validateToken.getUser();
		ctx.addZuulRequestHeader("userId", String.valueOf(user.getUserId()));
		ctx.addZuulRequestHeader("userType", String.valueOf(user.getUserType()));

		return null;
	}

	private boolean isValidToken(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token)
					.getBody();
			return claims.getExpiration() != null && claims.getExpiration().after(new Date());
		} catch (JwtException e) {
			log.error("Token validation failed: {}", e.getMessage());
			return false;
		}
	}
}
