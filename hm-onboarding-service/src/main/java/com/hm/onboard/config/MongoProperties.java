package com.hm.onboard.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "spring.data.mongodb")
@Data
public class MongoProperties {
	private String hosts;
	private String database;
	private String username;
	private String password;
	private String replicaSet;
	private boolean ssl;
}