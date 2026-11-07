package com.zuul.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class PostResponseLogFilter extends ZuulFilter {
	private static final Logger log = LoggerFactory.getLogger(PostResponseLogFilter.class);

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		int status = ctx.getResponseStatusCode();
		String routeHost = ctx.getRouteHost() != null ? ctx.getRouteHost().toString() : "serviceId";
		log.info("Response {} routed to {}", status, routeHost);
		return null;
	}
}
