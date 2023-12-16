package com.awinas.learning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonCharCountNString {

	public static void main(String[] args) {

		String[] arr = { "aabcc", "adcaa", "acdba" };

		List<Map<Character, Integer>> lists = new ArrayList<>();

		for (String s : arr) {
			Map<Character, Integer> mapsCount = new HashMap<>();
			for (int i = 0; i < s.length(); i++) {
				Character c = s.charAt(i);
				if (mapsCount.containsKey(c)) {
					mapsCount.put(c, mapsCount.get(c) + 1);
				} else {
					mapsCount.put(c, 1);
				}

			}
			lists.add(mapsCount);
		}
		System.out.println(lists);
		int commonChar = 0;
		for (Map.Entry<Character, Integer> entry : lists.get(0).entrySet()) {
			int least = entry.getValue();
			for (int i = 1; i < lists.size(); i++) {
				Map<Character, Integer> tempMap = lists.get(i);
				if (tempMap.containsKey(entry.getKey())) {
					int val = tempMap.get(entry.getKey());
					least = val <= least ? val : least;
				} else {
					least = 0;
					break;
				}
			}
			System.out.println("HERE");
			commonChar = commonChar + least;

		}

		System.out.println(commonChar);
	}

}
