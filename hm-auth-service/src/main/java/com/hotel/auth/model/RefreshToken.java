package com.hotel.auth.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "refresh_tokens")
@Data
public class RefreshToken {

	@Id
	private String id; // token id (we store the token string or a hashed id)
	private String username;
	private Date expiryDate;
}
