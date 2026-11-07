package com.hm.onboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Configuration
@PropertySource(value = "file:config/config.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:config/config-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
@Data
public class ConfigProperties {

}
