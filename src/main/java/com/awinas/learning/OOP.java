package com.awinas.learning;

import java.util.ArrayList;
import java.util.List;

public class OOP {


	public static void main(String[] args) {

		OOP obj = new OOP();
		List<String> actual = new ArrayList<>();
		actual.add("actual");
		obj.check(actual);
		System.out.println(actual);

	}

	public void check(List<String> formal) {
		List<String> temp = new ArrayList<>();
		temp.add("Awi");
		temp.add(null);
		temp.add("");
		temp.add("add all");
		formal.addAll(temp);
	}

}
