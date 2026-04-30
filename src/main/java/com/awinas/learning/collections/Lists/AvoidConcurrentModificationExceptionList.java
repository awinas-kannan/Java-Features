package com.awinas.learning.collections.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

// ============================================================================
// ConcurrentModificationException (CME) in Java Lists
// ============================================================================
//
// WHAT: CME is thrown when a collection is structurally modified (add/remove)
//       while it is being iterated using a fail-fast iterator.
//
// WHY:  ArrayList, LinkedList etc. maintain an internal counter called `modCount`.
//       Every add/remove increments modCount.
//       When an Iterator is created, it saves `expectedModCount = modCount`.
//       On every next() call, it checks: modCount != expectedModCount → throw CME!
//
// WHEN does CME occur?
//   1. Modifying a list inside a for-each loop
//   2. One thread iterates while another thread modifies
//   3. Calling list.remove() instead of iterator.remove() during iteration
//
// This class demonstrates the PROBLEM and 6 SOLUTIONS to avoid CME.
// ============================================================================

public class AvoidConcurrentModificationExceptionList {

	public static void main(String[] args) {

		System.out.println("====== THE PROBLEM: ConcurrentModificationException ======\n");
		demonstrateProblem();

		System.out.println("\n====== SOLUTION 1: Collect & Remove After Iteration ======\n");
		solution1_collectAndRemove();

		System.out.println("\n====== SOLUTION 2: Iterator.remove() ======\n");
		solution2_iteratorRemove();

		System.out.println("\n====== SOLUTION 3: removeIf() — Java 8+ (Recommended) ======\n");
		solution3_removeIf();

		System.out.println("\n====== SOLUTION 4: Iterate Backwards with Index ======\n");
		solution4_iterateBackwards();

		System.out.println("\n====== SOLUTION 5: CopyOnWriteArrayList (Fail-Safe) ======\n");
		solution5_copyOnWriteArrayList();

		System.out.println("\n====== SOLUTION 6: Stream filter() — Create New List ======\n");
		solution6_streamFilter();

		System.out.println("\n====== BONUS: Multi-threaded CME scenario ======\n");
		bonus_multiThreadedCME();
	}

	// ========================================================================
	// THE PROBLEM
	// ========================================================================
	// Modifying a list inside a for-each loop triggers CME because for-each
	// internally uses an Iterator. When list.remove() is called, modCount
	// increments but the iterator's expectedModCount stays the same.
	// On the next iteration, modCount != expectedModCount → CME!
	// ========================================================================
	private static void demonstrateProblem() {
		List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		System.out.println("Original list: " + numbers);
		System.out.println("Goal: Remove all even numbers");

		try {
			for (Integer num : numbers) {
				if (num % 2 == 0) {
					numbers.remove(num); // BAD! modifies list during for-each iteration
				}
			}
		} catch (java.util.ConcurrentModificationException e) {
			System.out.println("CAUGHT: ConcurrentModificationException!");
			System.out.println("Reason: list.remove() inside for-each loop increments modCount,");
			System.out.println("        but iterator's expectedModCount is stale → CME thrown on next()");
		}
	}

	// ========================================================================
	// SOLUTION 1: Collect items to remove, then removeAll() after iteration
	// ========================================================================
	// Pros: Simple to understand, works with any Java version
	// Cons: Extra memory for the temp list
	// ========================================================================
	private static void solution1_collectAndRemove() {
		List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

		List<Integer> toRemove = new ArrayList<>();
		for (Integer num : numbers) {
			if (num % 2 == 0) {
				toRemove.add(num); // collect, don't remove yet
			}
		}
		numbers.removeAll(toRemove); // safe — iteration is already done

		System.out.println("After removing evens: " + numbers); // [1, 3, 5, 7, 9]
	}

	// ========================================================================
	// SOLUTION 2: Use Iterator.remove()
	// ========================================================================
	// This is the ONLY safe way to remove during iteration with a standard iterator.
	// Iterator.remove() updates BOTH modCount AND expectedModCount together,
	// so the check passes on the next next() call.
	//
	// IMPORTANT: You must call next() before remove(). You cannot call remove()
	// twice without calling next() in between.
	// ========================================================================
	private static void solution2_iteratorRemove() {
		List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

		Iterator<Integer> it = numbers.iterator();
		while (it.hasNext()) {
			Integer num = it.next(); // MUST call next() before remove()
			if (num % 2 == 0) {
				it.remove(); // safe — syncs modCount and expectedModCount
			}
		}

		System.out.println("After removing evens: " + numbers); // [1, 3, 5, 7, 9]
	}

	// ========================================================================
	// SOLUTION 3: removeIf() — Java 8+ (RECOMMENDED)
	// ========================================================================
	// Cleanest and most concise. Internally uses Iterator.remove().
	// Takes a Predicate — removes elements where predicate returns true.
	// Returns true if any elements were removed.
	// ========================================================================
	private static void solution3_removeIf() {
		List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

		boolean removed = numbers.removeIf(num -> num % 2 == 0);

		System.out.println("Any removed? " + removed);             // true
		System.out.println("After removing evens: " + numbers);    // [1, 3, 5, 7, 9]

		// More examples:
		List<String> names = new ArrayList<>(Arrays.asList("Alice", "Bob", "Charlie", "Dave", "Eve"));
		names.removeIf(name -> name.length() <= 3); // remove names with 3 or fewer chars
		System.out.println("Names (length > 3): " + names); // [Alice, Charlie, Dave]
	}

	// ========================================================================
	// SOLUTION 4: Iterate Backwards with Index
	// ========================================================================
	// When you remove by index going backwards, the indices of elements
	// BEFORE the current position are not affected (only elements AFTER shift).
	// So you never skip an element or go out of bounds.
	//
	// Forward iteration problem:
	//   [A, B, C, D] → remove index 1 (B) → [A, C, D]
	//   i moves to 2 → reads D → SKIPS C!
	//
	// Backward iteration:
	//   [A, B, C, D] → i=3 (D), i=2 (C), i=1 (B) → remove B → [A, C, D]
	//   i moves to 0 → reads A → nothing skipped!
	// ========================================================================
	private static void solution4_iterateBackwards() {
		List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

		for (int i = numbers.size() - 1; i >= 0; i--) {
			if (numbers.get(i) % 2 == 0) {
				numbers.remove(i); // safe — doesn't affect indices of earlier elements
			}
		}

		System.out.println("After removing evens: " + numbers); // [1, 3, 5, 7, 9]
	}

	// ========================================================================
	// SOLUTION 5: Use CopyOnWriteArrayList (Fail-Safe Iterator)
	// ========================================================================
	// CopyOnWriteArrayList's iterator works on a SNAPSHOT of the array taken
	// at the time of iterator creation. Modifications to the list create a
	// new internal array, so the iterator is never affected.
	//
	// Pros: Thread-safe, no CME ever
	// Cons: Every write copies the entire array — expensive for write-heavy use
	// Note: Iterator does NOT support remove() — throws UnsupportedOperationException
	// ========================================================================
	private static void solution5_copyOnWriteArrayList() {
		CopyOnWriteArrayList<Integer> numbers = new CopyOnWriteArrayList<>(
				Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

		// Direct removal during for-each — NO CME!
		for (Integer num : numbers) {
			if (num % 2 == 0) {
				numbers.remove(num); // creates new array copy each time, but no exception
			}
		}

		System.out.println("After removing evens: " + numbers); // [1, 3, 5, 7, 9]

		// Iterator.remove() is NOT supported on COWAL
		try {
			Iterator<Integer> it = numbers.iterator();
			it.next();
			it.remove();
		} catch (UnsupportedOperationException e) {
			System.out.println("COWAL iterator.remove() → UnsupportedOperationException");
		}

		// For COWAL, use removeIf() instead:
		CopyOnWriteArrayList<Integer> nums2 = new CopyOnWriteArrayList<>(
				Arrays.asList(1, 2, 3, 4, 5));
		nums2.removeIf(n -> n % 2 != 0);
		System.out.println("COWAL removeIf (keep evens): " + nums2); // [2, 4]
	}

	// ========================================================================
	// SOLUTION 6: Stream filter() — Create a New List
	// ========================================================================
	// Instead of modifying the original list, create a new filtered list.
	// This is a functional/immutable approach.
	//
	// Pros: Clean, functional, no side effects
	// Cons: Creates a new list (extra memory), original list unchanged
	// ========================================================================
	private static void solution6_streamFilter() {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		// Keep only odd numbers (filter OUT evens)
		List<Integer> odds = numbers.stream()
				.filter(num -> num % 2 != 0)
				.collect(Collectors.toList());

		System.out.println("Original (unchanged): " + numbers);
		System.out.println("Filtered (odds only): " + odds); // [1, 3, 5, 7, 9]
	}

	// ========================================================================
	// BONUS: Multi-threaded CME scenario & fix
	// ========================================================================
	// CME can also happen when one thread iterates while another modifies.
	// Fix: Use synchronizedList with manual synchronization, or COWAL.
	// ========================================================================
	private static void bonus_multiThreadedCME() {

		// PROBLEM: unsynchronized ArrayList shared between threads
		List<String> unsafeList = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E"));

		// FIX 1: synchronizedList — must manually sync during iteration
		List<String> syncList = Collections.synchronizedList(
				new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E")));

		Thread reader = new Thread(() -> {
			synchronized (syncList) { // MUST sync manually for iteration!
				for (String s : syncList) {
					System.out.println("  syncList reader: " + s);
					try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
				}
			}
		});

		Thread writer = new Thread(() -> {
			synchronized (syncList) { // writer also syncs on same object
				syncList.add("F");
				System.out.println("  syncList writer: added F");
			}
		});

		reader.start();
		writer.start();
		try { reader.join(); writer.join(); } catch (InterruptedException ignored) {}

		System.out.println("syncList final: " + syncList);

		// FIX 2: CopyOnWriteArrayList — no manual sync needed
		CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<>(
				Arrays.asList("X", "Y", "Z"));

		Thread cowReader = new Thread(() -> {
			for (String s : cowList) { // snapshot — safe without sync
				System.out.println("  COWAL reader: " + s);
				try { Thread.sleep(10); } catch (InterruptedException ignored) {}
			}
		});

		Thread cowWriter = new Thread(() -> {
			cowList.add("W");
			System.out.println("  COWAL writer: added W");
		});

		cowReader.start();
		cowWriter.start();
		try { cowReader.join(); cowWriter.join(); } catch (InterruptedException ignored) {}

		System.out.println("COWAL final: " + cowList);
	}
}
