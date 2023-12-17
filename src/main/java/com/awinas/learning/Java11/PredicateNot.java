package com.awinas.learning.Java11;

import java.util.function.Predicate;

public class PredicateNot {

	public static void main(String[] args) {
		Predicate<Integer> isEven = num -> num % 2 == 0;
		Predicate<Integer> isOdd = Predicate.not(isEven);
		Predicate<Integer> isDivisibleBy3 = Predicate.not(isEven).and(num -> num % 3 == 0);

		System.out.println(isOdd.test(5)); // Output: true
		System.out.println(isDivisibleBy3.test(9)); // Output: true
	}

}
