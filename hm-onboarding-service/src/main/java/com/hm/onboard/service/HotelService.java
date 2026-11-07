package com.hm.onboard.service;

import java.util.List;

import com.hm.onboard.dto.HotelRequest;
import com.hm.onboard.model.HotelEntity;

public interface HotelService {

	HotelEntity saveHotelData(HotelRequest hotelEntity, String userId);

	List<HotelEntity> getHotelData(String userId, String hotelId);

	void deleteHotelData(String userId, String hotelId);

	void updateHotelData(HotelEntity hotelEntity, String userId, String hotelId);

}
