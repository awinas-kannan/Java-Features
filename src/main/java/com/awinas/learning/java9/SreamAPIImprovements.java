package com.awinas.learning.java9;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import com.awinas.learning.string.Str;

@Deprecated(since = "2019", forRemoval = true)
public class SreamAPIImprovements {

	public static void main(String[] args) {

		/*
		 * takeWhile
		 */
		List<String> alphabets = List.of("a", "b", "c", "d", "e", "f", "g", "h", "i");
		List<String> subset1 = alphabets.stream().takeWhile(s -> !s.equals("d")).toList();
		System.out.println(subset1);

		/*
		 * dropWhile
		 */
		List<String> subset2 = alphabets.stream().dropWhile(s -> !s.equals("d")).toList();
		System.out.println(subset2);

		/*
		 * Overloaded Stream iterate method
		 */

		// Java 8
		List<Integer> listInt8 = Stream.iterate(1, i -> i + 2).limit(10).toList();
		// Java 9
		List<Integer> listInt9 = Stream.iterate(1, i -> i <= 10, i -> i + 2).limit(3).toList();
		System.out.println(listInt8);
		System.out.println(listInt9);

		/*
		 * New Stream ofNullable() method
		 */

		Stream<String> stream = Stream.ofNullable("123");
		System.out.println(stream.count());

		stream = Stream.ofNullable(null);
		System.out.println(stream.count());

		List<String> s1 = Arrays.asList("1", "2", "3");
		List<String> s2 = null;
		s1.stream().forEach(System.out::println);
		Stream.ofNullable(s1).flatMap(e -> e.stream()).forEach(System.out::println);
		Stream.ofNullable(s2).flatMap(e -> e.stream()).forEach(System.out::println);
//		s2.stream().forEach(System.out::println);
		

	}
}

//UnaryOperator<Integer> uo = new UnaryOperator<Integer>() {
//
//	@Override
//	public Integer apply(Integer t) {
//		return t + 2;
//	}
//};