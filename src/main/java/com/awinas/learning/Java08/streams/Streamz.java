package com.awinas.learning.Java08.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Streamz {

	public static void main(String[] args) {
		List<String> memberNames = new ArrayList<>();
		memberNames.add("Amitabh");
		memberNames.add("Shekhar");
		memberNames.add("Aman");
		memberNames.add("Rahul");
		memberNames.add("Shahrukh");
		memberNames.add("Salman");
		memberNames.add("Yana");
		memberNames.add("Lokesh");

		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i < 10; i++) {
			list.add(i);
		}
		list.add(null);
		list.add(444);
		list.add(null);
		Stream<Integer> stream = list.stream();
		// System.out.println(stream.);

		System.out.println(stream.count());

//		 Supplier<Stream<Integer>> ints =  () -> Stream.of(stream`);
//		 ints.get().findAny();

	}

}
