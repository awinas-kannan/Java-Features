package com.awinas.learning.garbagecollection;

//Correct code to count number
// of employees excluding interns.
class Employee {
	private int ID;
	private String name;
	private int age;
	private static int nextId = 1;

	// it is made static because it
	// is keep common among all and
	// shared by all objects
	public Employee(String name, int age) {
		this.name = name;
		this.age = age;
		this.ID = nextId++;
	}

	public void show() {
		System.out.println("Id=" + ID + " Name=" + name + " Age=" + age);
	}

	public void showNextId() {
		System.out.println("Next employee id will be=" + nextId);
	}

	// this method will be call for each obj once
	@Override
	protected void finalize() {
		--nextId;
		System.out.println("finalization :: " + this.toString());

		// In this case,
		// gc will call finalize()
		// for 2 times for 2 objects.
	}

	@Override
	public String toString() {
		return "Employee [ID=" + ID + ", name=" + name + ", age=" + age + "]";
	}

}