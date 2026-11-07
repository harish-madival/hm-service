package com.hotel.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HmResponse {

	private String status;
	
	private Object data;
	
	
}
