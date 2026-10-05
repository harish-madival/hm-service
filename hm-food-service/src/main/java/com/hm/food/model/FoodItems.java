package com.hm.food.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodItems {

	@NotBlank(message = "ItemName Should not be empty or null.")
	private String itemName;
	@NotBlank(message = "price Should not be empty or null.")
	private int price;
	@NotBlank(message = "type Should not be empty or null.")
	private String type;

}
