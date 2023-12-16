package com.awinas.learning.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class MyLinkedHashSet {

	public static void main(String[] args) {
		LinkedHashSet<String> LinkedHashSet = new LinkedHashSet<>();

		// 2. Add elements to LinkedHashSet
		LinkedHashSet.add("A");
		LinkedHashSet.add("B");
		LinkedHashSet.add("C");
		LinkedHashSet.add("D");
		LinkedHashSet.add("E");

		System.out.println(LinkedHashSet);

		// 3. Check if element exists
		boolean found = LinkedHashSet.contains("A"); // true
		System.out.println(found);

		// 4. Remove an element
		LinkedHashSet.remove("D");

		// 5. Iterate over values
		Iterator<String> itr = LinkedHashSet.iterator();

		while (itr.hasNext()) {
			String value = itr.next();

			System.out.println("Value: " + value);
		}

		//
		System.out.println(" Convert LinkedHashSet to Array Example");
		String[] values = new String[LinkedHashSet.size()];

		LinkedHashSet.toArray(values);

		System.out.println(Arrays.toString(values));

		//
		System.out.println("Convert LinkedHashSet to ArrayList Example");
		List<String> valuesList = LinkedHashSet.stream().collect(Collectors.toList());

		System.out.println(valuesList);
	}

}
