package com.awinas.learning.Java08.streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//Ref :: https://howtodoinjava.com/java8/java-streams-by-examples/

//Intermediate operation always return a Stream

public class StreamIntermediateOperation extends MyList {
	static Comparator<String> comparator;

	static int x;
	public static void main(String[] args) {

		System.out.println("*****  Filter *****");

		// Filter takes predicate. it has test() method

		Predicate<String> predicate = new Predicate<String>() {

			@Override
			public boolean test(String e) {

				return e.startsWith("A");
			}
		};

		System.out.println("1: negate with predicate impl");
		memberNames.stream().filter(predicate.negate()).forEach(System.out::println);
		System.out.println("2:lambda predicate impl");
		memberNames.stream().filter(e -> e.startsWith("A")).forEach(System.out::println);
		System.out.println("3:distict match");
		memberNames.stream().filter(e -> e.startsWith("A")).distinct().forEach(System.out::println);

		System.out.println("*****  Map *****");

		// converting one form to another
		// map takes function. First one is input type, the next one is output type
		// Function<? super T, ? extends R> mapper
		Function<String, String> mapper = new Function<String, String>() {

			@Override
			public String apply(String t) {

				return t.toUpperCase();
			}
		};

		memberNames.stream().map(e -> e.toUpperCase()).forEach(System.out::println);

		memberNames.stream().map(mapper).forEach(System.out::println);
		
		//memberNames.add(null);
		//Optional<List<String>> opStr = Optional.of(memberNames);
		
		memberNames.stream().filter(e -> {
			System.out.println("Not null check " + e);
			return e != null;
		}).map(e -> {
			System.out.println("Mapping " + e);
			return Arrays.asList(e.split(""));
		}).forEach(e -> {

			int size = e.size();
			System.out.println("List size " + size + " list " + e);
		});

		list.stream().mapToLong(e -> e.longValue()).forEach(System.out::println);

		System.out.println("***** Flat Map *****");

		// flat map return a stream // list of list
		// flatMap(Function<? super T, ? extends Stream<? extends R>> mapper)
		Function<List<Integer>, Stream<Integer>> mapper1 = new Function<List<Integer>, Stream<Integer>>() {

			@Override
			public Stream<Integer> apply(List<Integer> t) {

				return t.stream();
			}
		};
		listOflist.stream().flatMap(mapper1).filter(e -> e % 2 == 0).forEach(e -> System.out.println(e + " "));
		listOflist.stream().flatMap(e -> e.stream()).filter(e -> e % 2 == 0).forEach(e -> System.out.println(e + " "));

		System.out.println("*****  Sorted *****");

		// Ascending
		memberNames.stream().sorted().forEach(System.out::println);

		// Descending
		comparator = new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return -(o1.compareTo(o2));

			}
		};
		System.out.println("---------------------------------------------");
		memberNames.stream().sorted(comparator).forEach(System.out::println);
		System.out.println("---------------------------------------------");
		memberNames.stream().sorted((o1, o2) -> {
			return -(o1.compareTo(o2));
		}).forEach(System.out::println);

		System.out.println("***** peek *****");

		// Stream.peek() without any terminal operation does nothing

		Consumer<? super String> action = new Consumer<String>() {

			@Override
			public void accept(String t) {
				System.out.println("-" + t);
			}
		};
		System.out.println(memberNames.stream().peek(action).collect(Collectors.joining(",")));

		System.out.println("\n ***** limit *****");

		list.stream().limit(5).forEach(e -> System.out.println(e));

		System.out.println("\n ***** skip *****");

		list.stream().skip(5).forEach(e -> System.out.println(e));
		
		
		list.stream().skip(2).forEach(e -> {
			System.out.println(e);
			inc();
		}
		);
		
		System.out.println("x val " + x);

	}
	
	static void inc() {
		x++;
	}

}
