package com.awinas.learning.Java08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

//  ClassName::MethodName
// https://www.geeksforgeeks.org/double-colon-operator-in-java/

//ForEach -- https://howtodoinjava.com/java8/foreach-method-example/

public class MyForEach {

	public static void main(String[] args) {

		System.out.println("***** List  *****");
		List<Integer> list = new ArrayList<>();

		for (int i = 1; i <= 10; i++) {
			list.add(new Integer(i));
		}

		// different ways of creating consumer
		Consumer<Integer> action = new Consumer<Integer>() {

			@Override
			public void accept(Integer t) {
				System.out.print(t + " ");

			}
		};

		action = e -> System.out.print(e + " ");

		action = System.out::print;

		action = MyForEach::printInteger;

		action = a -> printInteger(a);

		/*
		 * Employee emp = new Employee(); action = emp::setEmpId;
		 */

		list.forEach(action);

		list.forEach(e -> System.out.print(e + " "));

		System.out.println("\n******************");

		list.parallelStream().forEach(e -> System.out.print(e + " "));

		System.out.println("\n Consumer Impl ");

		/*
		 * Consumer<Integer> t =new ConsumerImpl<>(); list.forEach(t);
		 */

		list.forEach(new ConsumerImpl<>());

		System.out.println("\n \n \n ***** Map  *****");

		Map<Integer, String> map = new HashMap<>();

		map.put(1, "awinas");
		map.put(2, "karthi");
		map.put(3, "viki");
		map.put(4, "aswin");
		map.put(5, "maha");

		BiConsumer<Integer, String> actionMap = new BiConsumer<Integer, String>() {

			@Override
			public void accept(Integer k, String v) {
				// if(k==1)
				System.out.println(k + " " + v);
			}
		};

		actionMap = (k, v) -> System.out.println(k + " " + v);

		map.forEach(actionMap);

		// Entry Set

		Consumer<Map.Entry<Integer, String>> entrySetAction = new Consumer<Map.Entry<Integer, String>>() {

			@Override
			public void accept(Entry<Integer, String> t) {

				System.out.println(t.getKey() + " " + t.getValue());
			}
		};

		entrySetAction = t -> System.out.println(t.getKey() + " " + t.getValue());
		entrySetAction = t -> {
			System.out.println(t.getKey());
			System.out.println(t.getValue());
		};
		map.entrySet().forEach(entrySetAction);

		// map.values().removeIf(e -> (e == "awinas" || e.equals("karthi")));

		map.keySet().forEach(action);
		map.values().forEach(a -> printString(a));
		System.out.println(map);

		// list.forEach(add());

	}

	private static Consumer<Integer> add() {
		return new Consumer<Integer>() {

			@Override
			public void accept(Integer t) {

			}
		};
	}

	public static void printInteger(Integer a) {
		System.out.println(a + " ");
		System.out.println("printed in custom method");
	}

	public static void printString(String s) {
		System.out.println(s + " ");
		System.out.println("printed in custom method");
	}

}
