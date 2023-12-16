package com.awinas.learning.collections;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

//https://howtodoinjava.com/java/collections/java-hashset/

//check hash set performance para

public class MyHashSet {

	public static void main(String[] args) {
		// 1. Create HashSet
		HashSet<String> hashSet = new HashSet<>();

		// 2. Add elements to HashSet
		hashSet.add("A");
		hashSet.add("B");
		hashSet.add("C");
		hashSet.add("D");
		hashSet.add("E");

		System.out.println(hashSet);

		// 3. Check if element exists
		boolean found = hashSet.contains("A"); // true
		System.out.println(found);

		// 4. Remove an element
		hashSet.remove("D");

		// 5. Iterate over values
		Iterator<String> itr = hashSet.iterator();

		while (itr.hasNext()) {
			String value = itr.next();

			System.out.println("Value: " + value);
		}

		// Convert HashSet to Array Example
		String[] values = new String[hashSet.size()];

		hashSet.toArray(values);

		System.out.println(Arrays.toString(values));

		// 5.3. Convert HashSet to ArrayList Example

		List<String> valuesList = hashSet.stream().collect(Collectors.toList());

		System.out.println(valuesList);
	}

}
