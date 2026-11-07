package com.zuul.gateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.zuul.gateway.model.UserWithToken;

@FeignClient(name = "hm-auth-service")
public interface AuthFeignClient {

	@PostMapping("/fosys/validate-token")
	UserWithToken validateToken(@RequestHeader("Authorization") String token);
}