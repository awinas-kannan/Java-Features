package com.awinas.learning.Java08.streams;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ExceptionHandling {

	public static void main(String[] args) {

		List<Integer> integers = Arrays.asList(3, 9, 7, 0, 10, 20);

		Consumer<Integer> action = new Consumer<Integer>() {

			@Override
			public void accept(Integer t) {
				try {
					System.out.println(50 / t);
				} catch (ArithmeticException e) {
					System.err.println("Error for " + t);
				}
			}
		};
		integers.forEach(action);
	}

}
