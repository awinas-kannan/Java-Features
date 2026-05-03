package com.awinas.learning.collections.Maps;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

//https://howtodoinjava.com/java-hashmap/

//hash vaue is zero for null key

//https://stackoverflow.com/questions/948194/difference-between-java-enumeration-and-iterator#:~:text=1)%20The%20main%20difference%20between,not%20have%20remove()%20method.&text=Iterator%20is%20not%20a%20legacy%20interface.

//Load factor
//https://www.geeksforgeeks.org/load-factor-in-hashmap-in-java-with-examples/
//https://stackoverflow.com/questions/10901752/what-is-the-significance-of-load-factor-in-hashmap
//Index = hashcode(key) & (ArraySize � 1)

public class MyHashMap {

	public static void main(String[] args) {

		// =============================================
		// 1. HASHING INTERNALS DEMO
		// =============================================
		System.out.println("===== HASHING INTERNALS =====");

		String tempKey = "Awinas";
		int h;
		System.out.println("hashCode()        : " + tempKey.hashCode());
		System.out.println("hashCode() >>> 16 : " + (tempKey.hashCode() >>> 16));
		System.out.println("Spread hash       : " + ((h = tempKey.hashCode()) ^ (h >>> 16)));
		// Bucket index = (n-1) & hash, where n = table length (power of 2)
		int tableSize = 16;
		System.out.println("Bucket index (n=16): " + (((h = tempKey.hashCode()) ^ (h >>> 16)) & (tableSize - 1)));

		// =============================================
		// 2. BASIC PUT / GET / REMOVE
		// =============================================
		System.out.println("\n===== BASIC OPERATIONS =====");

		Map<Integer, String> map = new HashMap<>();
		map.put(1, "A");
		map.put(2, "B");
		map.put(3, "C");
		map.put(4, "D");
		map.put(3, "E");       // overwrites key=3 → "E"
		map.put(null, "NullVal"); // null key allowed (hashes to bucket 0)
		map.put(77, null);       // null value allowed
		System.out.println("Map: " + map);

		System.out.println("get(3): " + map.get(3));
		System.out.println("remove(1): " + map.remove(1));
		System.out.println("After remove(1): " + map);

		// =============================================
		// 3. getOrDefault — avoid null checks
		// =============================================
		// Without getOrDefault:
		//   String val = map.get(999);
		//   if (val == null) { val = "MISSING"; }
		System.out.println("\n===== getOrDefault =====");
		System.out.println("getOrDefault(999, \"MISSING\"): " + map.getOrDefault(999, "MISSING"));
		System.out.println("getOrDefault(2, \"MISSING\"): " + map.getOrDefault(2, "MISSING"));

		// =============================================
		// 4. putIfAbsent — put only if key is missing
		// =============================================
		// Without putIfAbsent:
		//   if (!map.containsKey(2)) {
		//       map.put(2, "OVERWRITE?");
		//   }
		System.out.println("\n===== putIfAbsent =====");
		map.putIfAbsent(2, "OVERWRITE?");   // key 2 exists → no change
		map.putIfAbsent(10, "TEN");          // key 10 absent → inserted
		System.out.println("After putIfAbsent: " + map);

		// =============================================
		// 5. compute / computeIfAbsent / computeIfPresent
		// =============================================
		System.out.println("\n===== compute family =====");

		// Without computeIfAbsent:
		//   List<String> list = multiMap.get("fruits");
		//   if (list == null) {
		//       list = new ArrayList<>();
		//       multiMap.put("fruits", list);
		//   }
		//   list.add("Apple");
		Map<String, List<String>> multiMap = new HashMap<>();
		multiMap.computeIfAbsent("fruits", k -> new ArrayList<>()).add("Apple");
		multiMap.computeIfAbsent("fruits", k -> new ArrayList<>()).add("Banana");
		System.out.println("computeIfAbsent multiMap: " + multiMap);

		// Without computeIfPresent:
		//   String val = map.get(2);
		//   if (val != null) {
		//       map.put(2, val.toUpperCase() + "_UPDATED");
		//   }
		map.computeIfPresent(2, (k, v) -> v.toUpperCase() + "_UPDATED");
		System.out.println("computeIfPresent(2): " + map.get(2));

		// Without compute:
		//   for (String w : words) {
		//       Integer count = wordCount.get(w);
		//       if (count == null) { wordCount.put(w, 1); }
		//       else { wordCount.put(w, count + 1); }
		//   }
		Map<String, Integer> wordCount = new HashMap<>();
		String[] words = {"hello", "world", "hello", "java", "hello"};
		for (String w : words) {
			wordCount.compute(w, (k, v) -> v == null ? 1 : v + 1);
		}
		System.out.println("Word count via compute: " + wordCount);

		// =============================================
		// 6. merge — combine old and new values
		// =============================================
		// Without merge:
		//   Integer oldScore = scores.get("Alice");
		//   if (oldScore == null) { scores.put("Alice", 15); }
		//   else { scores.put("Alice", oldScore + 15); }
		System.out.println("\n===== merge =====");
		Map<String, Integer> scores = new HashMap<>();
		scores.put("Alice", 80);
		scores.put("Bob", 90);
		scores.merge("Alice", 15, Integer::sum);   // 80 + 15 = 95
		scores.merge("Charlie", 70, Integer::sum);  // absent → 70
		System.out.println("Scores after merge: " + scores);

		// =============================================
		// 7. replace / replace(key, oldVal, newVal)
		// =============================================
		// Without replace(key, value):
		//   if (map.containsKey(2)) { map.put(2, "REPLACED"); }
		//
		// Without replace(key, oldVal, newVal) — CAS-style:
		//   String cur = map.get(2);
		//   if (cur != null && cur.equals("REPLACED")) {
		//       map.put(2, "FINAL");
		//   }
		System.out.println("\n===== replace =====");
		map.replace(2, "REPLACED");
		System.out.println("replace(2): " + map.get(2));

		boolean replaced = map.replace(2, "REPLACED", "FINAL");  // CAS-style
		System.out.println("replace(2, old, new) success: " + replaced + " → " + map.get(2));

		boolean notReplaced = map.replace(2, "WRONG_OLD", "NOPE");
		System.out.println("replace(2, wrongOld, new) success: " + notReplaced);

		// =============================================
		// 8. replaceAll — bulk transform all values
		// =============================================
		// Without replaceAll:
		//   for (Map.Entry<String, Integer> e : prices.entrySet()) {
		//       e.setValue(e.getValue() + 10);
		//   }
		System.out.println("\n===== replaceAll =====");
		Map<String, Integer> prices = new HashMap<>();
		prices.put("Apple", 100);
		prices.put("Banana", 40);
		prices.put("Cherry", 200);
		prices.replaceAll((fruit, price) -> price + 10); // add 10 to all prices
		System.out.println("Prices after replaceAll: " + prices);

		// =============================================
		// 9. forEach (Java 8 BiConsumer)
		// =============================================
		// Without forEach:
		//   for (Map.Entry<Integer, String> e : map.entrySet()) {
		//       System.out.println("  " + e.getKey() + " → " + e.getValue());
		//   }
		System.out.println("\n===== forEach =====");
		map.forEach((key, value) -> System.out.println("  " + key + " → " + value));

		// =============================================
		// 10. Iteration patterns
		// =============================================
		System.out.println("\n===== Iteration Patterns =====");

		// entrySet iteration (most efficient for key+value)
		for (Entry<Integer, String> entry : map.entrySet()) {
			System.out.println("  entrySet: " + entry.getKey() + "=" + entry.getValue());
		}

		// keySet
		Set<Integer> keys = map.keySet();
		System.out.println("Keys: " + keys);

		// values
		Collection<String> values = map.values();
		System.out.println("Values: " + values);

		// Iterator with safe remove
		Iterator<Entry<Integer, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, String> entry = it.next();
			if (entry.getKey() != null && entry.getKey() > 50) {
				it.remove(); // safe removal during iteration
			}
		}
		System.out.println("After iterator remove (key > 50): " + map);

		// =============================================
		// 11. containsKey / containsValue / size / isEmpty
		// =============================================
		System.out.println("\n===== Query Methods =====");
		System.out.println("containsKey(2): " + map.containsKey(2));
		System.out.println("containsValue(\"FINAL\"): " + map.containsValue("FINAL"));
		System.out.println("size: " + map.size());
		System.out.println("isEmpty: " + map.isEmpty());

		// =============================================
		// 12. putAll — copy from another map
		// =============================================
		System.out.println("\n===== putAll =====");
		Map<Integer, String> extra = Map.of(100, "HUNDRED", 200, "TWO_HUNDRED");
		map.putAll(extra);
		System.out.println("After putAll: " + map);

		// =============================================
		// 13. Custom initial capacity to avoid resizing
		// =============================================
		System.out.println("\n===== Optimal Sizing =====");
		int expectedEntries = 1000;
		// capacity / 0.75 + 1 avoids any resize
		Map<Integer, String> preSized = new HashMap<>((int) (expectedEntries / 0.75) + 1);
		preSized.put(1, "test");
		System.out.println("Pre-sized map created for " + expectedEntries + " entries (no resize needed): " + preSized.size());

	}

}
