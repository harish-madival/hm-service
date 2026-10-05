package com.harish.gateway.filter;

import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {

    private static final Logger log =
            LoggerFactory.getLogger(RequestLoggingFilter.class);

    private static final String CORRELATION_ID = "X-Correlation-Id";

    @Override
    public int getOrder() {
        return -150;
    }

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        long startTime = System.nanoTime();

        String method = Objects.requireNonNull(exchange.getRequest()
                        .getMethod())
                .name();

        String path = exchange.getRequest()
                .getURI()
                .getPath();

        String correlationId = exchange.getRequest()
                .getHeaders()
                .getFirst(CORRELATION_ID);

        // Generate correlation ID when client does not provide one
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }

        // Keep the value effectively final for the lambda
        final String finalCorrelationId = correlationId;

        // Add correlation ID to the downstream request
        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(builder -> builder.header(
                        CORRELATION_ID,
                        finalCorrelationId))
                .build();

        // Add correlation ID to the response
        exchange.getResponse()
                .getHeaders()
                .set(CORRELATION_ID, finalCorrelationId);

        log.info(
                "Incoming request method={} path={} correlationId={}",
                method,
                path,
                finalCorrelationId
        );

        return chain.filter(mutatedExchange)
                .doFinally(signalType -> {

                    long durationMs =
                            (System.nanoTime() - startTime) / 1_000_000;

                    HttpStatus status =
                            (HttpStatus) exchange.getResponse().getStatusCode();

                    int statusCode = status != null
                            ? status.value()
                            : 0;

                    if (signalType == SignalType.ON_ERROR) {

                        log.error(
                                "Request failed method={} path={} status={} durationMs={} correlationId={} signal={}",
                                method,
                                path,
                                statusCode,
                                durationMs,
                                finalCorrelationId,
                                signalType
                        );

                    } else {

                        log.info(
                                "Request completed method={} path={} status={} durationMs={} correlationId={} signal={}",
                                method,
                                path,
                                statusCode,
                                durationMs,
                                finalCorrelationId,
                                signalType
                        );
                    }
                });
    }
}