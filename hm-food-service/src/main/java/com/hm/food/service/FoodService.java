package com.hm.food.service;

import java.util.Map;

import com.hm.food.model.FoodItems;
import com.hm.food.model.FoodList;

public interface FoodService {

	FoodItemsInfo getFood(String userId, String userType, String filterKey, String filterValue,
			String sortBy, String sortOrder, int skip, int limit);

	FoodList addFood(FoodItems foodItems, String userId, String userType);

	FoodList updateFood(FoodItems foodItems, String userId, String userType, String foodId);

	Map<String, String> deleteFood(String userId, String userType, String foodId);

}
