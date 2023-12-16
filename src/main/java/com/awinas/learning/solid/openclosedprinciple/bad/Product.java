package com.awinas.learning.solid.openclosedprinciple.bad;

public class Product {

	private String type;

	private int quantity;

	public Product(String type, int quantity) {
		super();
		this.type = type;

		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
