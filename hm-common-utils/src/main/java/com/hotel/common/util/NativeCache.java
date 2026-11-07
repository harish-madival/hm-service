package com.hotel.common.util;

public interface NativeCache<K, V> {

	public V find(K key);

	public void set(K key, V value);
}
