package com.awinas.learning.collections.Maps;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

public class MyTreeMap {

	public static void main(String[] args) {

		// =============================================
		// 1. NATURAL ORDERING (keys must be Comparable)
		// =============================================
		System.out.println("===== NATURAL ORDERING =====");

		TreeMap<Integer, String> map = new TreeMap<>();
		map.put(5, "Five");
		map.put(2, "Two");
		map.put(8, "Eight");
		map.put(1, "One");
		map.put(4, "Four");
		map.put(7, "Seven");
		map.put(3, "Three");
		map.put(6, "Six");

		// Always sorted by key: 1,2,3,4,5,6,7,8
		System.out.println("Sorted map: " + map);

		// =============================================
		// 2. BASIC OPERATIONS
		// =============================================
		System.out.println("\n===== BASIC OPERATIONS =====");
		System.out.println("get(3): " + map.get(3));
		// Without getOrDefault:
		//   String val = map.get(99);
		//   if (val == null) { val = "MISSING"; }
		System.out.println("getOrDefault(99, \"MISSING\"): " + map.getOrDefault(99, "MISSING"));
		System.out.println("containsKey(5): " + map.containsKey(5));
		System.out.println("containsValue(\"Two\"): " + map.containsValue("Two"));
		System.out.println("size: " + map.size());

		// =============================================
		// 3. FIRST / LAST
		// =============================================
		System.out.println("\n===== FIRST / LAST =====");
		System.out.println("firstKey: " + map.firstKey());         // 1
		System.out.println("lastKey: " + map.lastKey());           // 8
		System.out.println("firstEntry: " + map.firstEntry());     // 1=One
		System.out.println("lastEntry: " + map.lastEntry());       // 8=Eight

		// Without pollFirstEntry:
		//   Map.Entry<Integer, String> first = map.firstEntry();
		//   if (first != null) { map.remove(first.getKey()); }
		//   // use first
		System.out.println("pollFirstEntry: " + map.pollFirstEntry()); // removes 1=One
		System.out.println("pollLastEntry: " + map.pollLastEntry());   // removes 8=Eight
		System.out.println("After polls: " + map);
		// restore
		map.put(1, "One");
		map.put(8, "Eight");

		// =============================================
		// 4. FLOOR / CEILING / LOWER / HIGHER
		// =============================================
		System.out.println("\n===== NAVIGABLE KEY LOOKUPS =====");
		// Without floorKey (greatest key ≤ given):
		//   Integer floor = null;
		//   for (Integer k : map.keySet()) {
		//       if (k <= 5) floor = k;
		//       else break;
		//   }
		System.out.println("floorKey(5): " + map.floorKey(5));     // 5 (exact match)
		System.out.println("floorKey(9): " + map.floorKey(9));     // 8

		// Without ceilingKey (smallest key ≥ given):
		//   Integer ceil = null;
		//   for (Integer k : map.keySet()) {
		//       if (k >= 5) { ceil = k; break; }
		//   }
		System.out.println("ceilingKey(5): " + map.ceilingKey(5)); // 5 (exact match)
		System.out.println("ceilingKey(0): " + map.ceilingKey(0)); // 1

		// Without lowerKey (greatest key strictly < given):
		//   Integer lower = null;
		//   for (Integer k : map.keySet()) {
		//       if (k < 5) lower = k; else break;
		//   }
		System.out.println("lowerKey(5): " + map.lowerKey(5));     // 4

		// Without higherKey (smallest key strictly > given):
		//   Integer higher = null;
		//   for (Integer k : map.keySet()) {
		//       if (k > 5) { higher = k; break; }
		//   }
		System.out.println("higherKey(5): " + map.higherKey(5));   // 6

		// Entry versions
		System.out.println("floorEntry(5): " + map.floorEntry(5));
		System.out.println("ceilingEntry(0): " + map.ceilingEntry(0));

		// =============================================
		// 5. SUB-MAP / HEAD-MAP / TAIL-MAP (range views)
		// =============================================
		System.out.println("\n===== RANGE VIEWS =====");

		// Without subMap — manual range filter:
		//   Map<Integer, String> sub = new TreeMap<>();
		//   for (Map.Entry<Integer, String> e : map.entrySet()) {
		//       if (e.getKey() >= 3 && e.getKey() < 7) sub.put(e.getKey(), e.getValue());
		//   }
		SortedMap<Integer, String> sub = map.subMap(3, 7);
		System.out.println("subMap(3, 7): " + sub); // {3, 4, 5, 6}

		NavigableMap<Integer, String> subInclusive = map.subMap(3, true, 7, true);
		System.out.println("subMap(3 incl, 7 incl): " + subInclusive); // {3, 4, 5, 6, 7}

		// Without headMap — manual filter:
		//   Map<Integer, String> head = new TreeMap<>();
		//   for (Map.Entry<Integer, String> e : map.entrySet()) {
		//       if (e.getKey() < 5) head.put(e.getKey(), e.getValue());
		//   }
		System.out.println("headMap(5): " + map.headMap(5));       // {1,2,3,4}
		System.out.println("headMap(5, true): " + map.headMap(5, true)); // {1,2,3,4,5}

		// Without tailMap — manual filter:
		//   Map<Integer, String> tail = new TreeMap<>();
		//   for (Map.Entry<Integer, String> e : map.entrySet()) {
		//       if (e.getKey() >= 5) tail.put(e.getKey(), e.getValue());
		//   }
		System.out.println("tailMap(5): " + map.tailMap(5));       // {5,6,7,8}
		System.out.println("tailMap(5, false): " + map.tailMap(5, false)); // {6,7,8}

		// Range views are LIVE — changes reflect in original map
		sub.put(3, "THREE_MODIFIED");
		System.out.println("Original map after subMap put: " + map.get(3));

		// =============================================
		// 6. DESCENDING MAP / DESCENDING KEY SET
		// =============================================
		// Without descendingMap:
		//   TreeMap<Integer, String> desc = new TreeMap<>(Collections.reverseOrder());
		//   desc.putAll(map);
		System.out.println("\n===== DESCENDING =====");
		System.out.println("descendingMap: " + map.descendingMap());
		System.out.println("descendingKeySet: " + map.descendingKeySet());

		// =============================================
		// 7. REVERSE ORDER via Comparator
		// =============================================
		System.out.println("\n===== REVERSE ORDER TREEMAP =====");
		TreeMap<Integer, String> reverseMap = new TreeMap<>(Comparator.reverseOrder());
		reverseMap.putAll(map);
		System.out.println("Reverse ordered: " + reverseMap);

		// =============================================
		// 8. CUSTOM COMPARATOR (sort by string length)
		// =============================================
		System.out.println("\n===== CUSTOM COMPARATOR =====");
		TreeMap<String, Integer> byLength = new TreeMap<>(
				Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder())
		);
		byLength.put("banana", 1);
		byLength.put("fig", 2);
		byLength.put("apple", 3);
		byLength.put("kiwi", 4);
		System.out.println("Sorted by name length: " + byLength);

		// =============================================
		// 9. compute / merge / replaceAll
		// =============================================
		System.out.println("\n===== compute / merge / replaceAll =====");

		// Without merge:
		//   String old = map.get(5);
		//   if (old == null) { map.put(5, "FIVE_MERGED"); }
		//   else { map.put(5, old + "+" + "FIVE_MERGED"); }
		map.merge(5, "FIVE_MERGED", (oldV, newV) -> oldV + "+" + newV);
		System.out.println("After merge(5): " + map.get(5));

		// Without compute:
		//   String v = map.get(2);
		//   map.put(2, v.toUpperCase());
		map.compute(2, (k, v) -> v.toUpperCase());
		System.out.println("After compute(2) toUpperCase: " + map.get(2));

		// Without replaceAll:
		//   for (Map.Entry<Integer, String> e : map.entrySet()) {
		//       e.setValue(e.getValue() + "_v2");
		//   }
		map.replaceAll((k, v) -> v + "_v2");
		System.out.println("After replaceAll: " + map);

		// =============================================
		// 10. SYNCHRONIZED TREEMAP (thread-safe wrapper)
		// =============================================
		System.out.println("\n===== SYNCHRONIZED TREEMAP =====");
		Map<Integer, String> syncMap = Collections.synchronizedSortedMap(new TreeMap<>());
		syncMap.put(1, "A");
		syncMap.put(2, "B");
		// Must externally synchronize on syncMap during iteration
		System.out.println("Synchronized TreeMap: " + syncMap);
	}

}
