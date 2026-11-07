//package com.zuul.gateway.filters;
//
//import com.github.benmanes.caffeine.cache.Cache;
//import com.github.benmanes.caffeine.cache.Caffeine;
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import io.github.bucket4j.Bandwidth;
//import io.github.bucket4j.Bucket;
//import io.github.bucket4j.Bucket4j;
//import io.github.bucket4j.Refill;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.servlet.http.HttpServletRequest;
//import java.time.Duration;
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class RateLimitFilter extends ZuulFilter {
//
//    private Cache<String, Bucket> cache;
//
//    @PostConstruct
//    public void init() {
//        cache = Caffeine.newBuilder()
//                .expireAfterAccess(30, TimeUnit.MINUTES)
//                .maximumSize(100_000)
//                .build();
//    }
//
//    @Override
//    public String filterType() {
//        return "pre";
//    }
//
//    @Override
//    public int filterOrder() {
//        return 0; // run earliest
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return true;
//    }
//
//    @Override
//    public Object run() {
//        RequestContext ctx = RequestContext.getCurrentContext();
//        HttpServletRequest req = ctx.getRequest();
//
//        String key = resolveKey(req);
//        Bucket bucket = cache.get(key, k -> newBucket());
//
//        if (!bucket.tryConsume(1)) {
//            ctx.setSendZuulResponse(false);
//            ctx.setResponseStatusCode(429);
//            ctx.setResponseBody("Rate limit exceeded");
//            ctx.getResponse().setContentType("text/plain;charset=UTF-8");
//        }
//        return null;
//    }
//
//    private String resolveKey(HttpServletRequest req) {
//        String ip = req.getRemoteAddr();
//        String route = req.getRequestURI().split("/")[1]; // crude per-top-level path
//        return ip + ":" + route;
//    }
//
//    private Bucket newBucket() {
//        // Allow 100 requests per minute per key
//        Refill refill = Refill.greedy(100, Duration.ofMinutes(1));
//        Bandwidth limit = Bandwidth.classic(100, refill);
//        return Bucket4j.builder().addLimit(limit).build();
//    }
//}
