package com.hm.onboard.dto;

import lombok.Data;

@Data
public class HotelRequest {

    private String name;

    private Float rating;

    private String cuisine;

    private String status;
}
