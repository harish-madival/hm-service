package com.hm.food.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.hm.food.model.FoodItems;
import com.hm.food.model.FoodList;
import com.hm.food.properties.ConfigProperties;
import com.hm.food.utils.FoodValidator;
import com.hotel.common.util.FosysConstants;

@Repository
public class FoodDaoImpl implements FoodDao {

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	ConfigProperties configProperties;

	@Override
	public FoodList addFood(FoodList foodList, String userId, String userType) {
		return mongoTemplate.save(foodList, configProperties.getFoodCollectionName());
	}

	@Override
	public List<FoodList> getFoods(String userId, String userType, String filterKey, String filterValue, String sortBy,
			String sortOrder, int skip, int limit) {
		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(Criteria.where(FosysConstants.USERID).is(userId),
				Criteria.where(FosysConstants.USER_TYPE).is(userType)));
		if (FoodValidator.isNotBlankNotNull(filterKey) && FoodValidator.isNotBlankNotNull(filterValue)) {
			query.addCriteria(Criteria.where(filterKey).regex(filterValue, "i"));
		}

		if (FoodValidator.isNotBlankNotNull(sortBy) && FoodValidator.isNotBlankNotNull(sortOrder)) {
			query.with(Sort.by(sortOrder, sortBy));

		} else {
			query.with(Sort.by(Sort.Direction.DESC, FosysConstants.CREATED_DATE));
		}
		query.skip(skip).limit(limit);

		return mongoTemplate.findAllAndRemove(query, FoodList.class, configProperties.getFoodCollectionName());

	}

	@Override
	public int getFoodsCount(String userId, String userType, String filterKey, String filterValue, String sortBy,
			String sortOrder) {
		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(Criteria.where(FosysConstants.USERID).is(userId),
				Criteria.where(FosysConstants.USER_TYPE).is(userType)));
		if (FoodValidator.isNotBlankNotNull(filterKey) && FoodValidator.isNotBlankNotNull(filterValue)) {
			query.addCriteria(Criteria.where(filterKey).regex(filterValue, "i"));
		}

		if (FoodValidator.isNotBlankNotNull(sortBy) && FoodValidator.isNotBlankNotNull(sortOrder)) {
			query.with(Sort.by(sortOrder, sortBy));

		} else {
			query.with(Sort.by(Sort.Direction.DESC, FosysConstants.CREATED_DATE));
		}

		return (int) mongoTemplate.count(query, FoodItems.class, configProperties.getFoodCollectionName());

	}

	@Override
	public FoodList getFoods(String foodId, String userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where(FosysConstants.ID).is(foodId));
		return mongoTemplate.findOne(query, FoodList.class, configProperties.getFoodCollectionName());
	}

	@Override
	public FoodList updateFood(FoodList foodList, String userId, String userType) {
		Query query = new Query();
		query.addCriteria(Criteria.where(FosysConstants.ID).is(foodList.getId()));
		return mongoTemplate.findAndReplace(query, foodList, configProperties.getFoodCollectionName());
	}

	@Override
	public void deleteFood(String foodId, String userId, String userType) {
		Query query = new Query();
		query.addCriteria(Criteria.where(FosysConstants.ID).is(foodId));
		mongoTemplate.findAndRemove(query, FoodList.class, configProperties.getFoodCollectionName());
	}

}
