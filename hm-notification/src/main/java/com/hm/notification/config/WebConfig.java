package com.hm.notification.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hm.notification.filter.SecurityApiKeyFilter;

@Configuration
public class WebConfig {
	@Bean
	FilterRegistrationBean<SecurityApiKeyFilter> apiKeyFilter(SecurityApiKeyFilter filter) {
		FilterRegistrationBean<SecurityApiKeyFilter> reg = new FilterRegistrationBean<>();
		reg.setFilter(filter);
		reg.addUrlPatterns("/api/notify/*");
		reg.setOrder(1);
		return reg;
	}
}
