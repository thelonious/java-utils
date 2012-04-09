/**
 * Copyright 2012, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.utils;

/**
 * ArrayUtils
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class ArrayUtils {
	/**
	 * A simple helper method that determines if an array value is empty. This
	 * treats null values as empty.
	 * 
	 * @param array
	 *            The array to test
	 * @return Returns a boolean
	 */
	public static <T> boolean isEmpty(T[] array) {
		return (array == null || array.length == 0);
	}

	/**
	 * ArrayUtils
	 */
	private ArrayUtils() {
	}
}
