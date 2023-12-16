package com.awinas.learning.solid.openclosedprinciple.good;

public class Car extends Product {

	public Car(String type, int quantity) {
		super(type, quantity);
	}

	public int calculateTotalPrice() {
		return this.getQuantity() * 1000;
	}
}
