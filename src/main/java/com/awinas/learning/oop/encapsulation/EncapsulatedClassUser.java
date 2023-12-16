package com.awinas.learning.oop.encapsulation;

public class EncapsulatedClassUser {

	public static void main(String[] args) {

		Igetters getDet = RetriveEmployee.getInstance().retrieveEmployeeDetails();
		System.out.println(getDet.getAge());
		System.out.println(getDet.getId());
		System.out.println(getDet.getName());

	}

}
