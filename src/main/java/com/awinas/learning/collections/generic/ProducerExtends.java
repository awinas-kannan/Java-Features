package com.awinas.learning.collections.generic;

import java.util.ArrayList;
import java.util.List;

//https://stackoverflow.com/questions/4343202/difference-between-super-t-and-extends-t-in-java
public class ProducerExtends {

	static List<Number> foo1 = new ArrayList<>(); // Number "extends" Number (in this context)
	static List<Integer> foo2 = new ArrayList<>(); // Integer extends Number
	static List<Double> foo3 = new ArrayList<>(); // Double extends Number

	public static void main(String[] args) {

		Number i1 = get1().get(1);
		// You can't read an Integer because foo could be pointing at a
		// List<Double>,List<Long>.
		// Integer i2 = get2().get(1);

		set1(get1());
	}

	public static List<? extends Number> get1() {
		return foo1;
	}

	public static List<? extends Number> get2() {
		return foo2;
	}

	public static List<? extends Number> get3() {
		return foo3;
	}

	public static void set1(List<? extends Number> n) {

		// You can't add an Integer because foo could be pointing at a List<Double>.
		// You can't add a Double because foo could be pointing at a List<Integer>.
		// You can't add a Number because foo could be pointing at a List<Integer>.
		// n.add(1);
	}
}
