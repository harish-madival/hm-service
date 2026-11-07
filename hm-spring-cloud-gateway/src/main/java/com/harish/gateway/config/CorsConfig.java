//package com.harish.gateway.config;
//
//import java.util.Arrays;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.web.cors.reactive.CorsWebFilter;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
//import org.springframework.web.cors.CorsConfiguration;
//
//@Configuration
//public class CorsConfig {
//
//    @Bean
//    CorsWebFilter corsWebFilter(
//            @Value("${gateway.cors.allowed-origins:http://localhost:5173,http://localhost:3000}") String allowedOrigins) {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split("\\s*,\\s*")));
//        configuration.setAllowedMethods(Arrays.asList(
//                HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
//                HttpMethod.PATCH.name(), HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name()));
//        configuration.addAllowedHeader(CorsConfiguration.ALL);
//        configuration.addExposedHeader("X-Correlation-Id");
//        configuration.setMaxAge(3600L);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return new CorsWebFilter(source);
//    }
//}
