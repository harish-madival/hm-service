package com.hotel.auth.service;

import java.util.Map;
import java.util.Optional;

import com.hotel.auth.model.LogInDetails;
import com.hotel.common.model.User;
import com.hotel.common.model.UserRequest;

public interface UserService {

	Optional<User> findUser(String mobileNumber);

	User save(UserRequest userRequest);

	Map<String, Object> sendOtp(LogInDetails loginDetails);

	Map<String, Object> verifyLogin(LogInDetails loginDetails);

	Map<String, Object> login(LogInDetails loginDetails);
}
