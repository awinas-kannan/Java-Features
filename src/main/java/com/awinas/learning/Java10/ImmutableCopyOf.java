package com.awinas.learning.Java10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ImmutableCopyOf {
	
	/*
	 * Case 1: If it(copyOf Method) takes an immutable object of collection then it returns the
	 * object of Unmodifiable collection. That is an immutable collection because it
	 * internally creates a new copy of the original collection. If we make any
	 * change in the original collection of immutable collections, those changes
	 * don’t reflect the immutable collection.
	 * 
	 * Case 2: When the original collection is already immutable, then the copyOf()
	 * method doesn’t create a new copy it simply returns a reference to the
	 * original collection. Even collection-sharing same reference but still we
	 * can’t make any change because the original collection cannot be changed,
	 * there is no need to make a copy of it.
	 * 
	 */
	
	public static void main(String[] args) {
		// Creating an object of mutable list
		List<String> mutableListOfSports = new ArrayList<String>();
		mutableListOfSports.add("Cricket");
		mutableListOfSports.add("Hockey");

		// Creating an object of mutable Set
		Set<String> mutableSetOfData = new HashSet<String>();
		mutableSetOfData.add("JavaGoal.com");
		mutableSetOfData.add("Website");

		// Creating an object of mutable Set
		Map<Integer, String> mutableMapOfSports = new HashMap<Integer, String>();
		mutableMapOfSports.put(1, "Cricket");
		mutableMapOfSports.put(2, "Hockey");

		// Creating an immutable ArrayList from mutable ArrayList
		List<String> immutableListOfSports = List.copyOf(mutableListOfSports);
		// Creating an immutable HashSet from mutable HashSet
		Set<String> immutableSetOfData = Set.copyOf(mutableSetOfData);
		// Creating an immutable HashMap from mutable HashMap
		Map<Integer, String> immutableMapOfSports = Map.copyOf(mutableMapOfSports);

		System.out.println("are both list same: " + (mutableListOfSports == immutableListOfSports));
		System.out.println("are both Set same: " + (mutableSetOfData == immutableSetOfData));
		System.out.println("are both Map same: " + (mutableMapOfSports == immutableMapOfSports));

		// Creating an immutable ArrayList from immutable ArrayList
		List<String> immutableListOfSports1 = List.copyOf(immutableListOfSports);
		// Creating an immutable HashSet from immutable HashSet
		Set<String> immutableSetOfData1 = Set.copyOf(immutableSetOfData);
		// Creating an immutable HashMap from immutable HashMap

		Map<Integer, String> immutableMapOfSports1 = Map.copyOf(immutableMapOfSports);
		System.out.println("are both list same: " + (immutableListOfSports == immutableListOfSports1));
		System.out.println("are both Set same: " + (immutableSetOfData == immutableSetOfData1));
		System.out.println("are both Map same: " + (immutableMapOfSports == immutableMapOfSports1));
	}
}
