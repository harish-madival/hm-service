package com.hm.food.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Configuration
@PropertySource(value = "file:config/config.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:config/config-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
@Data
public class ConfigProperties {

	@Value(value = "${food.collection.name}")
	String foodCollectionName;

}
