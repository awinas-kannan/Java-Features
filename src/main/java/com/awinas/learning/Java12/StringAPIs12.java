package com.awinas.learning.Java12;

import java.util.ArrayList;
import java.util.List;

/*
 * https://howtodoinjava.com/java12/new-features-enhancements/
 */
public class StringAPIs12 {

	public static void main(String[] args) {

		/*
		 * 
		 */
		var result = "line1\nline2\nline3".indent(4);
		System.out.println(result);
		result = "String apis in java 12".indent(4);
		System.out.println(result);

		/*
		 * The transform() method takes a String and transforms it into a new String
		 * with the help of a Function.
		 */

		var names = List.of("   Alex", "brian");
		var transformedNames = new ArrayList<>();
		for (String name : names) {
			String transformedName = name
					.transform(String::strip)
					.transform(String::toUpperCase);
			transformedNames.add(transformedName);
		}
		System.out.println(transformedNames);

	}

}
