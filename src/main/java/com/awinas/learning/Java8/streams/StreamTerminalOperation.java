package com.awinas.learning.Java8.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.awinas.learning.java8.Employee;

//Ref :: https://howtodoinjava.com/java8/java-streams-by-examples/

public class StreamTerminalOperation extends MyList {

	public static void main(String[] args) {

		System.out.println("***** ForEach *****");

		memberNames.forEach(System.out::println);

		System.out.println("***** Collect *****");

		System.out.println(memberNames.stream().filter(e -> e.startsWith("A")).collect(Collectors.toList()));
		System.out.println(
				memberNames.stream().filter(e -> e.startsWith("A")).collect(Collectors.joining("::", "[", "]")));
		String z = memberNames.stream().filter(e -> e.startsWith("A")).collect(Collectors.joining("::", "[", "]"));

		System.out.println("***** Match (Anymatch | AllMatch | Nonematch) *****");
		// TakesPredicate
		System.out.println(memberNames.stream().anyMatch(e -> e.startsWith("A")));
		System.out.println(memberNames.stream().allMatch(e -> e.startsWith("A")));
		System.out.println(memberNames.stream().noneMatch(e -> e.startsWith("P")));

		System.out.println("***** Match (Find first | Find any) *****");
		System.out.println(memberNames.stream().filter((e) -> e.startsWith("P")).findFirst().orElse("nill match"));
		System.out.println(memberNames.stream().filter((e) -> e.startsWith("Ami")).findAny().orElse("nill match"));

		System.out.println("***** Count *****");

		System.out.println(memberNames.stream().count());
		System.out.println(memberNames.stream().filter(e -> e.startsWith("A")).count());

		System.out.println("***** Reduce *****");

		// https://www.baeldung.com/java-stream-reduce
		// Concat Operation done

		Optional<String> result = memberNames.stream().reduce((a, b) -> a + "--" + b);
		result.ifPresent(System.out::println);

		String identity = "Concatination"; // initializer str= concatination
		BinaryOperator<String> accumulator = new BinaryOperator<String>() {

			@Override
			public String apply(String str, String e) {

				return str + " " + e;
			}
		};
		System.out.println(memberNames.stream().reduce(identity, accumulator));
		System.out.println(memberNames.stream().reduce(identity, String::concat));
		System.out.println(memberNames.stream().reduce("Concatination", (ConcatVal, e) -> ConcatVal + " " + e));

		// Initiliser sum =5;
		// - > 8 + 1+ 2+ 3+4+ .... + 10
		System.out.println(list.stream().reduce(8, (sum, e) -> sum + e));

		System.out.println(list.stream().map(e -> String.valueOf(e)).reduce((a, b) -> a + " " + b));

		System.out.println(list.stream().reduce(5, Integer::sum));

		int sumz = 0;
		for (int i : longlist) {
			sumz = sumz + i;
		}
		System.out.println(sumz);
		System.out.println(longlist.parallelStream().reduce(0, (sum, e) -> sum + e));

		employeeList.stream().forEach(System.out::println);

		// use *Combiner* when data types of accumulater are diffrent

		BiFunction<Integer, Employee, Integer> accumulater = new BiFunction<Integer, Employee, Integer>() {

			@Override
			public Integer apply(Integer age, Employee e) {
				return age + Integer.parseInt(e.getEmpAge());
			}

		};

		BinaryOperator<Integer> combiner = new BinaryOperator<Integer>() {

			@Override
			public Integer apply(Integer t, Integer u) {

				return t + u;
			}
		};

		System.out.println(employeeList.stream().reduce(10, accumulater, combiner));
		System.out.println(employeeList.stream().reduce(0, (empAge, e) -> empAge + Integer.parseInt(e.getEmpAge()),
				(a, b) -> a + 788));

		System.out.println("***** min *****");

		System.out.println(list.stream().min((a, b) -> a.compareTo(b)).get());

		System.out.println("***** max *****");

		System.out.println(list.stream().max((a, b) -> a.compareTo(b)).get());

		System.out.println("***** group by *****");

		// group by single value
		Function<Employee, String> function = new Function<Employee, String>() {

			@Override
			public String apply(Employee x) {
				return x.getEmpName();
			}

		};

		// group by multiple value
		Function<Employee, Object> classifier = new Function<Employee, Object>() {

			@Override
			public Object apply(Employee x) {
				return new ArrayList<>(Arrays.asList(x.getEmpName(), x.getEmpAge()));
			}

		};

		Map<String, List<Employee>> groupedResult0 = employeeList.stream().collect(Collectors.groupingBy(function));
		// Map<String, List<Employee>> groupedResult0 =
		// employeeList.stream().collect(Collectors.groupingBy(Employee::getEmpName));
		Map<Object, List<Employee>> groupedResult1 = employeeList.stream().collect(Collectors.groupingBy(classifier));
		Map<Object, List<Employee>> groupedResult2 = employeeList.stream().collect(Collectors.groupingBy(x -> {
			return new ArrayList<>(Arrays.asList(x.getEmpName(), x.getEmpAge()));
		}));
		Map<String, Set<Employee>> groupedResult3 = employeeList.stream()
				.collect(Collectors.groupingBy(Employee::getEmpName, Collectors.toSet()));
		System.out.println("0");
		System.out.println(groupedResult0);
		System.out.println("1");
		System.out.println(groupedResult1);
		System.out.println("2");
		System.out.println(groupedResult2);
		System.out.println("3");
		System.out.println(groupedResult3);

		Map<Object, Set<Employee>> groupedResult4 = employeeList.stream()
				.collect(Collectors.groupingBy(classifier, HashMap::new, Collectors.toSet()));
		System.out.println(groupedResult4);
		Map<String, Long> groupedResultA = employeeList.stream()
				.collect(Collectors.groupingBy(function, Collectors.counting()));

		Map<String, Long> groupedResultB = employeeList.stream()
				.collect(Collectors.groupingBy(Employee::getEmpName, Collectors.counting()));

		System.out.println(groupedResultA);
		System.out.println(groupedResultB);

		for (Object obj : groupedResult1.keySet()) {
			List a = (List) obj;
			System.out.println(a.get(0) + " - " + a.get(1));
		}

	}

}
