package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.phase2_juc_core;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * E - COMPLETABLE FUTURE Part 2: Combining, Composing & Parallel Execution
 * ============================================================================
 *
 * COMPOSING (chaining dependent futures):
 * ───────────────────────────────────────
 * thenCompose — "flatMap" — output of one feeds into the next
 *   Future<A> → (A → Future<B>) → Future<B>
 *
 * COMBINING (merging independent futures):
 * ────────────────────────────────────────
 * thenCombine — two independent futures, combine their results
 *   Future<A> + Future<B> → Future<C>
 *
 * PARALLEL EXECUTION:
 * ───────────────────
 * allOf — wait for ALL futures to complete
 * anyOf — wait for the FIRST future to complete
 *
 * thenCompose vs thenApply:
 * ─────────────────────────
 * thenApply(fn)   → fn returns a VALUE   → result is Future<Value>
 * thenCompose(fn) → fn returns a FUTURE  → result is Future<Value> (unwrapped)
 *
 * Without thenCompose:
 *   supplyAsync().thenApply(x -> fetchAsync(x)) → Future<Future<String>> (nested!)
 * With thenCompose:
 *   supplyAsync().thenCompose(x -> fetchAsync(x)) → Future<String> (flat!)
 */
public class E_CompletableFuture2 {

    // ========================================================================
    // DEMO 1: thenCompose — chaining dependent async calls
    // ========================================================================
    static void demo1_ThenCompose() throws Exception {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 1: thenCompose — sequential async pipeline");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * Scenario: getUserId() → getUser(id) → getOrders(user)
         * Each step depends on the previous result.
         * Each step is async.
         */

        CompletableFuture<String> pipeline = getUserId()
                .thenCompose(id -> getUser(id))
                .thenCompose(user -> getOrders(user));

        String result = pipeline.join();
        System.out.println("  Final: " + result);
        System.out.println();
    }

    static CompletableFuture<Integer> getUserId() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("  [Step 1] Fetching user ID...");
            sleep(300);
            return 42;
        });
    }

    static CompletableFuture<String> getUser(int id) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("  [Step 2] Fetching user for ID=" + id + "...");
            sleep(300);
            return "Awinas";
        });
    }

    static CompletableFuture<String> getOrders(String user) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("  [Step 3] Fetching orders for " + user + "...");
            sleep(300);
            return "Orders for " + user + ": [Laptop, Phone]";
        });
    }

    // ========================================================================
    // DEMO 2: thenCombine — merging two independent futures
    // ========================================================================
    static void demo2_ThenCombine() throws Exception {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 2: thenCombine — merge two independent results");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * Scenario: Fetch price from two sources in parallel,
         * then combine the results.
         */

        long start = System.currentTimeMillis();

        CompletableFuture<Double> priceFromA = CompletableFuture.supplyAsync(() -> {
            System.out.println("  [Source A] Fetching price...");
            sleep(1000);
            return 100.0;
        });

        CompletableFuture<Double> priceFromB = CompletableFuture.supplyAsync(() -> {
            System.out.println("  [Source B] Fetching price...");
            sleep(800);
            return 105.0;
        });

        // thenCombine runs when BOTH are done
        Double avgPrice = priceFromA
                .thenCombine(priceFromB, (a, b) -> {
                    System.out.println("  [Combine] A=" + a + ", B=" + b);
                    return (a + b) / 2;
                })
                .join();

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("  Average price: " + avgPrice + " (took " + elapsed + "ms)");
        System.out.println("  → Both ran in parallel, total time = slowest (1s), not sum (1.8s)\n");
    }

    // ========================================================================
    // DEMO 3: allOf — wait for ALL futures
    // ========================================================================
    static void demo3_AllOf() throws Exception {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 3: allOf — wait for all to complete");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * allOf(cf1, cf2, cf3...) → CompletableFuture<Void>
         *   - Completes when ALL futures complete
         *   - Returns Void — you must get individual results yourself
         *   - If any fails, the allOf fails
         */

        long start = System.currentTimeMillis();

        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            sleep(1000); return "Result-1";
        });
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            sleep(500); return "Result-2";
        });
        CompletableFuture<String> task3 = CompletableFuture.supplyAsync(() -> {
            sleep(1500); return "Result-3";
        });

        CompletableFuture<Void> allDone = CompletableFuture.allOf(task1, task2, task3);
        allDone.join(); // wait for all

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("  All done in " + elapsed + "ms");
        System.out.println("  Results: " + task1.join() + ", " + task2.join() + ", " + task3.join());

        // Common pattern: collect all results into a list
        List<CompletableFuture<String>> futures = List.of(task1, task2, task3);
        List<String> results = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        System.out.println("  Collected: " + results);
        System.out.println();
    }

    // ========================================================================
    // DEMO 4: anyOf — first to complete wins
    // ========================================================================
    static void demo4_AnyOf() throws Exception {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 4: anyOf — first to complete wins");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * anyOf(cf1, cf2, cf3...) → CompletableFuture<Object>
         *   - Completes when ANY one future completes
         *   - Returns the result of the fastest one
         *   - Others continue running (not auto-cancelled like invokeAny)
         */

        long start = System.currentTimeMillis();

        CompletableFuture<String> serverA = CompletableFuture.supplyAsync(() -> {
            sleep(2000); return "Response from Server-A (2s)";
        });
        CompletableFuture<String> serverB = CompletableFuture.supplyAsync(() -> {
            sleep(500); return "Response from Server-B (0.5s)";
        });
        CompletableFuture<String> serverC = CompletableFuture.supplyAsync(() -> {
            sleep(1000); return "Response from Server-C (1s)";
        });

        Object fastest = CompletableFuture.anyOf(serverA, serverB, serverC).join();

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("  Fastest: " + fastest + " in " + elapsed + "ms");
        System.out.println("  → Note: other tasks are NOT cancelled (unlike invokeAny)\n");
    }

    // ========================================================================
    // DEMO 5: thenCompose vs thenApply — the difference
    // ========================================================================
    static void demo5_ComposeVsApply() {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 5: thenCompose vs thenApply");
        System.out.println("═══════════════════════════════════════════════════");

        // thenApply: function returns a VALUE
        CompletableFuture<String> withApply = CompletableFuture
                .supplyAsync(() -> "hello")
                .thenApply(s -> s.toUpperCase()); // String → String

        System.out.println("  thenApply result: " + withApply.join());

        // thenApply with async function: NESTING problem!
        CompletableFuture<CompletableFuture<String>> nested = CompletableFuture
                .supplyAsync(() -> 42)
                .thenApply(id -> getUser(id)); // returns Future → Future<Future<String>>!

        System.out.println("  thenApply (nested): " + nested.join().join()); // double .join()!

        // thenCompose: function returns a FUTURE → unwraps automatically
        CompletableFuture<String> flat = CompletableFuture
                .supplyAsync(() -> 42)
                .thenCompose(id -> getUser(id)); // returns Future → Future<String> (flat!)

        System.out.println("  thenCompose (flat): " + flat.join()); // single .join()
        System.out.println("  → Use thenCompose when the function itself returns a CompletableFuture\n");
    }

    // ========================================================================
    // DEMO 6: Real-world — parallel API calls with timeout
    // ========================================================================
    static void demo6_RealWorldPattern() throws Exception {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 6: Real-world — parallel API calls + timeout");
        System.out.println("═══════════════════════════════════════════════════");

        ExecutorService pool = Executors.newFixedThreadPool(3);

        long start = System.currentTimeMillis();

        CompletableFuture<String> userFuture = CompletableFuture.supplyAsync(() -> {
            sleep(300); return "User: Awinas";
        }, pool);

        CompletableFuture<String> ordersFuture = CompletableFuture.supplyAsync(() -> {
            sleep(500); return "Orders: [Laptop]";
        }, pool);

        CompletableFuture<String> reviewsFuture = CompletableFuture.supplyAsync(() -> {
            sleep(400); return "Reviews: 4.5 stars";
        }, pool);

        // Wait for all, combine into one response
        String response = CompletableFuture.allOf(userFuture, ordersFuture, reviewsFuture)
                .thenApply(v ->
                    userFuture.join() + " | " + ordersFuture.join() + " | " + reviewsFuture.join()
                )
                .orTimeout(2, TimeUnit.SECONDS) // Java 9+ — fails if takes > 2s
                .join();

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("  Response: " + response);
        System.out.println("  Time: " + elapsed + "ms (all 3 calls ran in parallel)");

        pool.shutdown();
        System.out.println();
    }

    // ========================================================================
    // Helper
    // ========================================================================
    private static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    // ========================================================================
    // MAIN
    // ========================================================================
    public static void main(String[] args) throws Exception {
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║   PHASE 2E: COMPLETABLEFUTURE — Part 2           ║");
        System.out.println("╚═══════════════════════════════════════════════════╝\n");

        demo1_ThenCompose();
        demo2_ThenCombine();
        demo3_AllOf();
        demo4_AnyOf();
        demo5_ComposeVsApply();
        demo6_RealWorldPattern();

        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("1. thenCompose — chain dependent async calls (flatMap)");
        System.out.println("2. thenCombine — merge two independent results");
        System.out.println("3. allOf — wait for ALL futures (returns Void)");
        System.out.println("4. anyOf — first to complete wins (returns Object)");
        System.out.println("5. thenApply = fn returns value, thenCompose = fn returns Future");
        System.out.println("6. orTimeout (Java 9+) — fail if takes too long");
        System.out.println("7. Use custom ExecutorService for production workloads");
    }
}
