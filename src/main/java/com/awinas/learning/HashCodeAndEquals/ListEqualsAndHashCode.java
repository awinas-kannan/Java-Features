package com.awinas.learning.HashCodeAndEquals;

import java.util.ArrayList;
import java.util.List;

/*while comparing two list of predefined object 
 * or user defined object in EQUALS method , 
 * it also checks for the order of elements*/
public class ListEqualsAndHashCode {

	public static void main(String[] args) {

		userDefinedObject();

		predefinedObjectList();

	}

	private static void userDefinedObject() {
		System.out.println("------userDefinedObject---------");

		// Uncomment equals and hashcode in Employee obj

		Employee emp1 = new Employee(1, "raj", "25");
		Employee emp2 = new Employee(2, "manoj", "35");
		Employee emp3 = new Employee(3, "awinas", "23");
		Employee emp4 = new Employee(1, "raj", "25");
		Employee emp5 = new Employee(1, "raj", "27");

		List<Employee> empList = new ArrayList<>();
		empList.add(emp1);
		empList.add(emp2);
		empList.add(emp3);
		empList.add(emp4);
		empList.add(emp5);

		List<Employee> empList1 = new ArrayList<>(empList);
		List<Employee> empList2 = new ArrayList<>(empList);
		empList2.add(emp5);

		System.out.println(empList.hashCode());
		System.out.println(empList1.hashCode());
		System.out.println(empList2.hashCode());
		// When equals method uncommented // TRUE // Employee order is same //Id only
		// checked in equals method
		// When equals method is commented also TRUE //
		// new ArrayList<>(empList) -- > SHALLOW copy of employee // employee ref is
		// same
		System.out.println(empList.equals(empList1)); // true

		// Shallow copy proof
		System.out.println("Shallow copy PROOF");
		empList.get(1).setEmpName("New Name");
		System.out.println(empList1.get(1).getEmpName());

		// When equals method uncommented or commented // FALSE // Has one extra
		// employee
		System.out.println(empList1.equals(empList2));

		Employee emp6 = new Employee(1, "raj", "25");
		Employee emp7 = new Employee(2, "manoj", "35");
		Employee emp8 = new Employee(3, "awinas", "23");
		Employee emp9 = new Employee(1, "raj", "25");
		Employee emp10 = new Employee(1, "raj", "27");

		List<Employee> empList3 = new ArrayList<>();
		empList3.add(emp6);
		empList3.add(emp7);
		empList3.add(emp8);
		empList3.add(emp9);
		empList3.add(emp10);

		// FALSE When Equals method is commented //
		// Because while  emp1 != emp6 (references are checked)

		// TRUE When Equal method is uncommented //Only EMployee ID Checked

		System.out.println(empList.equals(empList3));

	}

	private static void predefinedObjectList() {
		System.out.println("------predefinedOnjectList---------");
		ArrayList<Integer> integetList = new ArrayList<>();
		integetList.add(1);
		integetList.add(2);
		integetList.add(3);
		integetList.add(4);
		integetList.add(1);
		integetList.add(null);

		List<Integer> integetList2 = new ArrayList<>(integetList);
		integetList2.contains(1);

		integetList.equals(integetList2);
		System.out.println("equals  " + integetList.equals(integetList2));
		System.out.println(integetList);
		integetList.remove(null);
		System.out.println(integetList);

		System.out.println("-----------------");

		List<Integer> integetList3 = new ArrayList<>();

		integetList3.add(1);
		integetList3.add(2);
		integetList3.add(3);
		integetList3.add(4);
		integetList3.add(1);
		System.out.println(integetList.equals(integetList3));
	}

}
