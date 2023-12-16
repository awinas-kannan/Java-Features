package com.awinas.learning.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//https://stackoverflow.com/questions/4534146/properly-removing-an-integer-from-a-listinteger
public class AvoidConcurrentModificationExceptionList {

	public static void main(String[] args) {

		List<Integer> list = new ArrayList<>();

		for (int i = 1; i < 100; i++) {
			list.add(i);
		}
		for (int i = 1; i < 100; i++) {
			list.add(i);
		}

		System.out.println(list);
		list.remove(new Integer(1));
		System.out.println(list);
		// removing even numbers from list

		// Concurrent modification exception occurs
//		for (Integer i : list) {
//
//			if (i % 2 == 0) {
//				list.remove(i);
//			}
//		}

		// Soln 1 : create a temp list
		List<Integer> tempList = new ArrayList<>();
		for (Integer i : list) {

			if (i % 2 == 0) {
				tempList.add(i);
			}
		}

		list.removeAll(tempList);

		System.out.println(list);

		// Soln 2 :
		Iterator<Integer> iter = list.iterator();

		while (iter.hasNext()) {
			Integer i = iter.next();

			if (i % 2 != 0)
				iter.remove();
		}

		System.out.println(list);

		// Java 8 Approach

		// Internally uses iterator
		list.removeIf((i) -> i % 2 == 0);

	}
}
