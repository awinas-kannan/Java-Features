package com.awinas.learning.interviewprep.ratelimiter;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class SlidingWindowCounterService {

	private static final int WINDOW_SIZE = 60; // Time window in seconds (1 minute)
	private static final int BUCKET_COUNT = 10; // Number of buckets
	private static final int REQUEST_LIMIT = 500; // Max requests per window

	// Stores user-specific buckets (userId → (bucketId → count))
	public final ConcurrentHashMap<String, ConcurrentHashMap<Long, AtomicInteger>> userBuckets = new ConcurrentHashMap<>();

	public synchronized boolean allowRequest(String userId) {

		long currentTime = Instant.now().getEpochSecond();
		long bucketId = currentTime / (WINDOW_SIZE / BUCKET_COUNT); // Determine the current bucket

		// Get or create user-specific bucket map
		userBuckets.putIfAbsent(userId, new ConcurrentHashMap<>());
		ConcurrentHashMap<Long, AtomicInteger> buckets = userBuckets.get(userId);

		// Increment request count in the current bucket
		buckets.putIfAbsent(bucketId, new AtomicInteger(0));
		buckets.get(bucketId).incrementAndGet();

		// Calculate total requests within the last 60 seconds
		int totalRequests = buckets.entrySet().stream().filter(entry -> entry.getKey() >= bucketId - BUCKET_COUNT)
				.mapToInt(entry -> entry.getValue().get()).sum();

		// Remove expired buckets
		buckets.keySet().removeIf(key -> key < bucketId - BUCKET_COUNT);

		return totalRequests <= REQUEST_LIMIT;
	}

}

// Calculate total requests within the window
//int totalRequests = buckets.entrySet().stream()
//      .filter(entry -> entry.getKey() >= (currentTime - WINDOW_SIZE) / (WINDOW_SIZE / BUCKET_COUNT))
//      .mapToInt(entry -> entry.getValue().get())
//      .sum();

//buckets.keySet().removeIf(key -> key < (currentTime - WINDOW_SIZE) / (WINDOW_SIZE / BUCKET_COUNT));
