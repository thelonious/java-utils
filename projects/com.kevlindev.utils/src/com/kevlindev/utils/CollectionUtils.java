/**
 * Copyright 2012, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * CollectionUtils
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class CollectionUtils {
	/**
	 * Create a new array list from the specified list of items. It is expected
	 * that all items are of the same type.
	 * 
	 * @param items
	 *            The items to add to the list
	 * @return
	 */
	public static <T> List<T> createList(T... items) {
		List<T> result;

		if (items != null && items.length > 0) {
			result = new ArrayList<T>(items.length);

			for (int i = 0; i < items.length; i++) {
				result.add(items[i]);
			}
		} else {
			result = Collections.emptyList();
		}

		return result;
	}

	public static <T> boolean isEmpty(Collection<T> collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * CollectionUtils
	 */
	private CollectionUtils() {
	}
}
