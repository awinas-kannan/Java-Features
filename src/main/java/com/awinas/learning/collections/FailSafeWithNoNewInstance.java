package com.awinas.learning.collections;

import java.util.Iterator;

import java.util.concurrent.ConcurrentHashMap;

//Java program to illustrate
//Fail-Safe Iterator which
//does not create separate copy

// concurrent modification is possible
public class FailSafeWithNoNewInstance {
	public static void main(String[] args) {

		// Creating a ConcurrentHashMap
		ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<String, Integer>();

		map.put("ONE", 1);
		map.put("TWO", 2);
		map.put("THREE", 3);
		map.put("FOUR", 4);

		// Getting an Iterator from map
		Iterator it = map.keySet().iterator();

		while (it.hasNext()) {
			String key = (String) it.next();
			System.out.println(key + " : " + map.get(key));

			// This will reflect in iterator.
			// Hence, it has not created separate copy

			map.put("SEVEN", 7);
		}

		System.out.println(map);
		// 7 WILL BE PRINTED
		// WE MAKE MODIFICATION IN ORIGINAL COLLECTION
	}
}