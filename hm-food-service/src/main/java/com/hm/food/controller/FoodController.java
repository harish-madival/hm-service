package com.hm.food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hm.food.model.FoodItems;
import com.hm.food.service.FoodService;
import com.hotel.common.model.HmResponse;
import com.hotel.common.util.FosysConstants;

@RestController
@RequestMapping("/food")
public class FoodController {

	@Autowired
	private FoodService foodService;

	@PostMapping
	public ResponseEntity<?> addFood(@RequestBody FoodItems foodItems,
			@RequestHeader(name = FosysConstants.USERID) String userId,
			@RequestHeader(name = FosysConstants.USER_TYPE) String userType) {
		return new ResponseEntity<>(new HmResponse("success", foodService.addFood(foodItems, userId, userType)),
				HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<?> getFood(@RequestHeader(name = FosysConstants.USERID) String userId,
			@RequestHeader(name = FosysConstants.USER_TYPE) String userType,
			@RequestParam(name = FosysConstants.FILTER_KEY, required = false) String filterKey,
			@RequestParam(name = FosysConstants.FILTER_VALUE, required = false) String filterValue,
			@RequestParam(name = FosysConstants.SORTBY, required = false) String sortBy,
			@RequestParam(name = FosysConstants.SORTORDER, required = false) String sortOrder,
			@RequestParam(name = FosysConstants.SKIP) int skip, @RequestParam(name = FosysConstants.LIMIT) int limit) {
		return new ResponseEntity<>(
				new HmResponse("success",
						foodService.getFood(userId, userType, filterKey, filterValue, sortBy, sortOrder, skip, limit)),
				HttpStatus.OK);

	}

	@DeleteMapping
	public ResponseEntity<?> deleteFood(@RequestHeader(name = FosysConstants.USERID) String userId,
			@RequestHeader(name = FosysConstants.USER_TYPE) String userType,
			@RequestParam(name = FosysConstants.FOOD_ID) String foodId) {
		return new ResponseEntity<>(new HmResponse("success", foodService.deleteFood(userId, userType, foodId)),
				HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> updateFood(@RequestBody FoodItems foodItems,
			@RequestHeader(name = FosysConstants.USERID) String userId,
			@RequestHeader(name = FosysConstants.USER_TYPE) String userType,
			@RequestParam(name = FosysConstants.FOOD_ID) String foodId) {

		return new ResponseEntity<>(
				new HmResponse("success", foodService.updateFood(foodItems, userId, userType, foodId)), HttpStatus.OK);

	}

}
