package com.awinas.learning.interview.nielsen;

import java.util.HashMap;
import java.util.Map;

public class NoofTimes {

	public static void main(String[] args) {
		Integer[] arr = new Integer[] { 5, 4, 3, 2, 1, 3, 4, 6, 2, 3 };

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();

		for (int x : arr) {

			if (map.get(x) != null) {
				map.put(x, map.get(x) + 1);
			} else {
				map.put(x, 1);
			}
		}

		System.out.println(map);
	}

}
