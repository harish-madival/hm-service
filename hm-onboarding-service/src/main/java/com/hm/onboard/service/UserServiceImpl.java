package com.hm.onboard.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hm.onboard.dao.UserDao;
import com.hotel.common.model.User;
import com.hotel.common.model.UserRequest;
import com.hotel.common.util.FosysConstants.UserType;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userdao;

	@Override
	public void updateUser(UserRequest userRequest, String userId, String updateUserId) {
		User user = userdao.findUser(userId);
		user.setEmail(userRequest.getEmail());
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		user.setMobileNumber(userRequest.getMobileNumber());
		user.setModifiedBy(userId);
		user.setModifiedDate(new Date());
		user.setUserName(userRequest.getUserName());
		user.setUserType(userRequest.getUserType());
		userdao.update(user, updateUserId);
	}

	@Override
	public Map<String, Object> getUser(String userId, UserType userType, String filterKey, String filterValue, int skip,
			int limit, String userByUserId) {
		Map<String, Object> map = new HashMap<>();
		map.put("users", userdao.getUser(userId, userType, filterKey, filterValue, skip, limit, userByUserId));
		map.put("userCount", userdao.getUserCount(userId, userType, filterKey, filterValue, userByUserId));
		return map;
	}

	@Override
	public void deleteUser(String userId, UserType userType, String userByUserId) {
		userdao.deleteUser(userByUserId);
	}

}
