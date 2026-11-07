package com.hm.notification.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SmtpHealthIndicator implements HealthIndicator {
	private final JavaMailSender sender;

	public SmtpHealthIndicator(JavaMailSender sender) {
		this.sender = sender;
	}

	@Override
	public Health health() {
		try {
			sender.createMimeMessage(); // lightweight check
			return Health.up().build();
		} catch (Exception e) {
			return Health.down(e).build();
		}
	}
}
