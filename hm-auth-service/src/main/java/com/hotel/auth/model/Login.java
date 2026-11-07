package com.hotel.auth.model;

import lombok.Data;

@Data
public class Login {

	private String userName;
	
	private String password;
	
	private String mobileNumber;
	
	private String otp;
	
}
