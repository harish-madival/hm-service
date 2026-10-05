package com.hotel.common.model;

import com.hotel.common.util.FosysConstants.UserType;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserRequest {

	private UserType userType;

	private String firstName;
	
	private String lastName;

	private String mobileNumber;

	private String roles;

	private String userName;

	private String password;
	
	private String confirmPassword;

	private String email;
}
