package com.hm.food.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "food-list")
public class FoodList {

	@Id
	private String id;
	private String itemName;
	private int price;
	private String type;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;
}
