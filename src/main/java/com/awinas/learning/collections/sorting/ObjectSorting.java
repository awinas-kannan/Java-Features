package com.awinas.learning.collections.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


//https://howtodoinjava.com/java/sort/sort-arraylist-objects-comparable-comparator/

public class ObjectSorting {

	public static void main(String[] args) {
		Employee e1 = new Employee(1, "Awi", "Kann", 34);
		Employee e5 = new Employee(5, "Awi", "M R", 34);
		Employee e2 = new Employee(2, "Tam", "Sel", 30);
		Employee e3 = new Employee(3, "Vik", "K", 31);
		Employee e4 = new Employee(4, "Kar", "Keyan", 25);

		List<Employee> employees = new ArrayList<Employee>();
		employees.add(e2);
		employees.add(e3);
		employees.add(e1);
		employees.add(e4);
		employees.add(e5);

		System.out.println(employees);

		System.out.println("*****************COMPARIBLE***********");
		Collections.sort(employees);

		System.out.println(employees);

		System.out.println("*****************COMPARATOR***********");
		Collections.sort(employees, new FirstNameSorter());

		System.out.println(employees);

		System.out.println("*****************Comparator in Java 8***********");

		employees.sort(Comparator.comparing(e -> e.getFirstName()));
		System.out.println(employees);

		// OR you can use below
		employees.sort(Comparator.comparing(Employee::getFirstName));

		// Sort all employees by first name in reverse order
		Comparator<Employee> comparator = Comparator.comparing(e -> e.getFirstName());
		employees.sort(comparator.reversed());

		// Sorting on multiple fields; Group by.
		Comparator<Employee> groupByComparator = Comparator.comparing(Employee::getFirstName)
				.thenComparing(Employee::getLastName);
		employees.sort(groupByComparator);
		System.out.println(employees);

	}

}
