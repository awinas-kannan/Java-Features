package com.awinas.learning.collections;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class MyTreeMap {

	public static void main(String[] args) {

		TreeMap<Integer, String> pairs = new TreeMap<>();

		pairs.put(2, "B");
		pairs.put(1, "A");
		pairs.put(3, "C");
		pairs.put(4, "C");
		pairs.put(0, "C");
		pairs.put(6, "C");

		String value = pairs.get(3); // get method

		System.out.println(value);

		value = pairs.getOrDefault(5, "oops"); // getOrDefault method

		System.out.println(value);

		// Iteration example
		Iterator<Integer> iterator = pairs.keySet().iterator();

		while (iterator.hasNext()) {
			Integer key = iterator.next();
			System.out.println("Key: " + key + ", Value: " + pairs.get(key));
		}

		// Remove example
		pairs.remove(3);
		System.out.println(pairs);

		System.out.println(pairs.containsKey(1)); // containsKey method

		System.out.println(pairs.containsValue("B")); // containsValue method

		System.out.println("***********");
		// greater than or equal to the key
		System.out.println(pairs.ceilingKey(5));
		System.out.println(pairs.ceilingKey(9));

		System.out.println("***********");
		// least key strictly greater
		System.out.println(pairs.higherKey(5));
		System.out.println(pairs.higherKey(9));
		System.out.println("***********");

		System.out.println(pairs.descendingMap());
		System.out.println("Another way of reverse order using Comparator in constructor");

		TreeMap<Integer, String> revOrderMap = new TreeMap<>(Collections.reverseOrder());

		revOrderMap.put(2, "B");
		revOrderMap.put(1, "A");
		revOrderMap.put(3, "C");

		System.out.println(revOrderMap);

		System.out.println("***********");
		Map<Integer, String> syncTreeMap = Collections.synchronizedSortedMap(new TreeMap<Integer, String>());

		syncTreeMap.put(1, "A");
		syncTreeMap.put(2, "B");
		syncTreeMap.put(3, "C");
	}

}
