package com.awinas.learning.java9;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public interface PrivateMethodInterface {

	float pi = 3.4f; // public static

	abstract void abstractMetod();

	default void defMethod1() {
		commonMethod("defMethod1");
	}

	default void defMethod2() {
		commonMethod("defMethod2");
	}

	static void staticMethod() {
		System.out.println("Static Method");
	}

	private void commonMethod(String methodname) {
		System.out.println(methodname);
	}

	default int addEvenNumbers(int... nums) {
		return add(n -> n % 2 == 0, nums);
	}

	default int addOddNumbers(int... nums) {
		return add(n -> n % 2 != 0, nums);
	}

	private int add(IntPredicate predicate, int... nums) {
		return IntStream.of(nums).filter(predicate).sum();
	}

	class x implements IntPredicate {

		@Override
		public boolean test(int value) {
			return value % 2 != 0;
		}

	}
}
