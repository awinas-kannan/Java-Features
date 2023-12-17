package com.awinas.learning.Java11;

import java.util.Arrays;
import java.util.List;

public class CollecitonToArr {

	public static void main(String[] args) {
		/*
		 * Before Java 11, converting a collection to array was not straightforward.
		 * Java 11 makes the conversion more convenient.
		 */

		List<String> names = List.of("1", "2", "3", "4");
		String[] namesArr1 = names.toArray(new String[names.size()]); // Before Java 11
		String[] namesArr2 = names.toArray(String[]::new);

		System.out.println(Arrays.toString(namesArr1));
		System.out.println(Arrays.toString(namesArr2));
	}

}
