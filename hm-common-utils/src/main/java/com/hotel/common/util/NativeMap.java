package com.hotel.common.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class NativeMap<K, V> extends LinkedHashMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int MAX_ELEMENT;

	public NativeMap(int capacity) {
		super();
		MAX_ELEMENT = capacity;
	}
	
	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return size()>MAX_ELEMENT;
		
	}
	

}
