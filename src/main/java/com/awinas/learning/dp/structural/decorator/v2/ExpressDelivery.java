package com.awinas.learning.dp.structural.decorator.v2;

public class ExpressDelivery extends ProductDecorator {
	public ExpressDelivery(Product product) {
		super(product);
	}

	public String getDescription() {
		return product.getDescription() + ", with Express Delivery";
	}

	public double getPrice() {
		return product.getPrice() + 100.0;
	}
}
