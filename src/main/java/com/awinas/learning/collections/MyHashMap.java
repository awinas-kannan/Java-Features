package com.awinas.learning.collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

//https://howtodoinjava.com/java-hashmap/

//hash vaue is zero for null key

//https://stackoverflow.com/questions/948194/difference-between-java-enumeration-and-iterator#:~:text=1)%20The%20main%20difference%20between,not%20have%20remove()%20method.&text=Iterator%20is%20not%20a%20legacy%20interface.

//Load factor
//https://www.geeksforgeeks.org/load-factor-in-hashmap-in-java-with-examples/
//https://stackoverflow.com/questions/10901752/what-is-the-significance-of-load-factor-in-hashmap
//Index = hashcode(key) & (ArraySize – 1)

public class MyHashMap {

	public static void main(String[] args) {

		String[] aa = new String[11];
		System.out.println("lenght->" + aa.length);
		String tempKey = "Awinas";
		String tempKey1 = "AwinasKannan";
		String tempKey12 = "AwinasKannanMR";
		System.out.println(tempKey.hashCode());
		System.out.println(tempKey.hashCode() >>> 16);
		System.out.println(tempKey.hashCode() ^ tempKey.hashCode() >>> 16);
		int h;
		System.out.println("Hash Code " + ((h = tempKey.hashCode()) ^ (h >>> 16)));
		System.out.println(0x7FFFFFFF);
		System.out.println("Index - >" + (((h = tempKey.hashCode()) ^ (h >>> 16)) & (5)));
		System.out.println("Index - >" + (((h = tempKey1.hashCode()) ^ (h >>> 16)) & (5)));
		System.out.println("Index - >" + (((h = tempKey12.hashCode()) ^ (h >>> 16)) & (5)));
		System.out.println("Index - >" + (tempKey.hashCode() & 0x7FFFFFFF) % 11);
		Map<Integer, String> map = new HashMap<>();

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

		Iterator<Integer> itr = map.keySet().iterator();

		while (itr.hasNext()) {
			Integer key = itr.next();
			String value = map.get(key);

			System.out.println("The key is :: " + key + ", and value is :: " + value);
		}

		System.out.println("//Iterate over entries set");

		Iterator<Entry<Integer, String>> entryIterator = map.entrySet().iterator();

		while (entryIterator.hasNext()) {
			Entry<Integer, String> entry = entryIterator.next();

			System.out.println("The key is :: " + entry.getKey() + ", and value is :: " + entry.getValue());
		}

	}

}
