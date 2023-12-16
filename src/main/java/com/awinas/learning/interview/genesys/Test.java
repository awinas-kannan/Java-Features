package com.awinas.learning.interview.genesys;

import java.util.Arrays;
import java.util.List;

public class Test {

	public static void main(String[] args) {

		Integer[] arr = new Integer[] { 1, 1, 1, 1, 1 };
		List<Integer> arrList = Arrays.asList(1, 3, 8, 4, 5);

		arrList.stream().sorted((a, b) -> {
			return a < b ? 1 : -1;
		}).forEach(System.out::println);

	}
}
