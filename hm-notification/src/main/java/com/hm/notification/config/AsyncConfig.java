package com.hm.notification.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {
	@Bean("notifyExecutor")
	Executor notifyExecutor() {
		ThreadPoolTaskExecutor ex = new ThreadPoolTaskExecutor();
		ex.setCorePoolSize(4);
		ex.setMaxPoolSize(16);
		ex.setQueueCapacity(1000);
		ex.setThreadNamePrefix("notify-");
		ex.initialize();
		return ex;
	}
}
