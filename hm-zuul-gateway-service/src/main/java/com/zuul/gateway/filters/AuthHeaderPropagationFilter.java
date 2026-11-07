package com.zuul.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class AuthHeaderPropagationFilter extends ZuulFilter {
  @Override public String filterType() { return "pre"; }
  @Override public int filterOrder() { return 2; }
  @Override public boolean shouldFilter() { return true; }

  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();
    // Allow Authorization header through (unless stripped by sensitive-headers)
    String auth = ctx.getRequest().getHeader("Authorization");
    if (auth != null && !auth.isEmpty()) {
      ctx.addZuulRequestHeader("Authorization", auth);
    }
    return null;
  }
}
