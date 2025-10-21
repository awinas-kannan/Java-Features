package com.awinas.learning.coreconcepts.immutablecollections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ImmutableListCreation {

	public static void main(String[] args) {

		List<String> arrList = new ArrayList<>();
		arrList.add("1");
		arrList.add("2");
		arrList.add("3");
		arrList.add("4");
		arrList.add("5");

		String[] strArr = new String[3];
		strArr[0] = "0";
		strArr[1] = "1";
		strArr[2] = "2";
		System.out.println(Arrays.asList(strArr));
		List<String> arrAsList = Arrays.asList(strArr);
//		arrAsList.add("5"); // java.lang.UnsupportedOperationException
		arrAsList.set(0, "asList Set method");
		System.out.println(strArr[0]);
		
		List<String> arrListUnModified = Collections.unmodifiableList(arrList);
		List<String> arrListof = List.of(strArr);
		List<String> arrCopyof = List.copyOf(arrList);

//		arrListUnModified.add("New");
//		arrListof.add("Try Add");
//		arrCopyof.add("Try add");

		print(arrList, arrAsList, arrListUnModified, arrListof, arrCopyof);

		
		
		strArr[2] = "set att val";
		arrList.add("100");

		print(arrList, arrAsList, arrListUnModified, arrListof, arrCopyof);

	}

	private static void print(List<String> arrList, List<String> arrAsList, List<String> arrListUnModified,
			List<String> arrListof, List<String> arrCopyof) {
		System.out.println("arrList" + arrList);
		System.out.println("arrAsList" + arrAsList);
		System.out.println("arrListUnModified" + arrListUnModified);
		System.out.println("arrListof" + arrListof);
		System.out.println("arrCopyof" + arrCopyof);
		System.out.println("###########################");
	}
}
