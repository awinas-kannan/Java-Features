package com.awinas.learning.interviewprep.popularcontent;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/*
 * 
Optimized O(1) Solution Using Doubly Linked List + HashMap
Key Data Structures
Map<Integer, Integer> (popularityMap)

Stores content ID → its popularity.
O(1) lookup.
Map<Integer, Node> (nodeMap)

Stores content ID → Node reference (for fast access in DLL).
O(1) lookup.
Doubly Linked List (FrequencyNode class)

Each node represents a frequency (popularity) and holds a set of content IDs.
Maintains most popular content at the tail for O(1) retrieval.
Insertion & deletion in O(1) time.


 */
class ContentPopularityV2 {
	private static class FrequencyNode {
		int frequency;
		Set<Integer> contentIds;
		FrequencyNode prev, next;

		FrequencyNode(int frequency) {
			this.frequency = frequency;
			this.contentIds = new LinkedHashSet<>();
		}
	}

	private final Map<Integer, Integer> popularityMap; // contentId -> popularity
	private final Map<Integer, FrequencyNode> nodeMap; // contentId -> corresponding frequency node
	private FrequencyNode head, tail; // Head (least popular), Tail (most popular)

	public ContentPopularityV2() {
		popularityMap = new HashMap<>();
		nodeMap = new HashMap<>();
		head = new FrequencyNode(0);
		tail = new FrequencyNode(Integer.MAX_VALUE);
		head.next = tail;
		tail.prev = head;
	}

	public void increasePopularity(int contentId) {
		if (contentId < 0)
			return; // Ignore invalid content IDs

		int oldPopularity = popularityMap.getOrDefault(contentId, 0);
		int newPopularity = oldPopularity + 1;
		popularityMap.put(contentId, newPopularity);

		FrequencyNode currentFreqNode = nodeMap.get(contentId);
		FrequencyNode newFreqNode;

		// Find or create new frequency node
		if (currentFreqNode == null) {
			// add to 1 or create new 1
			newFreqNode = head.next.frequency == newPopularity ? head.next : new FrequencyNode(newPopularity);
			if (newFreqNode != head.next) {
				newFreqNode.next = head.next;
				newFreqNode.prev = head;
				head.next.prev = newFreqNode;
				head.next = newFreqNode;
			}
		} else {
			// Freq could be 1 or More
			currentFreqNode.contentIds.remove(contentId);

			// If the Next Few matched the new popularity.. its not at last (tail)
			// So New few is next node
			if (currentFreqNode.next.frequency == newPopularity) {
				newFreqNode = currentFreqNode.next;
			} else {

				// Create a new node add it after the current node
				newFreqNode = new FrequencyNode(newPopularity);
				newFreqNode.next = currentFreqNode.next;
				newFreqNode.prev = currentFreqNode;
				currentFreqNode.next.prev = newFreqNode;
				currentFreqNode.next = newFreqNode;
			}
			if (currentFreqNode.contentIds.isEmpty()) {
				currentFreqNode.prev.next = currentFreqNode.next;
				currentFreqNode.next.prev = currentFreqNode.prev;
			}
		}

		newFreqNode.contentIds.add(contentId);
		nodeMap.put(contentId, newFreqNode);
	}

	public void decreasePopularity(int contentId) {
		if (!popularityMap.containsKey(contentId))
			return; // Ignore if content doesn't exist

		int oldPopularity = popularityMap.get(contentId);
		int newPopularity = oldPopularity - 1;

		FrequencyNode currentFreqNode = nodeMap.get(contentId);
		if (currentFreqNode == null)
			return;

		currentFreqNode.contentIds.remove(contentId);
		FrequencyNode newFreqNode = null;

		if (newPopularity == 0) {
			popularityMap.remove(contentId);
			nodeMap.remove(contentId);
		} else {
			popularityMap.put(contentId, newPopularity);

			// Find or create new frequency node
			if (currentFreqNode.prev.frequency == newPopularity) {
				newFreqNode = currentFreqNode.prev;
			} else {
				newFreqNode = new FrequencyNode(newPopularity);
				newFreqNode.next = currentFreqNode;
				newFreqNode.prev = currentFreqNode.prev;
				currentFreqNode.prev.next = newFreqNode;
				currentFreqNode.prev = newFreqNode;
			}
			newFreqNode.contentIds.add(contentId);
			nodeMap.put(contentId, newFreqNode);
		}

		// Remove empty frequency node
		if (currentFreqNode.contentIds.isEmpty()) {
			currentFreqNode.prev.next = currentFreqNode.next;
			currentFreqNode.next.prev = currentFreqNode.prev;
		}
	}

	public int mostPopular() {
		return tail.prev == head ? -1 : tail.prev.contentIds.iterator().next();
	}
}
