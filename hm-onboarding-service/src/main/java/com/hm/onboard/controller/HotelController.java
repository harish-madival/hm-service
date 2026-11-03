package com.hm.onboard.controller;

import com.hm.onboard.dto.HotelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hm.onboard.model.HotelEntity;
import com.hm.onboard.service.HotelService;
import com.hotel.common.model.HmResponse;
import com.hotel.common.util.FosysConstants.UserType;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    HotelService hotelService;

    @PostMapping
    public ResponseEntity<?> saveHotel(@RequestBody HotelRequest hotelRequest,
                                       @RequestHeader(name = "userId") String userId, @RequestHeader(name = "userType") UserType userType) {

        return new ResponseEntity<>(new HmResponse("SUCCESS", hotelService.saveHotelData(hotelRequest, userId)),
                HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateHotel(@RequestBody HotelEntity hotelEntity,
                                         @RequestHeader(name = "userId") String userId, @RequestHeader(name = "userType") UserType userType, @RequestParam(name = "hotelId") String hotelId) {

        hotelService.updateHotelData(hotelEntity, userId, hotelId);
        return new ResponseEntity<>(new HmResponse("SUCCESS", "Hotel Updated."), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getHotels(@RequestHeader(name = "userId") String userId,
                                       @RequestParam(name = "hotelId", required = false) String hotelId) {

        return new ResponseEntity<>(new HmResponse("SUCCESS", hotelService.getHotelData(userId, hotelId)), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteHotel(@RequestHeader(name = "userId") String userId,
                                         @RequestParam(name = "hotelId") String hotelId) {

        hotelService.deleteHotelData(userId, hotelId);
        return new ResponseEntity<>(new HmResponse("SUCCESS", "Hotel removed."), HttpStatus.OK);
    }

}
