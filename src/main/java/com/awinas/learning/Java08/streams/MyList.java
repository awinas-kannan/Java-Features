package com.awinas.learning.Java08.streams;

import java.util.ArrayList;
import java.util.List;

import com.awinas.learning.Java08.Employee;

public class MyList {
	protected static final List<String> memberNames = new ArrayList<>();
	protected static final List<Integer> list = new ArrayList<>();
	protected static final List<Integer> list2 = new ArrayList<>();
	protected static final List<Integer> list3 = new ArrayList<>();
	protected static final List<Integer> longlist = new ArrayList<>();
	protected static final List<List<Integer>> listOflist = new ArrayList<>();
	protected static final List<Employee> employeeList = new ArrayList<>();
	static {

		memberNames.add("Aman");
		memberNames.add("Aman");
		memberNames.add("Amitabh");
		memberNames.add("Amitabh");
		memberNames.add("Amitabh");
		memberNames.add("Shekhar");
		memberNames.add("Rahul");
		memberNames.add("Shahrukh");
		memberNames.add("Salman");
		memberNames.add("Yana");
		memberNames.add("Lokesh");

		for (int i = 1; i <= 10; i++) {
			list.add(i);
		}
		for (int i = 11; i <= 20; i++) {
			list2.add(i);
		}
		for (int i = 21; i <= 30; i++) {
			list3.add(i);
		}
		for (int i = 1; i <= 1000; i++) {
			longlist.add(i);
		}

		listOflist.add(list);
		listOflist.add(list2);
		listOflist.add(list3);

		for (int i = 1; i <= 5; i++) {
			employeeList.add(new Employee(i, "awinas", String.valueOf(i + 20)));
			employeeList.add(new Employee(i + 5, "kannan", String.valueOf(i + 20)));
			employeeList.add(new Employee(i + 10, "awinas kannan", String.valueOf(i + 20)));
		}

		for (int i = 1; i <= 5; i++) {
			employeeList.add(new Employee(i + 15, "awinas", String.valueOf(i + 20)));
			employeeList.add(new Employee(i + 20, "kannan", String.valueOf(i + 20)));
			employeeList.add(new Employee(i + 25, "awinas kannan", String.valueOf(i + 20)));
		}
	}

}
