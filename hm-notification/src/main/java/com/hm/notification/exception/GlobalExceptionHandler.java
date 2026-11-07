package com.hm.notification.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler({ MethodArgumentNotValidException.class, BindException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleValidation(Exception ex) {
		Map<String, String> map = new HashMap<>();
		map.put("error", "Invalid request payload");
		return map;
	}

	@ExceptionHandler(MailException.class)
	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	public Map<String, String> handleMail(MailException ex) {
		log.warn("mail_error: {}", ex.getMessage());
		Map<String, String> map = new HashMap<>();
		map.put("error", "Invalid request payload");
		return map;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, String> handleGeneric(Exception ex) {
		log.error("server_error", ex);
		Map<String, String> map = new HashMap<>();
		map.put("error", "Invalid request payload");
		return map;
	}
}
