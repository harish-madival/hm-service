package com.hotel.auth.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.auth.model.TokenValidationResponse;
import com.hotel.auth.security.JwtTokenUtil;
import com.hotel.auth.service.UserService;
import com.hotel.common.model.User;

@RestController
public class AuthenticationController {

	private static final String BEARER_PREFIX = "Bearer ";

	private final UserService userService;
	private final JwtTokenUtil jwtTokenUtil;

	public AuthenticationController(UserService userService, JwtTokenUtil jwtTokenUtil) {
		this.userService = userService;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@PostMapping("/validate-token")
	public ResponseEntity<TokenValidationResponse> validateToken(
			@RequestHeader(value = "Authorization", required = false) String authorization) {

		if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
			return unauthorizedResponse();
		}

		String token = authorization.substring(BEARER_PREFIX.length()).trim();

		if (token.isEmpty() || !jwtTokenUtil.validateToken(token)) {
			return unauthorizedResponse();
		}

		String mobileNumber = jwtTokenUtil.getMobileNumberFromToken(token);

		Optional<User> user = userService.findUser(mobileNumber);

		if (!user.isPresent()) {
			return unauthorizedResponse();
		}

		User authenticatedUser = user.get();

		String userType = authenticatedUser.getUserType() != null
				? authenticatedUser.getUserType().name()
				: null;

		return ResponseEntity.ok(
				TokenValidationResponse.valid(
						authenticatedUser.getUserId(),
						authenticatedUser.getMobileNumber(),
						authenticatedUser.getRoles(),
						userType
				)
		);
	}

	private ResponseEntity<TokenValidationResponse> unauthorizedResponse() {
		return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.body(TokenValidationResponse.invalid());
	}
}
