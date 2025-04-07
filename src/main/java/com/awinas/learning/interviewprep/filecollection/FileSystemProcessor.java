package com.awinas.learning.interviewprep.filecollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/*
 * https://leetcode.com/discuss/interview-experience/1504593/Atlassian-or-SDE-2-or-P4-or-September-2021-or-Bangalore-or-Offer
 */
class FileSystemProcessor {
	private int totalFileSize = 0;
	private Set<String> processedFiles = new HashSet<>();
	private Map<String, CollectionData> collectionMap = new HashMap<>();

	public void processFile(String fileName, int fileSize, List<String> collections) {
		if (!processedFiles.contains(fileName)) {
			totalFileSize += fileSize;
			processedFiles.add(fileName);
		}

		for (String collection : collections) {
			collectionMap.putIfAbsent(collection, new CollectionData());
			CollectionData collectionData = collectionMap.get(collection);

			if (!collectionData.files.contains(fileName)) {
				collectionData.totalSize += fileSize;
				collectionData.files.add(fileName);
			}
		}
	}

	public int getTotalProcessedFileSize() {
		return totalFileSize;
	}

	public List<Map.Entry<String, CollectionData>> getTopKCollections(int k) {
		// Max-Heap (sorting in descending order)
		PriorityQueue<Map.Entry<String, CollectionData>> maxHeap = new PriorityQueue<>(
				(a, b) -> Integer.compare(b.getValue().totalSize, a.getValue().totalSize));

		// Populate max heap
		maxHeap.addAll(collectionMap.entrySet());

		// Extract top K elements in O(K log M)
		List<Map.Entry<String, CollectionData>> result = new ArrayList<>();
		for (int i = 0; i < k && !maxHeap.isEmpty(); i++) {
			result.add(maxHeap.poll());
		}
		return result;
	}

	public void printCollections() {
		for (Map.Entry<String, CollectionData> entry : collectionMap.entrySet()) {
			System.out.println("Collection: " + entry.getKey() + ", Size: " + entry.getValue().totalSize + ", Files: "
					+ entry.getValue().files);
		}
	}

	public static void main(String[] args) {
		FileSystemProcessor processor = new FileSystemProcessor();

		processor.processFile("file1.txt", 100, new ArrayList<>());
		processor.processFile("file2.txt", 200, Arrays.asList("collection1"));
		processor.processFile("file3.txt", 200, Arrays.asList("collection1"));
		processor.processFile("file4.txt", 300, Arrays.asList("collection2"));
		processor.processFile("file5.txt", 100, new ArrayList<>());
		processor.processFile("file6.txt", 150, Arrays.asList("collection1", "collection2"));
		processor.processFile("file7.txt", 150, Arrays.asList("collection1", "collection3"));
		processor.processFile("file8.txt", 150, Arrays.asList("collection4", "collection3"));

		System.out.println("Total Processed File Size: " + processor.getTotalProcessedFileSize());

		processor.printCollections();

		System.out.println("\nTop K Collections:");
		List<Map.Entry<String, CollectionData>> topCollections = processor.getTopKCollections(2);
		for (Map.Entry<String, CollectionData> entry : topCollections) {
			System.out.println(
					entry.getKey() + " - " + entry.getValue().totalSize + ", Files: " + entry.getValue().files);
		}
	}
}