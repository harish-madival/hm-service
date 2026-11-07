package com.harish.gateway.client;

import com.harish.gateway.dto.TokenValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AuthServiceClient {

    private static final Logger log =
            LoggerFactory.getLogger(AuthServiceClient.class);

    private final WebClient webClient;

    public AuthServiceClient(WebClient.Builder webClientBuilder) {

        this.webClient = webClientBuilder
                .baseUrl("http://hm-auth-service")
                .build();
    }

    public Mono<TokenValidationResponse> validateToken(String token) {
        Mono<TokenValidationResponse> tokenValidationResponseMono = webClient
                .post()
                .uri("/fosys/validate-token")
                .header(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + token
                )
                .retrieve()
                .bodyToMono(TokenValidationResponse.class);
        return tokenValidationResponseMono;
    }
}