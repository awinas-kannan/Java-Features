package com.awinas.learning.solid.openclosedprinciple.bad;

import java.util.List;

public class ShoppingCart {

	public int calculateTotalPrice(List<Product> list) {
		int total = 0;
		for (Product p : list) {

			if ("CAR".equals(p.getType())) {
				total = total + (p.getQuantity() * 1000);
			} else if ("BIKE".equals(p.getType())) {
				total = total + (p.getQuantity() * 2000);
			} else if ("PHONE".equals(p.getType())) {
				total = total + (p.getQuantity() * 10000);
			} else if ("TOY".equals(p.getType())) {

			}
		}
		return total;

	}
}
