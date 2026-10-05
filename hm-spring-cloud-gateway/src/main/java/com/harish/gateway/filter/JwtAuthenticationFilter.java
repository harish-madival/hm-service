package com.harish.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harish.gateway.client.AuthServiceClient;
import com.harish.gateway.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final AuthServiceClient authServiceClient;

    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(AuthServiceClient authServiceClient, ObjectMapper objectMapper) {

        this.authServiceClient = authServiceClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public int getOrder() {
        return -100;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        if (isPublicEndpoint(path)) {
            return chain.filter(exchange);
        }

        String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {

            return unauthorized(exchange, "TOKEN_MISSING", "Authorization token is missing");
        }

        String token = authorizationHeader.substring(7).trim();

        /*
         * Empty token
         */
        if (token.isEmpty()) {

            return unauthorized(exchange, "TOKEN_MISSING", "Authorization token is empty");
        }

        /*
         * Validate token using Auth Service
         */
        return authServiceClient.validateToken(token)

                .flatMap(response -> {

                    if (response == null) {

                        log.error("Auth Service returned null response");

                        return serviceUnavailable(exchange, "AUTH_SERVICE_ERROR", "Unable to validate token");
                    }

                    /*
                     * Token invalid / expired
                     */
                    if (!response.isValid()) {
                        String error = "TOKEN_INVALID";
                        String message = "Invalid authentication token";
                        log.warn("Token validation failed error={} message={}", error, message);
                        return unauthorized(exchange, error, message);
                    }

                    /*
                     * Extract user information
                     */
                    String userId = response.getUserId();

//                    String username =
//                            response.getUsername();

                    String role = response.getRole();

                    ServerWebExchange mutatedExchange = exchange.mutate().request(request -> request.headers(headers -> {
                        headers.remove("X-User-Id");
                        headers.remove("X-Username");
                        headers.remove("X-User-Role");
                        if (userId != null) {
                            headers.add("X-User-Id", userId);
                        }

//                                                        if (username != null) {
//                                                            headers.add(
//                                                                    "X-Username",
//                                                                    username);
//                                                        }

                        if (role != null) {
                            headers.add("X-User-Role", role);
                        }
                    })).build();
                    return chain.filter(mutatedExchange);
                })
                .onErrorResume(exception -> {

                    log.error("Token validation failed. URI={}", exchange.getRequest().getURI(), exception);
                    if (exception instanceof NotFoundException) {
                        return serviceUnavailable(exchange, "Service Unavailable.", exception.getMessage());
                    } else if(exception instanceof WebClientResponseException.Unauthorized) {
                        return unauthorized(exchange, "Unauthorized.", "Unauthorized");
                    } else {
                        return serviceUnavailable(exchange, "Service error.", exception.getMessage());
                    }
                });
    }

    private boolean isPublicEndpoint(String path) {
        return path.equalsIgnoreCase("/fosys/auth/login") || path.equalsIgnoreCase("/fosys/auth/send-otp") || path.equalsIgnoreCase("/fosys/auth/verify-login");
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String error, String message) {
        return writeErrorResponse(exchange, HttpStatus.UNAUTHORIZED, error, message);
    }

    private Mono<Void> serviceUnavailable(ServerWebExchange exchange, String error, String message) {
        return writeErrorResponse(exchange, HttpStatus.SERVICE_UNAVAILABLE, error, message);
    }

    private Mono<Void> writeErrorResponse(ServerWebExchange exchange, HttpStatus status, String error, String message) {
        String correlationId = exchange.getRequest().getHeaders().getFirst("X-Correlation-Id");
        ErrorResponse errorResponse = new ErrorResponse(status.value(), error, message, correlationId);
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);
            return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
        } catch (JsonProcessingException exception) {
            log.error("Unable to create error response", exception);
            return exchange.getResponse().setComplete();
        }
    }
}