package com.awinas.learning.Java08;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UnmodifiedList {

	public static void main(String[] args) {
		List<String> listOfSports = new ArrayList<>();
		listOfSports.add("Hockey");
		listOfSports.add("Cricket");
		listOfSports.add("Tennis");
		// Output : [Hockey, Cricket, Tennis]
		System.out.println("Before modification: " + listOfSports);
		List<String> unModifiableListOfSports = Collections.unmodifiableList(listOfSports);
		// Output : [Hockey, Cricket, Tennis]
		System.out.println("Before modification: " + unModifiableListOfSports);
		// Adding a new sport in list and will be reflected in unModifiableListOfSports
		listOfSports.add("Kabaddi");
		System.out.println("After modification original List: " + listOfSports); // Output : [Hockey, Cricket, Tennis, Kabaddi]
		System.out.println("After modification unModified List: " + unModifiableListOfSports); // Output : [Hockey, Cricket, Tennis,
																				// Kabaddi]
		// It gives run-time error because modifications are not allowed
		unModifiableListOfSports.set(1, "SET");
		unModifiableListOfSports.add("Wrestling");
	}
}
