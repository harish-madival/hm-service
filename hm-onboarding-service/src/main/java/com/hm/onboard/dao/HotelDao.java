package com.hm.onboard.dao;

import java.util.List;

import com.hm.onboard.model.HotelEntity;

public interface HotelDao {

	HotelEntity saveHotel(HotelEntity hotelEntity);

	List<HotelEntity> getHotelData(String userId, String hotelId);

	void deleteHotelData(String userId, String hotelId);

	void updateHotel(HotelEntity hotelEntity, String userId, String hotelId);

}
