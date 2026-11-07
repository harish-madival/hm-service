package com.hotel.common.util;

public class NativeCacheImpl<K, V> implements NativeCache<K, V> {
	
	private NativeMap<K, V> map;
	
	public NativeCacheImpl(int capacity) {
		map = new NativeMap<>(10);
	}

	@Override
	public V find(K key) {
		V value = map.get(key);
		map.remove(key);
		map.put(key, value);
		return value;
	}

	@Override
	public void set(K key, V value) {
		find(key);
		map.put(key, value);
	}

}
