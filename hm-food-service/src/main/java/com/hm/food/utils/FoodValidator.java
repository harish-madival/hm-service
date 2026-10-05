package com.hm.food.utils;

import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class FoodValidator {

	public static boolean isNotBlankNotNull(String key) {
		return StringUtils.isNotBlank(key);
		
	}
}
