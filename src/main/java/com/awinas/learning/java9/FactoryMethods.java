package com.awinas.learning.java9;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FactoryMethods {

	public static void main(String[] args) {
		/*
		 * Oracle Corp has introduced some convenient factory methods to create
		 * Immutable List, Set, Map and Map.Entry objects. These utility methods are
		 * used to create empty or non-empty Collection objects. In Java SE 8 and
		 * earlier versions, We can use Collections class utility methods like
		 * unmodifiableXXX to create Immutable Collection objects. For instance, if we
		 * want to create an Immutable List, then we can use
		 * Collections.unmodifiableList method. However, these
		 * Collections.unmodifiableXXX methods are a tedious and verbose approach. To
		 * overcome those shortcomings, Oracle Corp has added a couple of utility
		 * methods to List, Set and Map interfaces. List and Set interfaces have “of()”
		 * methods to create an empty or no-empty Immutable List or Set objects as shown
		 * below: Empty List Example
		 */
				
		List<String> s = List.of("Awinas", "Kannan", "M", "R");
		System.out.println(s);
		Set<String> set = Set.of("Awinas", "Kannan", "M", "R");
		System.out.println(set);

		List immutableList = List.of();
		List immutableList1 = List.of("one", "two", "three");
		System.out.println(immutableList);
		System.out.println(immutableList1);
		
		
		Map emptyImmutableMap = Map.of();
		Map<Integer,String> nonemptyImmutableMap = Map.of(1, "one", 2, "two", 3, "three");
		System.out.println(nonemptyImmutableMap);
		

	}
}
