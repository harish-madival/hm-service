//package com.zuul.gateway.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//	  http
//	    .csrf(csrf -> csrf.disable())
//	    .headers(headers -> headers
//	        .contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self'")))
//	    .sessionManagement(session -> 
//	        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//	    .authorizeRequests(auth -> auth
//	        .antMatchers("/actuator/health", "/actuator/info").permitAll()
//	        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//	        .antMatchers("/hotel/api/auth/**").permitAll()
//	        .anyRequest().authenticated()
//	    )
//	    .httpBasic(Customizer.withDefaults());
//
//	  return http.build();
//	}
//
//}
