package com.zuul.gateway.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

//	@Bean
//	OpenAPI customOpenAPI() {
//		return new OpenAPI()
//				.info(new Info().title("Food Service API").version("1.0")
//				.description("Aggregated APIs for Auth + Food services"))
//		// 👇 show Gateway URL in Swagger (not private IPs)
//		.servers(Arrays.asList(new Server().url("http://localhost:8766")))
//				.components(new Components().addSecuritySchemes("bearer-jwt",
//						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
//				.addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
//	}
//
//	@Bean
//	GroupedOpenApi authApi() {
//		return GroupedOpenApi.builder().group("auth-service").packagesToScan("com.hotel.auth").build();
//	}
//
//	@Bean
//	GroupedOpenApi foodApi() {
//		return GroupedOpenApi.builder().group("food-service").pathsToMatch("/hotel/food/**").build();
//	}
}
