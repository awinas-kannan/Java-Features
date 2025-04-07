package com.awinas.learning.interviewprep.ratelimiter;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimiterTest {

	public static void main(String[] args) {
		SlidingWindowCounterService rateLimiter = new SlidingWindowCounterService();
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		ConcurrentHashMap<String, AtomicInteger[]> responseMap = new ConcurrentHashMap<>();

		Random random = new Random();

		// Simulate 100 concurrent requests
		for (int i = 1; i <= 20000; i++) {

			if (i % 1000 == 0) {
				try {
					System.out.println("Sleeping for 6 second...");
//					responseMap.forEach((userId, counts) -> System.out
//							.println("User " + userId + " => Allowed: " + counts[0] + ", Denied: " + counts[1]));
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}

			executorService.submit(() -> {

				String userId = String.valueOf(random.nextInt(20) + 1); // Random userId from 1 to 20
				boolean allowed = rateLimiter.allowRequest(userId);
//				System.out.println("User: " + userId + " | Allowed: " + allowed);

				responseMap.putIfAbsent(userId, new AtomicInteger[] { new AtomicInteger(0), new AtomicInteger(0) });
				if (allowed) {
					responseMap.get(userId)[0].incrementAndGet(); // Increment true count
				} else {
					responseMap.get(userId)[1].incrementAndGet(); // Increment false count
				}

			});
		}

		// Shutdown executor after tasks finish
		executorService.shutdown();

		try {
			executorService.awaitTermination(2, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		responseMap.forEach((userId, counts) -> System.out
				.println("User " + userId + " => Allowed: " + counts[0] + ", Denied: " + counts[1]));

		System.out.println("userBuckets" + rateLimiter.userBuckets);
	}
}

//requestCounter.incrementAndGet();
