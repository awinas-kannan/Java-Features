package com.awinas.learning.oop;

import java.util.Date;

//LocalDate.of(2015, 02, 20);
public class OopsMain {
	public static void main(String[] args) {
		Department dept1 = new Department(1, "core banking");
//		Department dept2 = new Department(2, "crediti");
//		Department dept3 = new Department(3, "invesemnets");
//		Department dept4 = new Department(4, "payments");
		Employee e1 = new Employee("Awi", 24, new Date(), dept1);

		Employee e2 = e1;

		System.out.println(e1);
		System.out.println(e1.hashCode());
		System.out.println(e2);
		System.out.println(e2.hashCode());
		e2.setAge(25);
		e2.setName("Awinas");
		System.out.println(e1);
		System.out.println(e1.hashCode());
		System.out.println(e2);
		System.out.println(e2.hashCode());

	}

}
