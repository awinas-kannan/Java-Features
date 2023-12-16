package com.awinas.learning.oop.encapsulation;

public class RetriveEmployee {

	private static final RetriveEmployee re = new RetriveEmployee();

	static RetriveEmployee getInstance() {
		return re;
	}

	Igetters retrieveEmployeeDetails() {

		Isetters ee = new EncapsulatedEmployee();
		ee.setId("1");
		ee.setName("awi");
		ee.setDob("27/05/97");

		return (Igetters) ee;
		
	}
}
