package com.hotel.auth.model;

import lombok.Data;

@Data
public class LogInDetails {

	private String mobileNumber;
	
	private String otp;
	
	private String userName;
	
	private String password;
}
