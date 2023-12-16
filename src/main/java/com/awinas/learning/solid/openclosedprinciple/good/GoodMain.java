package com.awinas.learning.solid.openclosedprinciple.good;

import java.util.Arrays;
import java.util.List;

import com.awinas.learning.solid.openclosedprinciple.good.Product;
import com.awinas.learning.solid.openclosedprinciple.good.ShoppingCart;

public class GoodMain {
	public static void main(String[] args) {

		Product p1 = new Car("CAR", 2);
		Product p2 = new Bike("BIKE", 3);
		Product p3 = new Phone("PHONE", 1);
		Product p4 = new Toy("Toy", 4);

		List<Product> list = Arrays.asList(p1, p2, p3, p4);
		ShoppingCart sc = new ShoppingCart();

		System.out.println(sc.calculateTotalPrice(list));
	}
}
