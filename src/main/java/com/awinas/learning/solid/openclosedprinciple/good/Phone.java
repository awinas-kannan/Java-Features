package com.awinas.learning.solid.openclosedprinciple.good;

public class Phone extends Product {

	public Phone(String type, int quantity) {
		super(type, quantity);
	}

	public int calculateTotalPrice() {
		return this.getQuantity() * 10000;
	}
}
