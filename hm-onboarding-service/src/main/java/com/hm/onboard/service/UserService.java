package com.hm.onboard.service;

import java.util.Map;

import com.hotel.common.model.UserRequest;
import com.hotel.common.util.FosysConstants.UserType;

public interface UserService {

	void updateUser(UserRequest userRequest, String userId, String updateUserId);

	Map<String, Object> getUser(String userId, UserType userType, String filterKey, String filterValue, int skip,
			int limit, String userByUserId);

	void deleteUser(String userId, UserType userType, String userByUserId);

}
