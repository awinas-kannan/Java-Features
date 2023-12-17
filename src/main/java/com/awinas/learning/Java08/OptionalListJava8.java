package com.awinas.learning.Java08;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class OptionalListJava8 {

	public static void main(String[] args) {
		String x = " 55  , 23 , 4556, 123 ";
		Optional<String> clubLists = Optional.ofNullable(x);
		if (clubLists.isPresent()) {
			Optional<List<String>> clubs = Optional.of(Arrays.asList(clubLists.get().split(",")));
			if (clubs.isPresent()) {
				System.out.println("List of clubs to be processed id :- {} " + clubs.get().toString());
				clubs.get().stream().filter((e) -> {
					System.out.println("Club -> " + e);
					System.out.println("lenght -> " + e.trim().length());
					return  e.trim().length() > 0;
				})
						.forEach((club) -> System.out.println("Process Club -> " + club.trim()));
			}
		}
		
		
		System.out.println("Optional Empty :::::::::::::");
		
		Optional<List<String>> opStr = Optional.empty();
		
		System.out.println(opStr.isPresent());
		System.out.println(opStr.isEmpty());
		System.out.println(opStr.get().size());
	}
}
