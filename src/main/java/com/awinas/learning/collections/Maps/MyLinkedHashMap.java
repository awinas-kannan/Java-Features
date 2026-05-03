package com.awinas.learning.collections.Maps;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyLinkedHashMap {

	public static void main(String[] args) {

		// =============================================
		// 1. INSERTION ORDER (default behavior)
		// =============================================
		System.out.println("===== INSERTION ORDER =====");

		Map<Integer, String> pairs = new LinkedHashMap<>();
		pairs.put(3, "C");
		pairs.put(1, "A");
		pairs.put(4, "D");
		pairs.put(2, "B");
		// Iteration always follows insertion order: 3, 1, 4, 2
		pairs.forEach((key, value) -> System.out.println("  " + key + " → " + value));

		// =============================================
		// 2. ACCESS ORDER (accessOrder = true)
		// =============================================
		System.out.println("\n===== ACCESS ORDER =====");
		// 3rd constructor arg = true → iteration order = last-access order
		LinkedHashMap<Integer, String> accessMap = new LinkedHashMap<>(16, 0.75f, true);
		accessMap.put(1, "A");
		accessMap.put(2, "B");
		accessMap.put(3, "C");
		accessMap.put(4, "D");

		System.out.println("Initial order:");
		accessMap.forEach((k, v) -> System.out.println("  " + k + " → " + v));

		// Access key 2 → moves to tail
		accessMap.get(2);
		// Access key 1 → moves to tail
		accessMap.get(1);

		System.out.println("After get(2), get(1) — accessed items move to end:");
		accessMap.forEach((k, v) -> System.out.println("  " + k + " → " + v));

		// =============================================
		// 3. LRU CACHE using removeEldestEntry
		// =============================================
		System.out.println("\n===== LRU CACHE (max 3 entries) =====");

		final int MAX_ENTRIES = 3;
		LinkedHashMap<String, String> lruCache = new LinkedHashMap<>(16, 0.75f, true) {
			@Override
			protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
				return size() > MAX_ENTRIES;
			}
		};

		lruCache.put("page1", "Home");
		lruCache.put("page2", "About");
		lruCache.put("page3", "Contact");
		System.out.println("Cache full: " + lruCache);

		lruCache.get("page1"); // access page1 → now it's "most recent"

		lruCache.put("page4", "Blog"); // evicts page2 (least recently used)
		System.out.println("After access page1, add page4 → page2 evicted: " + lruCache);

		lruCache.put("page5", "FAQ"); // evicts page3
		System.out.println("After add page5 → page3 evicted: " + lruCache);

		// =============================================
		// 4. putIfAbsent / getOrDefault
		// =============================================
		// Without putIfAbsent:
		//   if (!pairs.containsKey(2)) {
		//       pairs.put(2, "OVERWRITE?");
		//   }
		//
		// Without getOrDefault:
		//   String val = pairs.get(999);
		//   if (val == null) { val = "NOT_FOUND"; }
		System.out.println("\n===== putIfAbsent / getOrDefault =====");
		pairs.putIfAbsent(2, "OVERWRITE?"); // key exists → no change
		pairs.putIfAbsent(5, "E");           // key absent → inserted
		System.out.println("putIfAbsent: " + pairs);

		System.out.println("getOrDefault(999): " + pairs.getOrDefault(999, "NOT_FOUND"));

		// =============================================
		// 5. compute / merge
		// =============================================
		System.out.println("\n===== compute / merge =====");

		// Without merge:
		//   Integer count = pageViews.get(page);
		//   if (count == null) { pageViews.put(page, 1); }
		//   else { pageViews.put(page, count + 1); }
		Map<String, Integer> pageViews = new LinkedHashMap<>();
		String[] pages = {"home", "about", "home", "contact", "home", "about"};
		for (String page : pages) {
			pageViews.merge(page, 1, Integer::sum);
		}
		System.out.println("Page views (insertion order preserved): " + pageViews);

		// Without compute:
		//   Integer v = pageViews.get("home");
		//   if (v != null) { pageViews.put("home", v * 10); }
		pageViews.compute("home", (k, v) -> v * 10);
		System.out.println("After compute home*10: " + pageViews);

		// =============================================
		// 6. replaceAll / replace
		// =============================================
		// Without replaceAll:
		//   for (Map.Entry<Integer, String> e : pairs.entrySet()) {
		//       e.setValue(e.getValue().toLowerCase());
		//   }
		System.out.println("\n===== replaceAll / replace =====");
		pairs.replaceAll((k, v) -> v.toLowerCase());
		System.out.println("After replaceAll toLowerCase: " + pairs);

		// Without replace(key, value):
		//   if (pairs.containsKey(1)) { pairs.put(1, "alpha"); }
		pairs.replace(1, "alpha");
		System.out.println("After replace(1, alpha): " + pairs);

		// Without replace(key, oldVal, newVal):
		//   String cur = pairs.get(2);
		//   if ("b".equals(cur)) { pairs.put(2, "beta"); }
		boolean cas = pairs.replace(2, "b", "beta"); // CAS-style
		System.out.println("CAS replace(2, b, beta): " + cas + " → " + pairs.get(2));

		// =============================================
		// 7. containsKey / containsValue / size
		// =============================================
		System.out.println("\n===== Query Methods =====");
		System.out.println("containsKey(1): " + pairs.containsKey(1));
		System.out.println("containsValue(\"alpha\"): " + pairs.containsValue("alpha"));
		System.out.println("size: " + pairs.size());

		// =============================================
		// 8. entrySet / keySet / values — all maintain order
		// =============================================
		System.out.println("\n===== Ordered Views =====");
		System.out.println("keySet (ordered): " + pairs.keySet());
		System.out.println("values (ordered): " + pairs.values());
	}

}
