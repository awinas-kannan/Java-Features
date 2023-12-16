package com.awinas.learning.collections;

import java.util.Hashtable;
import java.util.Map;

public class MyHashTable {

	public static void main(String[] args) {
		Map<Integer, String> map = new Hashtable<>();

		map.put(1, "A");
		map.put(2, "B");
		map.put(3, "C");
		map.put(4, "D");
		map.put(3, "E");
		map.put(5, "E");
		map.put(6, "E");
		map.put(null, "AA");
		map.put(null, "B");
		map.put(77, null);
		map.put(88, null);

		System.out.println(map);
		System.out.println(map.remove(0));
		System.out.println(map.remove(1));
		System.out.println(map);

	}

}
