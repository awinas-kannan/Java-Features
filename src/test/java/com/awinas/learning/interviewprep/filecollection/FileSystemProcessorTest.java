package com.awinas.learning.interviewprep.filecollection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class FileSystemProcessorTest {
	private FileSystemProcessor processor;

	@BeforeEach
	void setUp() {
		processor = new FileSystemProcessor();
	}

	@Test
	void testTotalProcessedFileSize() {
		processor.processFile("file1.txt", 100, new ArrayList<>());
		processor.processFile("file2.txt", 200, Arrays.asList("collection1"));
		processor.processFile("file3.txt", 200, Arrays.asList("collection1"));
		processor.processFile("file4.txt", 300, Arrays.asList("collection2"));
		processor.processFile("file5.txt", 100, new ArrayList<>());
		processor.processFile("file6.txt", 150, Arrays.asList("collection1", "collection2"));

		assertEquals(1050, processor.getTotalProcessedFileSize());
	}

	@Test
	void testTopKCollections() {
		processor.processFile("file1.txt", 100, Arrays.asList("collection1"));
		processor.processFile("file2.txt", 200, Arrays.asList("collection1"));
		processor.processFile("file3.txt", 200, Arrays.asList("collection2"));
		processor.processFile("file4.txt", 300, Arrays.asList("collection2"));
		processor.processFile("file5.txt", 100, new ArrayList<>());
		processor.processFile("file6.txt", 150, Arrays.asList("collection1", "collection2"));

		List<Map.Entry<String, CollectionData>> topCollections = processor.getTopKCollections(2);

		assertEquals(2, topCollections.size());
		assertEquals("collection2", topCollections.get(0).getKey());
		assertEquals("collection1", topCollections.get(1).getKey());
		assertEquals(650, topCollections.get(0).getValue().totalSize);
		assertEquals(450, topCollections.get(1).getValue().totalSize);
	}

	@Test
	void testDuplicateFilesInCollections() {
		processor.processFile("file1.txt", 100, Arrays.asList("collection1", "collection1"));
		processor.processFile("file2.txt", 200, Arrays.asList("collection1", "collection1"));
		processor.processFile("file3.txt", 200, Arrays.asList("collection1", "collection2"));
		processor.processFile("file4.txt", 300, Arrays.asList("collection2", "collection2"));

		assertEquals(800, processor.getTotalProcessedFileSize());

		List<Map.Entry<String, CollectionData>> topCollections = processor.getTopKCollections(2);
		assertEquals(500, topCollections.get(0).getValue().totalSize);
		assertEquals(300, topCollections.get(1).getValue().totalSize);
	}

	@Test
	void testEmptyCollections() {
		assertEquals(0, processor.getTotalProcessedFileSize());
		assertTrue(processor.getTopKCollections(2).isEmpty());
	}

	@Test
	void testSingleFileMultipleCollections() {
		processor.processFile("file1.txt", 100, Arrays.asList("collection1", "collection2"));
		assertEquals(100, processor.getTotalProcessedFileSize());

		List<Map.Entry<String, CollectionData>> topCollections = processor.getTopKCollections(2);
		assertEquals(100, topCollections.get(0).getValue().totalSize);
		assertEquals(100, topCollections.get(1).getValue().totalSize);
	}
}
