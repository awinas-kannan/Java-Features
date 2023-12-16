package com.awinas.learning;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListToMap {

	static int idx;
	static Map<Integer, Character> map = new HashMap<>();

	public static void main(String[] args) {
		List<Character> list = Arrays.asList('A', '1', 'S', '2', 'e', 'd', '3');

		list.stream().filter((e) -> {
			int x = (int) e;
			System.out.println(x);
			if (x >= 49 && x <= 58) {
				return true;
			}
			return false;
		}).forEach(x -> saveToMap(x));

		System.out.println(map);
	}

	private static void saveToMap(Character x) {
		map.put(idx++, x);
	}

	
}
