package com.awinas.learning.Java11;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;

public class LVTILambda {

	public static void main(String[] args) {
		/*
		 * Java 10
		 */

		var numbers = Arrays.asList(3, 5, 2, 6, 1, 4);
		numbers.sort((Integer a, Integer b) -> b.compareTo(a));
		numbers.sort((a, b) -> b.compareTo(a));
		numbers.sort((a, b) -> b < a ? 1 : -1);

		System.out.println(numbers);

		/*
		 * Java 11
		 */

		var numbers_ = Arrays.asList(3, 5, 2, 6, 1, 4);
		numbers_.sort((var a, var b) -> b.compareTo(a));
		System.out.println(numbers_);

		/*
		 * numbers_.sort((var a, var b) -> b.compareTo(a)); numbers.sort((a, b) ->
		 * b.compareTo(a));
		 * 
		 * Why would we want to use var for lambda parameters when we could simply skip
		 * the types?
		 * 
		 * The benefit is now we can add annotations to the lambda parameters, see this
		 * example:
		 * 
		 * https://stackoverflow.com/questions/68608329/what-is-the-use-case-for-var-
		 * with-lambda-expression
		 * 
		 * https://openjdk.org/jeps/323
		 * 
		 * For uniformity with local variables, we wish to allow 'var' for the formal
		 * parameters of an implicitly typed lambda expression:
		 * 
		 */

		List<String> list = Arrays.asList("a", "b", "c", "b");
		String result = list.stream().map((@NonNull var x) -> x.toUpperCase()).collect(Collectors.joining(","));
		System.out.println(result);

	}

}

//Comparator<Integer> comp = new Comparator<Integer>() {
//@Override
//public int compare(Integer a, Integer b) {
//	return b.compareTo(a);
//}
//
//};