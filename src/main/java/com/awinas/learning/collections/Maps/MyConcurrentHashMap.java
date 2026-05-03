package com.awinas.learning.collections.Maps;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

public class MyConcurrentHashMap {

	public static void main(String[] args) throws InterruptedException {

		// =============================================
		// 1. BASIC OPERATIONS (same as HashMap)
		// =============================================
		System.out.println("===== BASIC OPERATIONS =====");

		ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
		map.put("Alice", 85);
		map.put("Bob", 92);
		map.put("Charlie", 78);
		map.put("Diana", 95);

		System.out.println("Map: " + map);
		System.out.println("get(Bob): " + map.get("Bob"));
		System.out.println("size: " + map.size());
		System.out.println("containsKey(Alice): " + map.containsKey("Alice"));

		// NULL KEYS AND VALUES ARE NOT ALLOWED
		// map.put(null, 100);     // throws NullPointerException
		// map.put("Eve", null);   // throws NullPointerException

		// =============================================
		// 2. ATOMIC / THREAD-SAFE OPERATIONS
		// =============================================
		System.out.println("\n===== ATOMIC OPERATIONS =====");

		// Without putIfAbsent (NOT thread-safe — race condition between check and put):
		//   synchronized (map) {
		//       if (!map.containsKey("Alice")) {
		//           map.put("Alice", 999);
		//       }
		//   }
		Integer prev = map.putIfAbsent("Alice", 999);  // exists → returns old value, no change
		System.out.println("putIfAbsent(Alice, 999) returned: " + prev + ", value: " + map.get("Alice"));

		map.putIfAbsent("Eve", 88); // absent → inserted
		System.out.println("putIfAbsent(Eve, 88): " + map.get("Eve"));

		// Without replace(key, oldVal, newVal) — NOT thread-safe without sync:
		//   synchronized (map) {
		//       Integer cur = map.get("Bob");
		//       if (cur != null && cur.equals(92)) { map.put("Bob", 99); }
		//   }
		boolean replaced = map.replace("Bob", 92, 99); // CAS: only if current value is 92
		System.out.println("replace(Bob, 92→99): " + replaced + ", value: " + map.get("Bob"));

		// Without replace(key, value):
		//   synchronized (map) {
		//       if (map.containsKey("Charlie")) { map.put("Charlie", 80); }
		//   }
		map.replace("Charlie", 80);
		System.out.println("replace(Charlie, 80): " + map.get("Charlie"));

		// Without remove(key, value) — NOT thread-safe without sync:
		//   synchronized (map) {
		//       Integer cur = map.get("Diana");
		//       if (cur != null && cur.equals(95)) { map.remove("Diana"); }
		//   }
		boolean removed = map.remove("Diana", 999); // only if value is 999 → false
		System.out.println("remove(Diana, 999): " + removed);
		removed = map.remove("Diana", 95);           // value matches → removed
		System.out.println("remove(Diana, 95): " + removed + ", map: " + map);

		// =============================================
		// 3. COMPUTE FAMILY (all atomic)
		// =============================================
		System.out.println("\n===== ATOMIC COMPUTE FAMILY =====");

		// Without compute — NOT thread-safe without sync:
		//   synchronized (map) {
		//       Integer v = map.get("Alice");
		//       map.put("Alice", v + 10);
		//   }
		map.compute("Alice", (k, v) -> v + 10);
		System.out.println("compute(Alice, +10): " + map.get("Alice"));

		// Without computeIfAbsent — NOT thread-safe, two threads could both see null:
		//   synchronized (map) {
		//       if (!map.containsKey("Frank")) {
		//           map.put("Frank", "Frank".length() * 10);
		//       }
		//   }
		map.computeIfAbsent("Frank", k -> k.length() * 10);
		System.out.println("computeIfAbsent(Frank): " + map.get("Frank"));

		// Without computeIfPresent:
		//   synchronized (map) {
		//       Integer v = map.get("Bob");
		//       if (v != null) { map.put("Bob", v * 2); }
		//   }
		map.computeIfPresent("Bob", (k, v) -> v * 2);
		System.out.println("computeIfPresent(Bob, *2): " + map.get("Bob"));

		// Without merge — NOT thread-safe without sync:
		//   synchronized (map) {
		//       Integer old = map.get("Alice");
		//       if (old == null) { map.put("Alice", 5); }
		//       else { map.put("Alice", old + 5); }
		//   }
		map.merge("Alice", 5, Integer::sum);   // 95 + 5 = 100
		map.merge("Zara", 70, Integer::sum);    // absent → 70
		System.out.println("merge(Alice, +5): " + map.get("Alice"));
		System.out.println("merge(Zara, 70): " + map.get("Zara"));

		// =============================================
		// 4. THREAD-SAFE WORD COUNTER (classic use case)
		// =============================================
		System.out.println("\n===== CONCURRENT WORD COUNTER =====");

		// Without computeIfAbsent + LongAdder — verbose and NOT thread-safe:
		//   synchronized (wordCount) {
		//       LongAdder adder = wordCount.get(word);
		//       if (adder == null) {
		//           adder = new LongAdder();
		//           wordCount.put(word, adder);
		//       }
		//       adder.increment();
		//   }
		ConcurrentHashMap<String, LongAdder> wordCount = new ConcurrentHashMap<>();
		String[] words = {"java", "python", "java", "go", "java", "python", "rust", "go", "java"};

		for (String word : words) {
			wordCount.computeIfAbsent(word, k -> new LongAdder()).increment();
		}
		wordCount.forEach((word, count) -> System.out.println("  " + word + " → " + count));

		// =============================================
		// 5. BULK / PARALLEL OPERATIONS (Java 8+)
		// =============================================
		System.out.println("\n===== BULK PARALLEL OPERATIONS =====");

		ConcurrentHashMap<String, Integer> scores = new ConcurrentHashMap<>();
		scores.put("Alice", 85);
		scores.put("Bob", 92);
		scores.put("Charlie", 78);
		scores.put("Diana", 95);
		scores.put("Eve", 60);

		// Without parallel forEach — manual thread pool + iteration:
		//   ExecutorService pool = Executors.newFixedThreadPool(4);
		//   for (Map.Entry<String, Integer> e : scores.entrySet()) {
		//       pool.submit(() -> System.out.println(e.getKey() + "=" + e.getValue()));
		//   }
		// threshold = 1 → always parallel; Long.MAX_VALUE → sequential
		System.out.println("Parallel forEach:");
		scores.forEach(1, (k, v) -> System.out.println("  [" + Thread.currentThread().getName() + "] " + k + "=" + v));

		// Without search — manual loop:
		//   String topScorer = null;
		//   for (Map.Entry<String, Integer> e : scores.entrySet()) {
		//       if (e.getValue() >= 95) { topScorer = e.getKey(); break; }
		//   }
		String topScorer = scores.search(1, (k, v) -> v >= 95 ? k : null);
		System.out.println("First scorer ≥ 95: " + topScorer);

		// Without reduce — manual loop:
		//   int totalScore = 0;
		//   for (int v : scores.values()) { totalScore += v; }
		int totalScore = scores.reduce(1, (k, v) -> v, Integer::sum);
		System.out.println("Total score (reduce): " + totalScore);

		// Without reduceValues — manual loop:
		//   int maxScore = Integer.MIN_VALUE;
		//   for (int v : scores.values()) { maxScore = Math.max(maxScore, v); }
		int maxScore = scores.reduceValues(1, Integer::max);
		System.out.println("Max score (reduceValues): " + maxScore);

		// mappingCount — returns long (better than size() for large maps)
		System.out.println("mappingCount: " + scores.mappingCount());

		// =============================================
		// 6. KEY SET VIEWS
		// =============================================
		System.out.println("\n===== KEY SET VIEWS =====");

		// keySet() — standard set view
		Set<String> keys = scores.keySet();
		System.out.println("keySet: " + keys);

		// newKeySet() — creates a concurrent Set backed by CHM
		Set<String> concurrentSet = ConcurrentHashMap.newKeySet();
		concurrentSet.add("X");
		concurrentSet.add("Y");
		concurrentSet.add("Z");
		System.out.println("newKeySet: " + concurrentSet);

		// keySet(defaultValue) — set view where add() inserts with this default value
		Set<String> keySetWithDefault = scores.keySet(0);
		keySetWithDefault.add("NewPlayer");
		System.out.println("After keySet(0).add(NewPlayer): " + scores.get("NewPlayer"));

		// =============================================
		// 7. MULTI-THREADED CONCURRENT ACCESS DEMO
		// =============================================
		System.out.println("\n===== CONCURRENT ACCESS DEMO =====");

		// Without merge — requires explicit synchronization:
		//   synchronized (counter) {
		//       counter.put("hits", counter.get("hits") + 1);
		//   }
		// Or use AtomicInteger. merge() does this atomically in one call.
		ConcurrentHashMap<String, Integer> counter = new ConcurrentHashMap<>();
		counter.put("hits", 0);

		ExecutorService executor = Executors.newFixedThreadPool(4);
		for (int i = 0; i < 1000; i++) {
			executor.submit(() -> counter.merge("hits", 1, Integer::sum));
		}
		executor.shutdown();
		executor.awaitTermination(5, TimeUnit.SECONDS);

		// Always 1000 — no lost updates, no ConcurrentModificationException
		System.out.println("Final hit count (expected 1000): " + counter.get("hits"));

		// =============================================
		// 8. getOrDefault / replaceAll / forEach
		// =============================================
		System.out.println("\n===== UTILITY METHODS =====");

		// Without getOrDefault:
		//   Integer val = scores.get("Unknown");
		//   if (val == null) { val = -1; }
		System.out.println("getOrDefault(Unknown, -1): " + scores.getOrDefault("Unknown", -1));

		// Without replaceAll:
		//   for (Map.Entry<String, Integer> e : scores.entrySet()) {
		//       e.setValue(e.getValue() + 5);
		//   }
		scores.replaceAll((k, v) -> v + 5);
		System.out.println("After replaceAll +5: " + scores);

		scores.forEach((k, v) -> System.out.println("  " + k + " = " + v));
	}

}
