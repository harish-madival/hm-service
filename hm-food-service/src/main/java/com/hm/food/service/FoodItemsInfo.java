package com.hm.food.service;

import java.util.List;

import com.hm.food.model.FoodItems;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FoodItemsInfo {
	
	List<FoodItems> foodList;
	
	int totalCount;
	
}
