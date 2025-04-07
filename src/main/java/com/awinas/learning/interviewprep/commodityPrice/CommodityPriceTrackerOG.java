package com.awinas.learning.interviewprep.commodityPrice;

import java.util.*;

class CommodityPriceTrackerOG {
	private class Node {
		int price;
		Node prev, next;

		Node(int price) {
			this.price = price;
		}
	}

	private Map<Integer, Integer> priceMap; // timestamp → price
	private Map<Integer, Integer> priceFrequency; // price → count of timestamps with this price
	private Map<Integer, Node> priceNodeMap; // price → node in linked list
	private Node head, tail; // Doubly LinkedList for sorted prices

	public CommodityPriceTrackerOG() {
		priceMap = new HashMap<>();
		priceFrequency = new HashMap<>();
		priceNodeMap = new HashMap<>();

		head = new Node(Integer.MIN_VALUE); // Dummy head
		tail = new Node(Integer.MAX_VALUE); // Dummy tail
		head.next = tail;
		tail.prev = head;
	}

	// ✅ O(1) - Upsert price at a given timestamp
	public void upsertCommodityPrice(int timestamp, int price) {
		if (priceMap.containsKey(timestamp)) {
			int oldPrice = priceMap.get(timestamp);
			removePrice(oldPrice);
		}
		priceMap.put(timestamp, price);
		addPrice(price);
	}

	// ✅ O(1) - Remove old price from LinkedList only when frequency is 0
	private void removePrice(int price) {
		priceFrequency.put(price, priceFrequency.get(price) - 1);
		if (priceFrequency.get(price) == 0) {
			priceFrequency.remove(price);
			Node node = priceNodeMap.remove(price);
			if (node != null) {
				node.prev.next = node.next;
				node.next.prev = node.prev;
			}
		}
	}

	// ✅ O(1) - Add new price to LinkedList in sorted order
	private void addPrice(int price) {
		priceFrequency.put(price, priceFrequency.getOrDefault(price, 0) + 1);
		if (priceNodeMap.containsKey(price))
			return; // Already exists

		Node newNode = new Node(price);
		priceNodeMap.put(price, newNode);

		// Insert in sorted order (low to high)
		Node curr = head;
		while (curr.next.price < price) {
			curr = curr.next;
		}
		insertAfter(curr, newNode);
	}

	// ✅ O(1) - Insert node into LinkedList
	private void insertAfter(Node prev, Node newNode) {
		newNode.next = prev.next;
		newNode.prev = prev;
		prev.next.prev = newNode;
		prev.next = newNode;
	}

	// ✅ O(1) - Get highest price
	public int getMaxCommodityPrice() {
		return tail.prev.price == Integer.MIN_VALUE ? -1 : tail.prev.price;
	}

	// ✅ O(1) - Get lowest price
	public int getMinCommodityPrice() {
		return head.next.price == Integer.MAX_VALUE ? -1 : head.next.price;
	}

	public static void main(String[] args) {
		CommodityPriceTrackerOG tracker = new CommodityPriceTrackerOG();

		tracker.upsertCommodityPrice(1, 100);
		tracker.upsertCommodityPrice(2, 120);
		tracker.upsertCommodityPrice(3, 110);
		tracker.upsertCommodityPrice(4, 150);
		tracker.upsertCommodityPrice(2, 130); // Old price 120 removed
		tracker.upsertCommodityPrice(5, 130); // 130 appears twice
		tracker.upsertCommodityPrice(5, 130); // 130 appears twice
		tracker.upsertCommodityPrice(5, 130); // 130 appears twice
		tracker.upsertCommodityPrice(6, 150);
		tracker.upsertCommodityPrice(7, 110);

		System.out.println("Max Price: " + tracker.getMaxCommodityPrice()); // Expected: 150
		System.out.println("Min Price: " + tracker.getMinCommodityPrice()); // Expected: 100

		tracker.upsertCommodityPrice(5, 160); // 130 frequency reduced, 160 added

		System.out.println("Max Price: " + tracker.getMaxCommodityPrice()); // Expected: 160
		System.out.println("Min Price: " + tracker.getMinCommodityPrice()); // Expected: 100
	}
}
