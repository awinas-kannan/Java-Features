package com.awinas.learning.dp.structural.decorator.v2;

public abstract class ProductDecorator implements Product {
	protected Product product;

	public ProductDecorator(Product product) {
		this.product = product;
	}
}
