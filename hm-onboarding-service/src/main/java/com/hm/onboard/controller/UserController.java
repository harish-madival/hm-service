package com.hm.onboard.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hm.onboard.service.UserService;
import com.hotel.common.model.HmResponse;
import com.hotel.common.model.UserRequest;
import com.hotel.common.util.FosysConstants;
import com.hotel.common.util.FosysConstants.UserType;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest,
			@RequestHeader(name = FosysConstants.USERID) String userId,
			@RequestHeader(name = FosysConstants.USER_TYPE) UserType userType,
			@RequestParam(name = FosysConstants.USER_BY_USERID) String updateUserId) {
		userService.updateUser(userRequest, userId, updateUserId);
		Map<String, String> map = new HashMap<>();
		map.put("message", "Updated user successfully");
		return new ResponseEntity<>(new HmResponse("success", map), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> getUser(@RequestHeader(name = FosysConstants.USERID) String userId,
			@RequestHeader(name = FosysConstants.USER_TYPE) UserType userType,
			@RequestParam(name = FosysConstants.FILTER_KEY, required = false) String filterKey,
			@RequestParam(name = FosysConstants.FILTER_VALUE, required = false) String filterValue,
			@RequestParam(name = FosysConstants.SKIP, required = false) int skip,
			@RequestParam(name = FosysConstants.LIMIT, required = false) int limit,
			@RequestParam(name = FosysConstants.USER_BY_USERID, required = false) String userByUserId) {

		return new ResponseEntity<>(
				new HmResponse("success",
						userService.getUser(userId, userType, filterKey, filterValue, skip, limit, userByUserId)),
				HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteUser(@RequestHeader(name = FosysConstants.USERID) String userId,
			@RequestHeader(name = FosysConstants.USER_TYPE) UserType userType,
			@RequestParam(name = FosysConstants.USER_BY_USERID, required = false) String userByUserId) {
		userService.deleteUser(userId, userType, userByUserId);
		Map<String, String> map = new HashMap<>();
		map.put("message", "User Deleted.");
		return new ResponseEntity<>(new HmResponse("success", map), HttpStatus.OK);
	}
}
