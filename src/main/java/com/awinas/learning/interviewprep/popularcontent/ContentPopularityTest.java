package com.awinas.learning.interviewprep.popularcontent;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContentPopularityTest {
	@Test
	void testPopularityTracking() {
		ContentPopularity tracker = new ContentPopularity();

		// Initial state
		assertEquals(-1, tracker.mostPopular());

		// Increase popularity
		tracker.increasePopularity(1);
		tracker.increasePopularity(2);
		tracker.increasePopularity(1);
		tracker.increasePopularity(3);
		tracker.increasePopularity(3);
		tracker.increasePopularity(3);

		// Most popular should be 3
		assertEquals(3, tracker.mostPopular());

		// Decrease popularity of 3
		tracker.decreasePopularity(3);
		assertEquals(1, tracker.mostPopular());

		// Decrease popularity of 1
		tracker.decreasePopularity(1);
		tracker.decreasePopularity(1);
		assertEquals(3, tracker.mostPopular());

		// Decrease all popularity
		tracker.decreasePopularity(3);
		tracker.decreasePopularity(2);
		assertEquals(-1, tracker.mostPopular());
	}
}
