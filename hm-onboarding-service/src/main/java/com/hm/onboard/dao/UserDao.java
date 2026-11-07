package com.hm.onboard.dao;

import java.util.List;

import com.hotel.common.model.User;
import com.hotel.common.util.FosysConstants.UserType;

public interface UserDao {

	User findUser(String userId);

	void update(User user, String userId);

	List<User> getUser(String userId, UserType userType, String filterKey, String filterValue, int skip, int limit,
			String userByUserId);

	int getUserCount(String userId, UserType userType, String filterKey, String filterValue, String userByUserId);

	void deleteUser(String userByUserId);

}
