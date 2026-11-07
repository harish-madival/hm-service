package com.hotel.common.model;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document("user_token")
public class UserWithToken {

	private User user;

	private String userToken;

	private LocalDateTime expiryTime;
}
