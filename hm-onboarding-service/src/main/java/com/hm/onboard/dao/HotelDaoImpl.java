package com.hm.onboard.dao;

import java.util.List;

import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.hm.onboard.model.HotelEntity;

@Repository
public class HotelDaoImpl implements HotelDao {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public HotelEntity saveHotel(HotelEntity hotelEntity) {
		return mongoTemplate.save(hotelEntity);
	}

	@Override
	public List<HotelEntity> getHotelData(String userId, String hotelId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		if (StringUtils.isNotBlank(hotelId)) {
			query.addCriteria(Criteria.where("id").is(hotelId));
		}
		return mongoTemplate.find(query, HotelEntity.class);
	}

	@Override
	public void deleteHotelData(String userId, String hotelId) {
		mongoTemplate.remove(new Query().addCriteria(
				new Criteria().andOperator(Criteria.where("userId").is(userId), Criteria.where("id").is(hotelId))),
				HotelEntity.class);
	}

	@Override
	public void updateHotel(HotelEntity hotelEntity, String userId, String hotelId) {
		// TODO Auto-generated method stub
		mongoTemplate.findAndReplace(new Query().addCriteria(
				new Criteria().andOperator(Criteria.where("userId").is(userId), Criteria.where("id").is(hotelId))),
				hotelEntity);
	}

}
