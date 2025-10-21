package com.awinas.learning.oop.encapsulation;

import java.time.LocalDate;
import java.time.Period;

class Customer {
	private LocalDate dob; // private variable

	public Customer(LocalDate dob) {
		this.dob = dob;
	}

	// Encapsulated getter: logic hidden inside method
	public int getAge() {
		return Period.between(dob, LocalDate.now()).getYears();
	}

	// Optional setter
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
}

public class DobEncapsulation {
	public static void main(String[] args) {
		Customer c = new Customer(LocalDate.of(2000, 5, 20));
		System.out.println("Age: " + c.getAge()); // Logic hidden inside getAge()
	}
}
