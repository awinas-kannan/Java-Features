package com.awinas.learning.dp.structural.decorator.v2;

public class GiftWrap extends ProductDecorator {
	public GiftWrap(Product product) {
		super(product);
	}

	public String getDescription() {
		return product.getDescription() + ", with Gift Wrap";
	}

	public double getPrice() {
		return product.getPrice() + 50.0;
	}
}
