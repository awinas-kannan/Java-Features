package com.awinas.learning.solid.openclosedprinciple.good;

import java.util.List;

public class ShoppingCart {

	public int calculateTotalPrice(List<Product> list) {
		int total = 0;
		for (Product p : list) {
			total = total + p.calculateTotalPrice();
		}
		return total;

	}
}
