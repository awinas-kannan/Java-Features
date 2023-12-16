package com.awinas.learning;

import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

//true – if variable is instance of specified class, it’s parent class or implement specified
//interface or it’s parent interface
//false – if variable is not instance of the class or implement the interface; 
//or variable is null
public class InstanceOf {

	public static void main(String[] args) {
		List<String> arrayList = new ArrayList<>();

		System.out.println(arrayList instanceof ArrayList); // true

		System.out.println(arrayList instanceof AbstractList); // true
		System.out.println(arrayList instanceof AbstractCollection);

		System.out.println(arrayList instanceof List); // true

		System.out.println(arrayList instanceof Collection); // true

		System.out.println(null instanceof ArrayList); // false

		System.out.println(arrayList instanceof LinkedList); // false

		System.out.println("*************With Arrays*****************");

		int[] intArr = new int[3];
		float[] floatArr = new float[3];

		Integer[] intObjArr = new Integer[3];
		Float[] floatObjArr = new Float[3];
		String[] stringArr = new String[3];

		System.out.println(intArr instanceof Object); // true
		System.out.println(intArr instanceof int[]); // true

		System.out.println(floatArr instanceof Object); // true
		System.out.println(floatArr instanceof float[]); // true

		System.out.println(intObjArr instanceof Object); // true
		System.out.println(intObjArr instanceof Object[]); // true
		System.out.println(intObjArr instanceof Integer[]); // true
		System.out.println(intObjArr instanceof Number[]); // true

		System.out.println(floatObjArr instanceof Float[]); // true
		// System.out.println(floatObjArr instanceof float[]); // incompatible
		System.out.println(stringArr instanceof String[]); // true
	}

}
