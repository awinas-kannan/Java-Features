package com.awinas.learning.collections.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

//Array List features

//Ordered 

//Index based

//Dynamic resizing

//non syncronised

//duplicated allowed

public class MyArrayList {

	public static void main(String[] args) {

		System.out.println("*******Declaration ***********");
		List<Integer> numbers = new ArrayList<>();

		// Generic Arraylist with the given capacity
		List<Integer> numbers1 = new ArrayList<>(3);
		numbers1.add(3);
		numbers1.add(3);
		numbers1.add(4);
		numbers1.add(4);
		numbers1.add(3);

		// Generic Arraylist initialized with another collection
		List<Integer> numbers2 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
		numbers2.add(10);

		System.out.println(numbers1.size());
		System.out.println(numbers1);
		System.out.println(numbers2);

		// 1. ArrayList to Array
		String array[] = new String[numbers2.size()];
		Integer arrayz[] = new Integer[numbers2.size()];
		numbers2.toArray(arrayz);

		System.out.println(Arrays.toString(arrayz));

		// random access
		List<Integer> suncList = Collections.synchronizedList(numbers2);

		utilMethod();
	}

	public static void utilMethod() {
		System.out.println("\n========== ArrayList - Frequently Used Methods ==========\n");

		// --- 1. Creation & Initialization ---
		List<String> fruits = new ArrayList<>();
		List<String> fromArray = new ArrayList<>(Arrays.asList("Apple", "Banana", "Cherry"));
		List<String> fixedSize = List.of("X", "Y", "Z"); // immutable (Java 9+)

		// --- 2. add() / addAll() ---
		fruits.add("Apple");
		fruits.add("Banana");
		fruits.add("Cherry");
		fruits.add("Apple");           // duplicates allowed
		fruits.add(1, "Mango");        // insert at index 1
		fruits.addAll(Arrays.asList("Grapes", "Kiwi"));
		System.out.println("After add/addAll : " + fruits);

		// --- 3. get() ---
		System.out.println("get(0)           : " + fruits.get(0));
		System.out.println("get(last)        : " + fruits.get(fruits.size() - 1));

		// --- 4. set() — replace element at index ---
		fruits.set(0, "Avocado");
		System.out.println("After set(0)     : " + fruits);

		// --- 5. remove() ---
		fruits.remove("Apple");        // removes first occurrence
		fruits.remove(0);              // removes by index
		System.out.println("After remove     : " + fruits);

		// --- 6. contains() / containsAll() ---
		System.out.println("contains Banana  : " + fruits.contains("Banana"));
		System.out.println("containsAll      : " + fruits.containsAll(Arrays.asList("Banana", "Cherry")));

		// --- 7. indexOf() / lastIndexOf() ---
		fruits.add("Cherry");
		System.out.println("indexOf Cherry   : " + fruits.indexOf("Cherry"));
		System.out.println("lastIndexOf Cherry: " + fruits.lastIndexOf("Cherry"));

		// --- 8. size() / isEmpty() ---
		System.out.println("size             : " + fruits.size());
		System.out.println("isEmpty          : " + fruits.isEmpty());

		// --- 9. subList() — returns a view ---
		List<String> sub = fruits.subList(1, 3);
		System.out.println("subList(1,3)     : " + sub);

		// --- 10. toArray() ---
		String[] arr = fruits.toArray(new String[0]);
		System.out.println("toArray          : " + Arrays.toString(arr));

		// --- 11. sort() ---
		fruits.sort(Comparator.naturalOrder());
		System.out.println("Sorted (natural) : " + fruits);
		fruits.sort(Comparator.reverseOrder());
		System.out.println("Sorted (reverse) : " + fruits);

		// --- 12. Collections utility methods ---
		Collections.shuffle(fruits);
		System.out.println("Shuffled         : " + fruits);
		Collections.sort(fruits);
		System.out.println("Collections.sort : " + fruits);

		System.out.println("frequency(Cherry): " + Collections.frequency(fruits, "Cherry"));
		System.out.println("min              : " + Collections.min(fruits));
		System.out.println("max              : " + Collections.max(fruits));

		// --- 13. Iteration methods ---
		System.out.print("for-each loop    : ");
		for (String f : fruits) {
			System.out.print(f + " ");
		}
		System.out.println();

		System.out.print("Iterator         : ");
		Iterator<String> it = fruits.iterator();
		while (it.hasNext()) {
			System.out.print(it.next() + " ");
		}
		System.out.println();

		System.out.print("ListIterator(rev): ");
		ListIterator<String> lit = fruits.listIterator(fruits.size());
		while (lit.hasPrevious()) {
			System.out.print(lit.previous() + " ");
		}
		System.out.println();

		System.out.print("forEach (lambda) : ");
		fruits.forEach(f -> System.out.print(f + " "));
		System.out.println();

		// --- 14. Stream operations ---
		List<String> filtered = fruits.stream()
				.filter(f -> f.startsWith("C"))
				.collect(Collectors.toList());
		System.out.println("Stream filter(C) : " + filtered);

		long count = fruits.stream().count();
		System.out.println("Stream count     : " + count);

		// --- 15. removeIf() (Java 8+) ---
		List<String> copy = new ArrayList<>(fruits);
		copy.removeIf(f -> f.startsWith("C"));
		System.out.println("removeIf(!C)     : " + copy);

		// --- 16. replaceAll() (Java 8+) ---
		List<String> upper = new ArrayList<>(fruits);
		upper.replaceAll(String::toUpperCase);
		System.out.println("replaceAll upper : " + upper);

		// --- 17. retainAll() — keep only matching ---
		List<String> retain = new ArrayList<>(fruits);
		retain.retainAll(Arrays.asList("Cherry", "Kiwi"));
		System.out.println("retainAll        : " + retain);

		// --- 18. clear() ---
		List<String> temp = new ArrayList<>(fruits);
		temp.clear();
		System.out.println("After clear      : " + temp + ", isEmpty: " + temp.isEmpty());

		// --- 19. Unmodifiable / Immutable views ---
		List<String> unmodifiable = Collections.unmodifiableList(fruits);
		System.out.println("Unmodifiable     : " + unmodifiable);

		// --- 20. Synchronized wrapper ---
		List<String> syncList = Collections.synchronizedList(new ArrayList<>(fruits));
		System.out.println("Synchronized     : " + syncList);

		System.out.println("\n========== End of ArrayList utilMethod ==========\n");
	}

}
