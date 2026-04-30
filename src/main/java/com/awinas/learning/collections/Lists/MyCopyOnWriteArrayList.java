package com.awinas.learning.collections.Lists;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

// CopyOnWriteArrayList features:

// Thread-safe — all mutative operations (add, set, remove) are synchronized internally
// Uses ReentrantLock for write operations
// Creates a new copy of the underlying array on every mutation
// Iterators work on a snapshot — never throw ConcurrentModificationException
// Best when reads vastly outnumber writes (e.g., listener lists, config lists)
// Allows nulls and duplicates
// Implements List, RandomAccess, Cloneable, Serializable
// Part of java.util.concurrent (since Java 1.5)

public class MyCopyOnWriteArrayList {

	public static void main(String[] args) {

		System.out.println("*******Declaration ***********");

		CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<>();
		CopyOnWriteArrayList<String> fromArray = new CopyOnWriteArrayList<>(new String[]{"Apple", "Banana"});
		CopyOnWriteArrayList<String> fromCollection = new CopyOnWriteArrayList<>(Arrays.asList("X", "Y", "Z"));

		System.out.println("fromArray      : " + fromArray);
		System.out.println("fromCollection : " + fromCollection);

		utilMethod();
	}

	public static void utilMethod() {
		System.out.println("\n========== CopyOnWriteArrayList - Frequently Used Methods ==========\n");

		// --- 1. Creation & Initialization ---
		CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
		CopyOnWriteArrayList<String> fromList = new CopyOnWriteArrayList<>(Arrays.asList("A", "B", "C"));

		// --- 2. add() / addAll() ---
		list.add("Apple");
		list.add("Banana");
		list.add("Cherry");
		list.add("Apple");                // duplicates allowed
		list.add(1, "Mango");            // insert at index
		list.addAll(Arrays.asList("Grapes", "Kiwi"));
		System.out.println("After add/addAll       : " + list);

		// --- 3. addIfAbsent() — unique to CopyOnWriteArrayList ---
		boolean added1 = list.addIfAbsent("Banana");  // already exists, won't add
		boolean added2 = list.addIfAbsent("Peach");   // new, will add
		System.out.println("addIfAbsent(Banana)    : " + added1);
		System.out.println("addIfAbsent(Peach)     : " + added2);
		System.out.println("After addIfAbsent      : " + list);

		// --- 4. addAllAbsent() — unique to CopyOnWriteArrayList ---
		int addedCount = list.addAllAbsent(Arrays.asList("Apple", "Melon", "Papaya"));
		System.out.println("addAllAbsent count     : " + addedCount);
		System.out.println("After addAllAbsent     : " + list);

		// --- 5. get() ---
		System.out.println("get(0)                 : " + list.get(0));
		System.out.println("get(last)              : " + list.get(list.size() - 1));

		// --- 6. set() — triggers array copy ---
		list.set(0, "Avocado");
		System.out.println("After set(0, Avocado)  : " + list);

		// --- 7. remove() — triggers array copy ---
		list.remove("Apple");            // removes first occurrence
		list.remove(0);                  // removes by index
		System.out.println("After remove           : " + list);

		// --- 8. contains() / containsAll() ---
		System.out.println("contains Banana        : " + list.contains("Banana"));
		System.out.println("containsAll            : " + list.containsAll(Arrays.asList("Banana", "Cherry")));

		// --- 9. indexOf() / lastIndexOf() ---
		System.out.println("indexOf Cherry         : " + list.indexOf("Cherry"));
		System.out.println("lastIndexOf Cherry     : " + list.lastIndexOf("Cherry"));

		// --- 10. size() / isEmpty() ---
		System.out.println("size                   : " + list.size());
		System.out.println("isEmpty                : " + list.isEmpty());

		// --- 11. subList() ---
		List<String> sub = list.subList(1, 4);
		System.out.println("subList(1,4)           : " + sub);

		// --- 12. toArray() ---
		String[] arr = list.toArray(new String[0]);
		System.out.println("toArray                : " + Arrays.toString(arr));

		// --- 13. Snapshot Iterator (key feature) ---
		System.out.println("\n--- Snapshot Iterator Demo ---");
		CopyOnWriteArrayList<String> snapshotDemo = new CopyOnWriteArrayList<>(
				Arrays.asList("One", "Two", "Three"));

		Iterator<String> it = snapshotDemo.iterator();
		snapshotDemo.add("Four");        // modification after iterator creation
		snapshotDemo.remove("One");      // another modification

		System.out.print("Iterator (snapshot)    : ");
		while (it.hasNext()) {
			System.out.print(it.next() + " "); // prints One Two Three (snapshot!)
		}
		System.out.println();
		System.out.println("Actual list now        : " + snapshotDemo);
		System.out.println("(Iterator does NOT reflect changes made after its creation)");

		// --- 14. Iterator does NOT support remove() ---
		System.out.println("\n--- Iterator.remove() throws UnsupportedOperationException ---");
		try {
			Iterator<String> it2 = snapshotDemo.iterator();
			it2.next();
			it2.remove();
		} catch (UnsupportedOperationException e) {
			System.out.println("Caught: " + e.getClass().getSimpleName()
					+ " — iterator.remove() is NOT supported");
		}

		// --- 15. forEach (lambda) ---
		System.out.print("forEach (lambda)       : ");
		list.forEach(s -> System.out.print(s + " "));
		System.out.println();

		// --- 16. sort() ---
		CopyOnWriteArrayList<String> sortable = new CopyOnWriteArrayList<>(
				Arrays.asList("Cherry", "Apple", "Banana"));
		sortable.sort(Comparator.naturalOrder());
		System.out.println("Sorted (natural)       : " + sortable);
		sortable.sort(Comparator.reverseOrder());
		System.out.println("Sorted (reverse)       : " + sortable);

		// --- 17. Stream operations ---
		List<String> filtered = list.stream()
				.filter(s -> s.startsWith("M") || s.startsWith("P"))
				.collect(Collectors.toList());
		System.out.println("Stream filter(M or P)  : " + filtered);

		long count = list.stream().distinct().count();
		System.out.println("Stream distinct count  : " + count);

		// --- 18. removeIf() ---
		CopyOnWriteArrayList<String> copy = new CopyOnWriteArrayList<>(list);
		copy.removeIf(s -> s.startsWith("M"));
		System.out.println("removeIf(starts M)     : " + copy);

		// --- 19. replaceAll() ---
		CopyOnWriteArrayList<String> upper = new CopyOnWriteArrayList<>(
				Arrays.asList("apple", "banana"));
		upper.replaceAll(String::toUpperCase);
		System.out.println("replaceAll toUpperCase : " + upper);

		// --- 20. retainAll() ---
		CopyOnWriteArrayList<String> retain = new CopyOnWriteArrayList<>(list);
		retain.retainAll(Arrays.asList("Cherry", "Kiwi"));
		System.out.println("retainAll              : " + retain);

		// --- 21. Thread-safety demo ---
		System.out.println("\n--- Thread-Safety Demo ---");
		CopyOnWriteArrayList<String> sharedList = new CopyOnWriteArrayList<>(
				Arrays.asList("A", "B", "C"));

		Thread reader = new Thread(() -> {
			for (String s : sharedList) {
				System.out.println("  Reader thread reads: " + s);
				try { Thread.sleep(50); } catch (InterruptedException ignored) {}
			}
		});

		Thread writer = new Thread(() -> {
			sharedList.add("D");
			System.out.println("  Writer thread added: D");
			sharedList.add("E");
			System.out.println("  Writer thread added: E");
		});

		reader.start();
		writer.start();

		try {
			reader.join();
			writer.join();
		} catch (InterruptedException ignored) {}

		System.out.println("Final sharedList       : " + sharedList);

		// --- 22. clear() ---
		CopyOnWriteArrayList<String> temp = new CopyOnWriteArrayList<>(list);
		temp.clear();
		System.out.println("After clear            : " + temp + ", isEmpty: " + temp.isEmpty());
		System.out.println("After clear            : " + list + ", isEmpty: " + list.isEmpty());

		System.out.println("\n========== End of CopyOnWriteArrayList utilMethod ==========\n");
	}
}
