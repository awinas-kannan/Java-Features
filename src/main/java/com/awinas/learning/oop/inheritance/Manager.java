package com.awinas.learning.oop.inheritance;

import java.util.List;

public class Manager extends Employee {

	public Long id = 20l;

	private List<Employee> subordinates;

	public Long getId() {
		return id;
	}

	public List<Employee> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(List<Employee> subordinates) {
		this.subordinates = subordinates;
	}

	public String getWho() {
		return "Manager";
	}

	@Override
	public String toString() {
		return "Manager [subordinates=" + subordinates + ", details=" + super.toString() + "]";
	}
}