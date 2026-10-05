package com.harish.gateway.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harish.gateway.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.webflux.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(
            ServerWebExchange exchange,
            Throwable exception) {

        log.error(
                "Gateway exception. URI={}",
                exchange.getRequest().getURI(),
                exception
        );

        if (exchange.getResponse().isCommitted()) {
            return Mono.error(exception);
        }

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String error = "GATEWAY_ERROR";
        String message = "An unexpected error occurred";

        /*
         * Auth Service / downstream service
         * instance not available
         */
        if (exception instanceof NotFoundException) {

            status = HttpStatus.SERVICE_UNAVAILABLE;

            error = "SERVICE_UNAVAILABLE";

            message = "Authentication service is currently unavailable";
        }

        String correlationId =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst("X-Correlation-Id");

        ErrorResponse errorResponse =
                new ErrorResponse(
                        status.value(),
                        error,
                        message,
                        correlationId
                );

        exchange.getResponse()
                .setStatusCode(status);

        exchange.getResponse()
                .getHeaders()
                .setContentType(MediaType.APPLICATION_JSON);

        try {

            byte[] bytes =
                    objectMapper.writeValueAsBytes(errorResponse);

            return exchange.getResponse()
                    .writeWith(
                            Mono.just(
                                    exchange.getResponse()
                                            .bufferFactory()
                                            .wrap(bytes)
                            )
                    );

        } catch (Exception e) {

            log.error(
                    "Unable to create error response",
                    e
            );

            return exchange.getResponse()
                    .setComplete();
        }
    }
}