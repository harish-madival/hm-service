package com.hm.onboard.dao;

import java.util.List;

import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.hotel.common.model.User;
import com.hotel.common.util.FosysConstants;
import com.hotel.common.util.FosysConstants.UserType;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public User findUser(String userId) {
		return mongoTemplate.findOne(new Query().addCriteria(Criteria.where("userId").is(userId)), User.class);
	}

	@Override
	public void update(User user, String userId) {
		mongoTemplate.findAndReplace(new Query().addCriteria(Criteria.where("userId").is(userId)), User.class);

	}

	@Override
	public List<User> getUser(String userId, UserType userType, String filterKey, String filterValue, int skip,
			int limit, String userByUserId) {
		Query query = new Query();
		query.addCriteria(Criteria.where(FosysConstants.CREATEDBY).is(userId));
		if (StringUtils.isNotBlank(filterKey) && StringUtils.isNotBlank(filterValue)) {
			query.addCriteria(Criteria.where(filterKey).is(filterValue).regex(filterValue, "i"));
		}
		if (StringUtils.isNotBlank(userByUserId)) {
			query.addCriteria(Criteria.where(FosysConstants.USERID).is(userByUserId));
		}
		query.skip(skip).limit(limit);

		return mongoTemplate.find(query, User.class);
	}

	@Override
	public int getUserCount(String userId, UserType userType, String filterKey, String filterValue,
			String userByUserId) {
		Query query = new Query();
		query.addCriteria(Criteria.where(FosysConstants.CREATEDBY).is(userId));
		if (StringUtils.isNotBlank(filterKey) && StringUtils.isNotBlank(filterValue)) {
			query.addCriteria(Criteria.where(filterKey).is(filterValue).regex(filterValue, "i"));
		}
		if (StringUtils.isNotBlank(userByUserId)) {
			query.addCriteria(Criteria.where(FosysConstants.USERID).is(userByUserId));
		}
		return (int) mongoTemplate.count(query, User.class);
	}

	@Override
	public void deleteUser(String userByUserId) {
		mongoTemplate.findAndRemove(new Query().addCriteria(Criteria.where("userId").is(userByUserId)), User.class);

	}

}
