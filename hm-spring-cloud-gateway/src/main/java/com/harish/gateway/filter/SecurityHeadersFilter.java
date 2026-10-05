package com.harish.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class SecurityHeadersFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return -50;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        HttpHeaders headers = exchange.getResponse().getHeaders();

        /*
         * Prevent MIME type sniffing
         */
        headers.add("X-Content-Type-Options", "nosniff");

        /*
         * Prevent clickjacking
         */
        headers.add("X-Frame-Options", "DENY");

        /*
         * XSS protection for older browsers
         */
        headers.add("X-XSS-Protection", "1; mode=block");

        /*
         * Referrer policy
         */
        headers.add("Referrer-Policy", "no-referrer");

        /*
         * Permissions policy
         */
        headers.add("Permissions-Policy", "geolocation=(), microphone=(), camera=()");

        /*
         * HSTS
         *
         * IMPORTANT:
         * Enable only when Gateway is served through HTTPS.
         */
        headers.add("Strict-Transport-Security", "max-age=31536000; includeSubDomains");

        return chain.filter(exchange);
    }
}
