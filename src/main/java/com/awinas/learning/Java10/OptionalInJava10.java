package com.awinas.learning.Java10;

import java.util.Optional;

public class OptionalInJava10 {

	public static void main(String[] args) {

		String stringOne = "Hello world";
		var optionalStringOne = Optional.ofNullable(stringOne);
		var valueStringOne = optionalStringOne.orElseThrow();
		System.out.println(valueStringOne);

		String stringTwo = null;
		var optionalStringTwo = Optional.ofNullable(stringTwo);
//		var valueStringTwo = optionalStringTwo.orElseThrow();
//		System.out.println(valueStringTwo);

		// create a Optional
		Optional<Integer> op = Optional.of(9455);
		Optional<Integer> opEmpty = Optional.empty();
		// print supplier
		System.out.println("Optional: " + op);
		System.out.println("Optional: " + opEmpty);
		// orElseThrow supplier
		System.out.println("Value by orElseThrow(" + "ArithmeticException::new) method: "
				+ op.orElseThrow(ArithmeticException::new));
		System.out.println("Value by orElseThrow(" + "ArithmeticException::new) method: "
				+ opEmpty.orElseThrow(ArithmeticException::new));
	}
}