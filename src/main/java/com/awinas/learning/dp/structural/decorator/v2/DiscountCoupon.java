package com.awinas.learning.dp.structural.decorator.v2;

public class DiscountCoupon extends ProductDecorator {
	public DiscountCoupon(Product product) {
		super(product);
	}

	public String getDescription() {
		return product.getDescription() + ", with 10% Discount";
	}

	public double getPrice() {
		return product.getPrice() * 0.90; // 10% off
	}
}
