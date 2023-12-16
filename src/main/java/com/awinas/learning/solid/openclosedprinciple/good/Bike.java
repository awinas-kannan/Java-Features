package com.awinas.learning.solid.openclosedprinciple.good;

public class Bike extends Product {

	public Bike(String type, int quantity) {
		super(type, quantity);
	}

	public int calculateTotalPrice() {
		return this.getQuantity() * 2000;
	}
}
