package com.hm.food.dao;

import java.util.List;

import com.hm.food.model.FoodList;

public interface FoodDao {

	FoodList addFood(FoodList foodList, String userId, String userType);

	List<FoodList> getFoods(String userId, String userType, String filterKey, String filterValue, String sortBy,
			String sortOrder, int skip, int limit);

	int getFoodsCount(String userId, String userType, String filterKey, String filterValue, String sortBy,
			String sortOrder);

	FoodList getFoods(String foodId, String userId);

	FoodList updateFood(FoodList foodList, String userId, String userType);

	void deleteFood(String foodId, String userId, String userType);

}
