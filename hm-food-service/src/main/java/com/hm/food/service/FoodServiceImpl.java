package com.hm.food.service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hm.food.dao.FoodDao;
import com.hm.food.model.FoodItems;
import com.hm.food.model.FoodList;

@Service
public class FoodServiceImpl implements FoodService {

	@Autowired
	FoodDao foodDao;

	@Override
	public FoodList addFood(FoodItems foodItems, String userId, String userType) {
		FoodList foodList = new FoodList();
		foodList.setCreatedBy(userId);
		foodList.setCreatedDate(Date.from(Instant.now()));
		foodList.setItemName(foodItems.getItemName());
		foodList.setPrice(foodItems.getPrice());
		foodList.setType(foodItems.getType());
		return foodDao.addFood(foodList, userId, userType);
	}

	@Override
	public FoodItemsInfo getFood(String userId, String userType, String filterKey, String filterValue, String sortBy,
			String sortOrder, int skip, int limit) {
		List<FoodList> foods = foodDao.getFoods(userId, userType, filterKey, filterValue, sortBy, sortOrder, skip,
				limit);
		return new FoodItemsInfo(
				foods.stream().map(food -> new FoodItems(food.getItemName(), food.getPrice(), food.getType()))
						.collect(Collectors.toList()),
				foodDao.getFoodsCount(userId, userType, filterKey, filterValue, sortBy, sortOrder));
	}

	@Override
	public FoodList updateFood(FoodItems foodItems, String userId, String userType, String foodId) {
		FoodList foodList = foodDao.getFoods(foodId, userId);
		foodList.setUpdatedBy(userId);
		foodList.setUpdatedDate(Date.from(Instant.now()));
		foodList.setItemName(foodItems.getItemName());
		foodList.setPrice(foodItems.getPrice());
		foodList.setType(foodItems.getType());

		return foodDao.updateFood(foodList, userId, userType);
	}

	@Override
	public Map<String, String> deleteFood(String userId, String userType, String foodId) {
		foodDao.deleteFood(foodId, userId, userType);
		return null;
	}

}
