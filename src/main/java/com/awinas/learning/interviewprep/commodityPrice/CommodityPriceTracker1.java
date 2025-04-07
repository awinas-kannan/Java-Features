package com.awinas.learning.interviewprep.commodityPrice;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

class CommodityPriceTracker1 {
	private TreeMap<Integer, TreeSet<Integer>> priceMap; // Timestamp → Sorted Prices (Descending)
	private TreeMap<Integer, Integer> checkpointMax; // Checkpoint → Max Price till then

	public CommodityPriceTracker1() {
		this.priceMap = new TreeMap<>();
		this.checkpointMax = new TreeMap<>();
	}

	// ✅ Add a price entry at a timestamp
	public void addPrice(int timestamp, int price) {
		priceMap.putIfAbsent(timestamp, new TreeSet<>(Collections.reverseOrder())); // Ensure timestamp exists
		priceMap.get(timestamp).add(price); // Add price to descending sorted set
	}

	// ✅ Get the highest price at a specific timestamp
	public int getHighestPriceAtTimestamp(int timestamp) {
		if (!priceMap.containsKey(timestamp) || priceMap.get(timestamp).isEmpty())
			return -1;
		return priceMap.get(timestamp).first(); // First element is the max price
	}

	// ✅ Create a checkpoint → store max price till now
	public void createCheckpoint(int timestamp) {
		int maxPrice = 0;
		for (Map.Entry<Integer, TreeSet<Integer>> entry : priceMap.entrySet()) {
			if (entry.getKey() > timestamp)
				break; // Stop if we exceed timestamp
			maxPrice = Math.max(maxPrice, entry.getValue().first());
		}
		checkpointMax.put(timestamp, maxPrice);
	}

	// ✅ Get max commodity price till a given checkpoint timestamp
	public int getMaxPriceUntilCheckpoint(int checkpointTimestamp) {
		int maxPrice = -1;
		for (Map.Entry<Integer, Integer> entry : checkpointMax.entrySet()) {
			if (entry.getKey() > checkpointTimestamp)
				break; // Stop if we exceed timestamp
			maxPrice = entry.getValue();
		}
		return maxPrice;
	}

	// ✅ Print stored data
	public void printData() {
		System.out.println("Price Map:");
		for (var entry : priceMap.entrySet()) {
			System.out.println("Timestamp " + entry.getKey() + " → " + entry.getValue());
		}
		System.out.println("Checkpoint Max:");
		for (var entry : checkpointMax.entrySet()) {
			System.out.println("Checkpoint " + entry.getKey() + " → " + entry.getValue());
		}
	}

	public static void main(String[] args) {
		CommodityPriceTracker tracker = new CommodityPriceTracker();

		// ✅ Adding commodity prices
		tracker.addPrice(1, 100);
		tracker.addPrice(2, 120);
		tracker.addPrice(2, 110);
		tracker.addPrice(3, 130);
		tracker.addPrice(4, 90);
		tracker.addPrice(5, 150);

		// ✅ Creating checkpoints
		tracker.createCheckpoint(3);
		tracker.createCheckpoint(5);

		// ✅ Print stored data
		tracker.printData();

		// ✅ Queries
		System.out.println("\nHighest price at T=2: " + tracker.getHighestPriceAtTimestamp(2)); // Expected: 120
		System.out.println("Highest price at T=4: " + tracker.getHighestPriceAtTimestamp(4)); // Expected: 90
		System.out.println("Max price till checkpoint 3: " + tracker.getMaxPriceUntilCheckpoint(3)); // Expected: 130
		System.out.println("Max price till checkpoint 5: " + tracker.getMaxPriceUntilCheckpoint(5)); // Expected: 150
	}
}
