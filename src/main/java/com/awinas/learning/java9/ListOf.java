package com.awinas.learning.Java9;

import java.util.ArrayList;
import java.util.List;

public class ListOf {
	public static void main(String[] args) {
		String[] strArr = new String[3];
		strArr[0] = "0";
		strArr[1] = "1";
		strArr[2] = "2";
		List<String> arrListof = List.of(strArr);

		List<String> strList = new ArrayList<>();
		strList.add("5");
		List<List<String>> arrListof1 = List.of(strList);
		strList.add("6");
		System.out.println(arrListof);
		System.out.println(arrListof1);

	}
}
