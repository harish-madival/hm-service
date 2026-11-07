package com.hm.food.properties;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("Food Service API").version("1.0"));
	}

//	@Bean
//	OpenAPI customOpenAPI() {
//		return new OpenAPI()
//				.info(new Info().title("Food Service API").version("1.0")
//						.description("Aggregated APIs for Auth + Food services"))
//				// 👇 show Gateway URL in Swagger (not private IPs)
//				.servers(Arrays.asList(new Server().url("http://localhost:8766/hotel/food")))
//				.components(new Components().addSecuritySchemes("bearer-jwt",
//						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
//				.addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
//	}

//	@Bean
//	OpenAPI customOpenAPI() {
//		return new OpenAPI()
//				.info(new Info().title("Food Service API").version("1.0")
//						.description("Aggregated APIs for Auth + Food services"))
//				.components(new Components().addSecuritySchemes("bearer-jwt",
//						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
//				.addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
//	}

	@Bean
	GroupedOpenApi foodApi() {
		return GroupedOpenApi.builder().group("food-service").pathsToMatch("/**").build();
	}

//	@Bean
//	GroupedOpenApi foodApi() {
//		return GroupedOpenApi.builder().group("food-service").packagesToScan("com.hm.food").build();
//	}

}
