package com.awinas.learning.interview.optum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirstAndLast5InList {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>(
				Arrays.asList(3, 5, 6, 2, 3, 7, 8, 1, 9, 4, 5, 3, 6, 5, 44, 33, 22, 77, 100));

		System.out.println(list.size());
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < list.size() - 1 - i; j++) {
				if (list.get(j) > list.get(j + 1)) {
					swap(list, j, j + 1);
				}
			}
		}
		System.out.println(list);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < list.size() - 1 - i; j++) {
				if (list.get(j) < list.get(j + 1)) {
					swap(list, j, j + 1);
				}
			}
		}
		System.out.println(list);

	}

	private static void swap(List<Integer> list, int i, int j) {
		int x = list.get(j);
		int y = list.get(i);
		list.set(j, y);
		list.set(i, x);

	}

}
