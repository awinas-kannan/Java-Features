package com.awinas.learning.solid.openclosedprinciple.bad;

import java.util.Arrays;
import java.util.List;

public class BadMain {

	public static void main(String[] args) {

		Product p1 = new Product("CAR", 2);
		Product p2 = new Product("BIKE", 3);
		Product p3 = new Product("PHONE", 1);

		List<Product> list = Arrays.asList(p1, p2, p3);
		ShoppingCart sc = new ShoppingCart();

		System.out.println(sc.calculateTotalPrice(list));
	}

}
