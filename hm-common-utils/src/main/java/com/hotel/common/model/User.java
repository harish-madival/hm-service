package com.hotel.common.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.hotel.common.util.FosysConstants.UserType;

import lombok.Data;

@Data
@Document(collection = "users")
public class User {
	@Id
	private String userId;

	private String firstName;

	private String lastName;

	private UserType userType;

	@Indexed(unique = true)
	private String mobileNumber;

	private String roles;

	@Indexed(unique = true)
	private String userName;

	private String password;
	
	private String confirmPassword;

	@Indexed(unique = true)
	private String email;

	private Date createdDate;

	private String createdBy;

	private Date modifiedDate;

	private String modifiedBy;
}
