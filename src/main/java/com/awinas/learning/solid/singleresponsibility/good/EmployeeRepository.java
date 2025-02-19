package com.awinas.learning.solid.singleresponsibility.good;

class EmployeeRepository {
	public void saveToDatabase(Employee employee) {
		System.out.println("Saving employee " + employee.getName() + " to database...");
	}
}
