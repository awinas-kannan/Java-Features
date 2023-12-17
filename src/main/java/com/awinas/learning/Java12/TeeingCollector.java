package com.awinas.learning.Java12;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TeeingCollector {
	public static void main(String[] args) {

		List<Integer> intList = new Random().ints(10, 1, 100).boxed().toList();
		System.out.println(intList);

		/*
		 * Find Min and Max Number in list and find the difference
		 * 
		 * downstream1 - the first downstream collector downstream2 - the second
		 * downstream collector merger - the function which merges two results into the
		 * single one
		 * 
		 * returns - a Collector which aggregates the results of two supplied
		 * collectors.
		 */

		Integer diff = intList.stream()
				.collect(Collectors.teeing(
						Collectors.minBy(Comparator.comparing(Integer::intValue)),
						Collectors.maxBy(Comparator.comparing(Integer::intValue)), 
						(v1, v2) -> {
							int min = v1.get();
							int max = v2.get();
							System.out.println(min);
							System.out.println(max);
							return max - min;
						}));
		System.out.println(diff);

		var result = intList.stream()
				.collect(
						Collectors.teeing(
								Collectors.filtering(e -> e > 50, Collectors.toList()),
								Collectors.filtering(e -> e > 50, Collectors.counting()), 
								(list, count) -> {
									var map = new HashMap<>();
									map.put("list", list);
									map.put("count", count);
									return map;
				}));

		System.out.println(result);
	}
}
