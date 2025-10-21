package com.awinas.learning.oop.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MainInterface {

	public static void main(String[] args) {
		Idefault id = new DefaultUtilizser();
		id.defMethod();
		id.normalMethod();
		id.defMethod1();


		System.out.println("***************************************************");

		Istatic is = new StaticUtilizer();
		is.normalmethod();
		Istatic.staticMethod1();
		Istatic.staticMethod2();
		StaticUtilizer.staticMethod1();

		System.out.println("***************************************************");
		
		List<String> str = new ArrayList<>();
		str.add("ak");
		// str.add(null);
		str.add("awi");
		Consumer<String> c = new Consumer<String>() {

			@Override
			public void accept(String t) {
				System.out.println(t);
			}
		};

		Consumer<String> c1 = (t) -> System.out.println(t);

		str.forEach(e -> System.out.println(e.contains("a")));
		str.forEach(c);
		str.forEach(c1);
		
	}

}
