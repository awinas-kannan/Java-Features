package com.awinas.learning.collections.sorting;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//https://howtodoinjava.com/java/sort/java-sort-map-by-key/
//https://howtodoinjava.com/java/sort/java-sort-map-by-values/

public class MapSorting {

	public static void main(String[] args) {
		Map<String, Integer> unSortedMap = getUnSortedMap();

		System.out.println("Unsorted Map : " + unSortedMap);

		// LinkedHashMap preserve the ordering of elements in which they are inserted
		Map<String, Integer> sortedMap = new LinkedHashMap<>();

		// use stream to order and store it in linkedhashmap

		System.out.println("Sort by key -> lambda expression");

		unSortedMap.entrySet().stream().sorted((a, b) -> a.getKey().compareTo(b.getKey()))
				.forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
		System.out.println(sortedMap);

		System.out.println("Sort by Vaue -> Map.Entry.comparingByValue()");
		Map<String, Integer> sortedMap1 = new LinkedHashMap<>();
		unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.forEachOrdered(x -> sortedMap1.put(x.getKey(), x.getValue()));
		System.out.println(sortedMap1);

		System.out.println("Reverse Sorting ");
		System.out.println("Reverse Sort by key -> lambda expression");
		LinkedHashMap<String, Integer> reverseSortedMapbyKey = new LinkedHashMap<>();

		unSortedMap.entrySet().stream().sorted((a, b) -> b.getKey().compareTo(a.getKey()))
				.forEachOrdered(x -> reverseSortedMapbyKey.put(x.getKey(), x.getValue()));

		System.out.println("Reverse Sorted Map   : " + reverseSortedMapbyKey);
		System.out.println("Reverse by Vaue -> Map.Entry.comparingByValue()");
		// Use Comparator.reverseOrder() for reverse ordering
		LinkedHashMap<String, Integer> reverseSortedMapbyValue = new LinkedHashMap<>();
		unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEachOrdered(x -> reverseSortedMapbyValue.put(x.getKey(), x.getValue()));

		System.out.println("Reverse Sorted Map   : " + reverseSortedMapbyValue);
	}

	private static Map<String, Integer> getUnSortedMap() {
		Map<String, Integer> unsortMap = new HashMap<>();
		unsortMap.put("alex", 1);
		unsortMap.put("david", 2);
		unsortMap.put("elle", 3);
		unsortMap.put("charles", 4);
		unsortMap.put("brian", 5);
		return unsortMap;
	}

}
