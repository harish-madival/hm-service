package com.hm.notification.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityApiKeyFilter implements Filter {

  @Value("${security.apiKeyHeader}")
  private String apiKeyHeader;

  @Value("${security.apiKeyValue}")
  private String apiKeyValue;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    String provided = req.getHeader(apiKeyHeader);
    if (provided == null || !provided.equals(apiKeyValue)) {
      ((HttpServletResponse) response).sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid API key");
      return;
    }
    chain.doFilter(request, response);
  }
}

