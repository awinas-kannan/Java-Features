package com.awinas.learning.solid.openclosedprinciple.good;

public class Toy extends Product {

	public Toy(String type, int quantity) {
		super(type, quantity);
	}

	public int calculateTotalPrice() {
		return this.getQuantity() * 50;
	}
}
