package com.awinas.learning.interviewprep.popularcontent;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;

/*]
 * 
Time Complexity Analysis
Operation	Complexity	Explanation
increasePopularity()	O(log N)	HashMap update (O(1)) + TreeMap update (O(log N))
decreasePopularity()	O(log N)	HashMap update (O(1)) + TreeMap update (O(log N))
mostPopular()	O(1)	Fetch last entry from TreeMap

https://leetcode.com/discuss/interview-question/5890716/Atlassian-Interview-Experience-%3A-Content-Popularity-Tracker/

 */
public class ContentPopularity {
	private final Map<Integer, Integer> popularityMap; // contentId -> popularity
	private final TreeMap<Integer, LinkedHashSet<Integer>> sortedPopularity; // popularity -> Set of contentIds

	public ContentPopularity() {
		popularityMap = new HashMap<>();
		sortedPopularity = new TreeMap<>();
	}

	public void increasePopularity(int contentId) {
		updatePopularity(contentId, 1);
	}

	public void decreasePopularity(int contentId) {
		updatePopularity(contentId, -1);
	}

	public int mostPopular() {
		if (sortedPopularity.isEmpty())
			return -1;
		return sortedPopularity.lastEntry().getValue().iterator().next(); // Get most popular content ID
	}

	private void updatePopularity(int contentId, int change) {
		int oldPopularity = popularityMap.getOrDefault(contentId, 0);
		int newPopularity = oldPopularity + change;

		// Remove from old popularity group
		if (oldPopularity > 0) {
			removeContentFromTree(oldPopularity, contentId);
		}

		if (newPopularity > 0) {
			popularityMap.put(contentId, newPopularity);
			sortedPopularity.computeIfAbsent(newPopularity, k -> new LinkedHashSet<>()).add(contentId);
		} else {
			popularityMap.remove(contentId);
		}
	}

	private void removeContentFromTree(int popularity, int contentId) {
		LinkedHashSet<Integer> contentSet = sortedPopularity.get(popularity);
		if (contentSet != null) {
			contentSet.remove(contentId);
			if (contentSet.isEmpty()) {
				sortedPopularity.remove(popularity);
			}
		}
	}
}
