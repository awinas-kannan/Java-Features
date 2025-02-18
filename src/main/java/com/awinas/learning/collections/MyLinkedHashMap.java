package com.awinas.learning.collections;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyLinkedHashMap {

	public static void main(String[] args) {
		Map<Integer, String> pairs = new LinkedHashMap<>();

		pairs.put(1, "A");
		pairs.put(2, "B");
		pairs.put(3, "C");
		pairs.put(4, "D");

		pairs.entrySet().stream().forEach((entry) -> {
			System.out.println("Key " + entry.getKey() + " Value " + entry.getValue());
		});
		pairs.forEach((key, value) -> {
			System.out.println("Key:" + key + ", Value:" + value);
		});

		System.out.println("**********Access order true**********************");
		System.out.println("Most recent eleent is displayed last ");
		Map<Integer, String> accessorderMap = new LinkedHashMap<>(2, .75f, true);

		accessorderMap.put(1, "A");
		accessorderMap.put(2, "B");
		accessorderMap.put(4, "D");
		accessorderMap.put(3, "C");
		accessorderMap.put(4, "D");
		accessorderMap.put(2, "B");
		accessorderMap.put(4, "D");

		accessorderMap.forEach((key, value) -> {
			System.out.println("Key:" + key + ", Value:" + value);
		});
		// Access 3rd pair
		System.out.println(accessorderMap.get(3));

		// Access 1st pair
		System.out.println(accessorderMap.getOrDefault(2, "oops"));

		accessorderMap.forEach((key, value) -> {
			System.out.println("Key:" + key + ", Value:" + value);
		});

		System.out.println("********************");

		System.out.println(accessorderMap.getOrDefault(100, "oops"));
		System.out.println(pairs.containsKey(1)); // containsKey method

		System.out.println(pairs.containsValue("B")); // containsValue method

	}

}
