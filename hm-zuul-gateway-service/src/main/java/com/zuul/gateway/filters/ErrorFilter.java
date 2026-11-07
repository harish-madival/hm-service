package com.zuul.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ErrorFilter extends ZuulFilter {
    private static final Logger log = LoggerFactory.getLogger(ErrorFilter.class);

    @Override public String filterType() { return "error"; }
    @Override public int filterOrder() { return 1; }
    @Override public boolean shouldFilter() { return true; }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();
        log.error("Zuul error detected: {}", throwable.getMessage(), throwable);

        ctx.setResponseStatusCode(500);
        ctx.getResponse().setContentType("application/json");
        ctx.setResponseBody("{\"error\": \"Internal gateway error\"}");
        return null;
    }
}

