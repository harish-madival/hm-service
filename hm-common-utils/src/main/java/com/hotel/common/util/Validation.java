package com.hotel.common.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * Common validation helpers for required request values.
 */
public final class Validation {

	private Validation() {
		// Utility class
	}

	/**
	 * Returns {@code true} when the supplied value is present and, where
	 * applicable, contains a value.
	 */
	public static boolean isRequired(Object value) {
		if (value == null) {
			return false;
		}
		if (value instanceof CharSequence) {
			return !isBlank((CharSequence) value);
		}
		if (value instanceof Collection<?>) {
			return !((Collection<?>) value).isEmpty();
		}
		if (value instanceof Map<?, ?>) {
			return !((Map<?, ?>) value).isEmpty();
		}
		if (value.getClass().isArray()) {
			return Array.getLength(value) > 0;
		}
		return true;
	}

	public static boolean isValidObject(Object value) {
		return value != null;
	}

	public static boolean isNull(Object value) {
		return value == null;
	}

	public static boolean isNotNull(Object value) {
		return value != null;
	}

	public static boolean isBlank(CharSequence value) {
		if (value == null || value.length() == 0) {
			return true;
		}
		for (int index = 0; index < value.length(); index++) {
			if (!Character.isWhitespace(value.charAt(index))) {
				return false;
			}
		}
		return true;
	}

	public static boolean hasText(CharSequence value) {
		return !isBlank(value);
	}

	public static boolean isEmpty(Collection<?> value) {
		return value == null || value.isEmpty();
	}

	public static boolean isNotEmpty(Collection<?> value) {
		return !isEmpty(value);
	}

	public static boolean isEmpty(Map<?, ?> value) {
		return value == null || value.isEmpty();
	}

	public static boolean isNotEmpty(Map<?, ?> value) {
		return !isEmpty(value);
	}

	public static boolean isEmpty(Object[] value) {
		return value == null || value.length == 0;
	}

	public static boolean isNotEmpty(Object[] value) {
		return !isEmpty(value);
	}
}
