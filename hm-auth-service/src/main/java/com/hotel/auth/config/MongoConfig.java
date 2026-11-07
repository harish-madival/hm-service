package com.hotel.auth.config;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;

import com.hotel.auth.model.OtpVerification;
import com.hotel.common.model.User;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig {

	@Autowired
	private MongoProperties mongoProperties;

	@Bean
	MongoClient mongoClient() {
		List<ServerAddress> servers = Arrays.stream(mongoProperties.getHosts().split(",")).map(host -> {
			String[] parts = host.split(":");
			String hostname = parts[0].trim();
			int port = parts.length > 1 ? Integer.parseInt(parts[1].trim()) : 27017;
			return new ServerAddress(hostname, port);
		}).collect(Collectors.toList());

		MongoClientSettings.Builder settingsBuilder = MongoClientSettings.builder().applyToClusterSettings(builder -> {
			builder.hosts(servers);
			if (mongoProperties.getReplicaSet() != null && !mongoProperties.getReplicaSet().isEmpty()) {
				builder.requiredReplicaSetName(mongoProperties.getReplicaSet());
			}
		}).applyToConnectionPoolSettings(
				builder -> builder.maxSize(100).minSize(10).maxWaitTime(1000, TimeUnit.MILLISECONDS))
				.applyToSocketSettings(
						builder -> builder.connectTimeout(10, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS))
				.applyToSslSettings(builder -> builder.enabled(mongoProperties.isSsl()));

		if (mongoProperties.getUsername() != null && !mongoProperties.getUsername().isEmpty()) {
			MongoCredential credential = MongoCredential.createCredential(mongoProperties.getUsername(),
					mongoProperties.getDatabase(), mongoProperties.getPassword().toCharArray());
			settingsBuilder.credential(credential);
		}

		return MongoClients.create(settingsBuilder.build());
	}

	@Bean
	public MongoTemplate mongoTemplate() {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoClient(), mongoProperties.getDatabase());
		initIndexes(mongoTemplate);
		return mongoTemplate;
	}

	private void initIndexes(MongoTemplate mongoTemplate) {
		IndexOperations otpIndexOps = mongoTemplate.indexOps(OtpVerification.class);
		Index ttlIndex = new Index().on("expiryTime", Sort.Direction.ASC).expire(0L, TimeUnit.SECONDS);
		otpIndexOps.ensureIndex(ttlIndex);

		IndexOperations userIndexOps = mongoTemplate.indexOps(User.class);
		Index mobileIndex = new Index().on("mobileNumber", Sort.Direction.ASC).unique();
		userIndexOps.ensureIndex(mobileIndex);
	}
}
