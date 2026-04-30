package com.awinas.learning.collections.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

// ============================================================================
// Synchronized List in Java — Thread-Safe List Options
// ============================================================================
//
// Problem: ArrayList and LinkedList are NOT thread-safe.
//          Multiple threads reading/writing simultaneously causes:
//          - Data corruption (lost updates, inconsistent state)
//          - ConcurrentModificationException
//
// 3 Ways to get a Thread-Safe List:
// ┌──────────────────────────┬────────────────────────────────────────────────┐
// │ Option                   │ How it works                                  │
// ├──────────────────────────┼────────────────────────────────────────────────┤
// │ 1. Vector                │ Every method is synchronized (legacy, avoid)  │
// │ 2. synchronizedList()    │ Wraps any List with synchronized methods      │
// │ 3. CopyOnWriteArrayList  │ Copy-on-write, snapshot iterators (read-heavy)│
// └──────────────────────────┴────────────────────────────────────────────────┘
//
// This class demonstrates all three with pros, cons, gotchas, and benchmarks.
// ============================================================================

public class MySynchronizedList {

	public static void main(String[] args) throws InterruptedException {

		System.out.println("====== 1. THE PROBLEM: ArrayList is NOT thread-safe ======\n");
		problem_unsafeArrayList();

		System.out.println("\n====== 2. Collections.synchronizedList() ======\n");
		demo_synchronizedList();

		System.out.println("\n====== 3. synchronizedList — ITERATION GOTCHA ======\n");
		demo_iterationGotcha();

		System.out.println("\n====== 4. Vector (Legacy) ======\n");
		demo_vector();

		System.out.println("\n====== 5. CopyOnWriteArrayList ======\n");
		demo_copyOnWriteArrayList();

		System.out.println("\n====== 6. COMPARISON: All 3 approaches ======\n");
		demo_comparison();

		System.out.println("\n====== 7. When to Use What — Decision Guide ======\n");
		printDecisionGuide();
	}

	// ========================================================================
	// 1. THE PROBLEM: Why ArrayList is not thread-safe
	// ========================================================================
	// Two threads adding to the same ArrayList simultaneously can cause:
	//   - Lost elements (both threads write to the same index)
	//   - ArrayIndexOutOfBoundsException (size check and insert are not atomic)
	//   - Null values in the list
	// ========================================================================
	private static void problem_unsafeArrayList() throws InterruptedException {
		List<Integer> unsafeList = new ArrayList<>();

		Runnable addTask = () -> {
			for (int i = 0; i < 1000; i++) {
				unsafeList.add(i);
			}
		};

		Thread t1 = new Thread(addTask, "Writer-1");
		Thread t2 = new Thread(addTask, "Writer-2");

		t1.start();
		t2.start();
		t1.join();
		t2.join();

		// Expected: 2000 elements. Actual: often less due to race conditions
		System.out.println("Expected size: 2000");
		System.out.println("Actual size  : " + unsafeList.size() + " (may vary due to race condition)");

		long nullCount = unsafeList.stream().filter(i -> i == null).count();
		if (nullCount > 0) {
			System.out.println("Null entries : " + nullCount + " (data corruption!)");
		}
	}

	// ========================================================================
	// 2. Collections.synchronizedList() — How it works
	// ========================================================================
	//
	// Internally creates a wrapper class (SynchronizedList) that delegates
	// every method call to the wrapped list inside a synchronized block:
	//
	//   public E get(int index) {
	//       synchronized (mutex) { return list.get(index); }
	//   }
	//   public void add(int index, E element) {
	//       synchronized (mutex) { list.add(index, element); }
	//   }
	//
	// The mutex is the wrapper object itself (this), unless you pass a
	// custom mutex via Collections.synchronizedList(list, mutex).
	//
	// IMPORTANT: Individual method calls are atomic, but COMPOUND operations
	//            (check-then-act, iteration) are NOT automatically safe!
	// ========================================================================
	private static void demo_synchronizedList() throws InterruptedException {
		List<Integer> syncList = Collections.synchronizedList(new ArrayList<>());

		Runnable addTask = () -> {
			for (int i = 0; i < 1000; i++) {
				syncList.add(i);
			}
		};

		Thread t1 = new Thread(addTask, "Writer-1");
		Thread t2 = new Thread(addTask, "Writer-2");

		t1.start();
		t2.start();
		t1.join();
		t2.join();

		System.out.println("Expected size: 2000");
		System.out.println("Actual size  : " + syncList.size() + " (always 2000 — thread-safe!)");

		// Individual operations are atomic
		syncList.add(999);          // thread-safe
		syncList.get(0);            // thread-safe
		syncList.remove(0);         // thread-safe
		syncList.set(0, 100);       // thread-safe
		syncList.size();            // thread-safe
		syncList.contains(100);     // thread-safe

		// COMPOUND operations are NOT atomic — must sync manually
		// BAD: check-then-act without sync
		// if (!syncList.contains(42)) {
		//     syncList.add(42);        // another thread could add 42 between check and add!
		// }

		// GOOD: manual sync for compound operations
		synchronized (syncList) {
			if (!syncList.contains(42)) {
				syncList.add(42);
			}
		}
		System.out.println("Compound check-then-add done safely with manual sync");
	}

	// ========================================================================
	// 3. synchronizedList — ITERATION GOTCHA (most common interview question!)
	// ========================================================================
	//
	// The iterator returned by synchronizedList is NOT synchronized!
	// From the Javadoc:
	//   "It is imperative that the user manually synchronize on the returned
	//    list when iterating over it."
	//
	// Why? Because the iterator makes multiple calls (hasNext, next) and
	// the list could be modified between those calls by another thread.
	// ========================================================================
	private static void demo_iterationGotcha() {
		List<String> syncList = Collections.synchronizedList(
				new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E")));

		// ❌ WRONG — ConcurrentModificationException possible!
		// for (String s : syncList) {
		//     System.out.println(s);
		// }

		// ❌ WRONG — Iterator is not synchronized
		// Iterator<String> it = syncList.iterator();
		// while (it.hasNext()) {
		//     System.out.println(it.next());  // another thread could modify between hasNext and next
		// }

		// ✅ CORRECT — Manual synchronization on the list object
		System.out.println("Safe iteration with synchronized block:");
		synchronized (syncList) {
			for (String s : syncList) {
				System.out.print(s + " ");
			}
		}
		System.out.println();

		// ✅ CORRECT — Using iterator inside synchronized block
		System.out.println("Safe iteration with iterator + synchronized:");
		synchronized (syncList) {
			Iterator<String> it = syncList.iterator();
			while (it.hasNext()) {
				System.out.print(it.next() + " ");
			}
		}
		System.out.println();

		// ✅ CORRECT — forEach (Java 8+) — synchronized internally for synchronizedList
		System.out.print("forEach() call     : ");
		syncList.forEach(s -> System.out.print(s + " "));
		System.out.println();

		// ✅ CORRECT — stream() inside synchronized block
		synchronized (syncList) {
			String joined = syncList.stream().reduce("", (a, b) -> a + b + " ");
			System.out.println("stream() in sync   : " + joined.trim());
		}
	}

	// ========================================================================
	// 4. Vector (Legacy — since Java 1.0)
	// ========================================================================
	//
	// Vector is the original synchronized List implementation.
	// Every method (get, set, add, remove, size, etc.) is individually
	// synchronized using the `synchronized` keyword on the method itself.
	//
	// Key differences from synchronizedList:
	// ┌────────────────────────┬─────────────────────────┬──────────────────┐
	// │ Feature                │ Vector                  │ synchronizedList │
	// ├────────────────────────┼─────────────────────────┼──────────────────┤
	// │ Lock object            │ this (the Vector)       │ mutex (wrapper)  │
	// │ Growth strategy        │ doubles (100% growth)   │ 50% growth (AL)  │
	// │ Legacy methods         │ elements(), capacity()  │ none             │
	// │ Introduced             │ Java 1.0                │ Java 1.2         │
	// │ Iterator               │ fail-fast               │ fail-fast        │
	// │ Enumeration            │ NOT fail-fast           │ N/A              │
	// └────────────────────────┴─────────────────────────┴──────────────────┘
	//
	// WHY AVOID VECTOR:
	// 1. Synchronizes ALL methods — even when no concurrency is needed (slow)
	// 2. Same iteration gotcha as synchronizedList — not safe without manual sync
	// 3. Modern alternatives are better: synchronizedList, CopyOnWriteArrayList
	// ========================================================================
	private static void demo_vector() {
		Vector<String> vector = new Vector<>();

		// Basic operations (all synchronized)
		vector.add("Apple");
		vector.add("Banana");
		vector.add("Cherry");
		vector.addElement("Date");       // legacy method (same as add)
		vector.insertElementAt("Fig", 2); // legacy method (same as add at index)
		System.out.println("Vector          : " + vector);

		// Vector-specific methods
		System.out.println("capacity()      : " + vector.capacity()); // default 10
		System.out.println("elementAt(0)    : " + vector.elementAt(0)); // legacy get()
		System.out.println("firstElement()  : " + vector.firstElement());
		System.out.println("lastElement()   : " + vector.lastElement());

		// Growth: Vector doubles in size (vs ArrayList's 50% growth)
		// Default capacity = 10, grows to 20, 40, 80, 160...
		Vector<Integer> growthDemo = new Vector<>();
		System.out.print("Capacity growth : ");
		for (int i = 0; i < 50; i++) {
			if (i == 0 || growthDemo.size() == growthDemo.capacity()) {
				System.out.print(growthDemo.capacity() + " → ");
			}
			growthDemo.add(i);
		}
		System.out.println(growthDemo.capacity());

		// You can specify initial capacity AND capacity increment
		Vector<String> customGrowth = new Vector<>(5, 3); // initial=5, grows by 3 each time
		System.out.println("Custom Vector capacity: " + customGrowth.capacity()); // 5

		// Thread-safe demo
		Vector<Integer> sharedVector = new Vector<>();
		Runnable task = () -> {
			for (int i = 0; i < 500; i++) sharedVector.add(i);
		};
		Thread t1 = new Thread(task);
		Thread t2 = new Thread(task);
		t1.start();
		t2.start();
		try { t1.join(); t2.join(); } catch (InterruptedException ignored) {}
		System.out.println("Vector size (2 threads × 500): " + sharedVector.size()); // always 1000

		// GOTCHA: Iteration is still NOT safe without manual sync (same as synchronizedList)
		// synchronized (vector) {
		//     for (String s : vector) { ... }
		// }
	}

	// ========================================================================
	// 5. CopyOnWriteArrayList — Snapshot-based thread safety
	// ========================================================================
	// Covered in detail in MyCopyOnWriteArrayList.java.
	// Quick comparison here to show when it's better than synchronizedList.
	// ========================================================================
	private static void demo_copyOnWriteArrayList() throws InterruptedException {
		CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<>(
				Arrays.asList("A", "B", "C"));

		// Read-heavy scenario: multiple readers, one writer
		Thread reader1 = new Thread(() -> {
			for (String s : cowList) { // snapshot — no sync needed, no CME
				System.out.println("  Reader-1: " + s);
				try { Thread.sleep(20); } catch (InterruptedException ignored) {}
			}
		});

		Thread reader2 = new Thread(() -> {
			for (String s : cowList) { // another reader — also safe
				System.out.println("  Reader-2: " + s);
				try { Thread.sleep(20); } catch (InterruptedException ignored) {}
			}
		});

		Thread writer = new Thread(() -> {
			cowList.add("D");
			System.out.println("  Writer  : added D");
			cowList.add("E");
			System.out.println("  Writer  : added E");
		});

		reader1.start();
		reader2.start();
		writer.start();
		reader1.join();
		reader2.join();
		writer.join();

		System.out.println("Final COWAL: " + cowList);
		System.out.println("Readers saw snapshot [A, B, C] — may not see D, E");
	}

	// ========================================================================
	// 6. COMPARISON: Performance characteristics
	// ========================================================================
	private static void demo_comparison() throws InterruptedException {
		int iterations = 100_000;

		// --- synchronizedList performance ---
		List<Integer> syncList = Collections.synchronizedList(new ArrayList<>());
		long start = System.nanoTime();
		for (int i = 0; i < iterations; i++) syncList.add(i);
		long syncWriteTime = System.nanoTime() - start;

		start = System.nanoTime();
		synchronized (syncList) {
			for (Integer i : syncList) { /* read */ }
		}
		long syncReadTime = System.nanoTime() - start;

		// --- CopyOnWriteArrayList performance ---
		// Using fewer iterations for writes because COWAL copies the entire array each time
		CopyOnWriteArrayList<Integer> cowList = new CopyOnWriteArrayList<>();
		int cowIterations = 10_000;
		start = System.nanoTime();
		for (int i = 0; i < cowIterations; i++) cowList.add(i);
		long cowWriteTime = System.nanoTime() - start;

		start = System.nanoTime();
		for (Integer i : cowList) { /* read */ }
		long cowReadTime = System.nanoTime() - start;

		// --- Vector performance ---
		Vector<Integer> vector = new Vector<>();
		start = System.nanoTime();
		for (int i = 0; i < iterations; i++) vector.add(i);
		long vectorWriteTime = System.nanoTime() - start;

		start = System.nanoTime();
		synchronized (vector) {
			for (Integer i : vector) { /* read */ }
		}
		long vectorReadTime = System.nanoTime() - start;

		System.out.println("Write Performance (" + iterations + " adds, COWAL uses " + cowIterations + "):");
		System.out.printf("  synchronizedList : %,d ns%n", syncWriteTime);
		System.out.printf("  Vector           : %,d ns%n", vectorWriteTime);
		System.out.printf("  COWAL (" + cowIterations + " adds) : %,d ns (copies array each time!)%n", cowWriteTime);

		System.out.println("\nRead Performance (full iteration):");
		System.out.printf("  synchronizedList : %,d ns%n", syncReadTime);
		System.out.printf("  Vector           : %,d ns%n", vectorReadTime);
		System.out.printf("  COWAL            : %,d ns (no lock — fastest reads!)%n", cowReadTime);
	}

	// ========================================================================
	// 7. When to Use What — Decision Guide
	// ========================================================================
	private static void printDecisionGuide() {
		String guide = """
				┌──────────────────────────────────────────────────────────────────┐
				│           THREAD-SAFE LIST — DECISION GUIDE                      │
				├──────────────────────────────────────────────────────────────────┤
				│                                                                  │
				│  Q: Do you need thread safety?                                   │
				│  ├── NO  → Use ArrayList (fastest, no sync overhead)             │
				│  │                                                                │
				│  └── YES → Q: What's your read/write ratio?                      │
				│       │                                                           │
				│       ├── Reads >> Writes (e.g., listener lists, config)         │
				│       │   → Use CopyOnWriteArrayList                             │
				│       │   ✓ No lock on reads                                     │
				│       │   ✓ Snapshot iterators — never CME                       │
				│       │   ✗ Expensive writes (copies entire array)               │
				│       │                                                           │
				│       ├── Balanced reads & writes                                 │
				│       │   → Use Collections.synchronizedList(new ArrayList<>())  │
				│       │   ✓ Same performance as Vector, more flexible             │
				│       │   ✗ Must manually sync during iteration                  │
				│       │                                                           │
				│       └── Legacy codebase requires it                             │
				│           → Use Vector (but prefer synchronizedList)              │
				│           ✗ Synchronizes everything, even when not needed         │
				│           ✗ Legacy API (elements(), Enumeration)                  │
				│                                                                   │
				├──────────────────────────────────────────────────────────────────┤
				│  SUMMARY TABLE                                                   │
				│                                                                   │
				│  Feature         │ syncList   │ Vector   │ COWAL               │
				│  ─────────────── │ ────────── │ ──────── │ ─────────────────── │
				│  Lock mechanism  │ mutex obj  │ this     │ ReentrantLock(write)│
				│  Read lock?      │ Yes        │ Yes      │ No (volatile read) │
				│  Write lock?     │ Yes        │ Yes      │ Yes (copies array) │
				│  Iterator safe?  │ No         │ No       │ Yes (snapshot)     │
				│  Read speed      │ Moderate   │ Moderate │ Fast               │
				│  Write speed     │ Moderate   │ Moderate │ Slow               │
				│  Memory          │ Low        │ Low      │ High on write      │
				│  Growth          │ 50% (AL)   │ 100%     │ N/A (new array)    │
				│  Since           │ Java 1.2   │ Java 1.0 │ Java 1.5           │
				└──────────────────────────────────────────────────────────────────┘
				""";
		System.out.println(guide);
	}
}
