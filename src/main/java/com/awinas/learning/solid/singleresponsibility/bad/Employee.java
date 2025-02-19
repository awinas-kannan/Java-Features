package com.awinas.learning.solid.singleresponsibility.bad;

/*
 * 

	Why is this a bad design?
	❌ Multiple Responsibilities:
	
	Managing employee data (name, salary).
	Generating salary slips.
	Storing employee data in the database.
	
	❌ Violates SRP:
	If we need to change how salaries are calculated, how salary slips are generated, or how data is saved in the database, 
	this single class will need modifications—introducing unnecessary coupling.
	
 */

class Employee {
	private String name;
	private double salary;

	public Employee(String name, double salary) {
		this.name = name;
		this.salary = salary;
	}

	// Function to generate salary slip
	public void generateSalarySlip() {
		System.out.println("Generating salary slip for: " + name);
	}

	// Function to save employee data to a database
	public void saveToDatabase() {
		System.out.println("Saving employee data to database...");
	}
}
