package com.zuul.gateway.model;

import java.time.LocalDateTime;

public class UserWithToken {

	private User user;

	private String userToken;

	private LocalDateTime expiryTime;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public LocalDateTime getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(LocalDateTime expiryTime) {
		this.expiryTime = expiryTime;
	}
	
	
}
