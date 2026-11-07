package com.hotel.auth.dao;

import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.hotel.common.model.User;
import com.hotel.common.util.FosysConstants;

@Repository
public class UserRepository {
	private final MongoTemplate mongoTemplate;

	public UserRepository(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public Optional<User> findUser(String userId) {
		Query query = new Query(new Criteria().orOperator(Criteria.where(FosysConstants.MOBILENUMBER).is(userId),
				Criteria.where(FosysConstants.USERID).is(userId), Criteria.where(FosysConstants.USERNAME).is(userId),
				Criteria.where(FosysConstants.EMAIL).is(userId)));
		User user = mongoTemplate.findOne(query, User.class);
		return Optional.ofNullable(user);
	}

	public User save(User user) {
		return mongoTemplate.save(user);
	}

}
