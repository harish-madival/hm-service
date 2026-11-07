package com.hm.onboard.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("hotel_entity")
public class HotelEntity {

    private String id;

    private String name;

    private Float rating;

    private String cuisine;

    private String status;

    private String userId;

    public HotelEntity() {
    }

    public HotelEntity(String name, Float rating, String cuisine) {
        this.name = name;
        this.rating = rating;
        this.cuisine = cuisine;
    }
}
