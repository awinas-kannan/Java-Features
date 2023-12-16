package com.awinas.learning.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

public class Learn {

	static Integer[] elementData = new Integer[] { 1, 2, 3, 4 };

	public static void main(String[] args) {

		List<Integer> list = new ArrayList<>(Arrays.asList(1, 2));
		System.out.println(list.size());
		Vector<Integer> vector = new Vector<>();
		System.out.println(vector.capacity());
		System.out.println(elementData.length);
		grow(elementData.length + 1);
		System.out.println(elementData.length);

		System.out.println("####### collection sort comparator############");
		List<Integer> linkList = new LinkedList<>(Arrays.asList(19, 3, 45, 34, 1, 2));
		Collections.sort(list);
		Collections.sort(linkList, (o, t) -> {
			return o < t ? 1 : -1;
		});

		System.out.println(linkList);

		System.out.println("####### collection sort comparible############");

		List<Student> stList = new LinkedList<>(
				Arrays.asList(new Student(1, 10, 20, 30), new Student(1, 10, 20, 50), new Student(1, 10, 30, 30)));
		Collections.sort(stList);
		/// If the student class do not implement comparible ,, this line will throw
		/// error
		System.out.println(stList);
		System.out.println("##### set ######");
		Set<Integer> set1 = new HashSet<>();
		set1.add(null);
		set1.add(null);
		set1.add(null);
		set1.add(101);
		set1.add(5);
		set1.add(10);
		System.out.println(set1);
		// Set<Integer> set2 = new TreeSet<>(set1);
		// Treeset do not allow null value
		// System.out.println(set2);

		System.out.println("##### hashtable ######");
		// do not allow null key or value
		Hashtable<Integer, String> ht = new Hashtable<Integer, String>();
		ht.put(102, "Ravi");
		ht.put(101, "ajay");
		ht.put(103, "Rahul");
		ht.put(101, "Vijay");
		ht.put(100, "Ram");
		// ht.put(104, null);
		// ht.put(null, "ak");
		// ht.put(null, "kanna");
		for (Map.Entry m : ht.entrySet()) {
			System.out.println(m.getKey() + " " + m.getValue());
		}

		System.out.println("##### hashmap ######");
		// can have one null key and any null values

		Map<Integer, String> hm = new HashMap<Integer, String>();
		hm.put(100, "Amit");
		hm.put(104, "Amit");
		hm.put(101, "Vijay");
		hm.put(102, "Rahul");
		hm.put(104, null);
		hm.put(null, "ak");
		hm.put(null, "kanna");

		for (Map.Entry m : hm.entrySet()) {
			System.out.println(m.getKey() + " " + m.getValue());
		}

	}

	private static void grow(int minCapacity) {
		// overflow-conscious code
		int oldCapacity = elementData.length;
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		// signed right shift operator formula -> a / 2^ n ( old capacity / 2 ^ 1)
		System.out.println("newCapacity " + newCapacity);
		if (newCapacity - minCapacity < 0)
			newCapacity = minCapacity;

		elementData = Arrays.copyOf(elementData, newCapacity);
	}
}
