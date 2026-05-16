package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.phase2_juc_core;

import java.util.concurrent.*;

/**
 * ============================================================================
 * D - COMPLETABLE FUTURE Part 1: Async Pipelines & Exception Handling
 * ============================================================================
 *
 * WHY COMPLETABLEFUTURE?
 * ──────────────────────
 * Future.get() BLOCKS the calling thread — defeats the purpose of async!
 * CompletableFuture (Java 8) adds:
 *   - Non-blocking callbacks (thenApply, thenAccept, thenRun)
 *   - Exception handling (exceptionally, handle)
 *   - Chaining (pipeline of async steps)
 *   - Manual completion (complete(), completeExceptionally())
 *
 * CREATING A COMPLETABLEFUTURE:
 * ─────────────────────────────
 * supplyAsync(Supplier)  → runs async, returns a value
 * runAsync(Runnable)     → runs async, no return value
 * completedFuture(value) → already completed with a value
 *
 * CALLBACK CHAIN:
 * ───────────────
 * ┌──────────────┬──────────────────────────────────────────────────┐
 * │ Method       │ Input → Output                                   │
 * ├──────────────┼──────────────────────────────────────────────────┤
 * │ thenApply    │ T → U         (transform the result)            │
 * │ thenAccept   │ T → void      (consume the result)              │
 * │ thenRun      │ () → void     (run after, ignore result)        │
 * └──────────────┴──────────────────────────────────────────────────┘
 *
 * *Async variants (thenApplyAsync, etc.) run callback on a DIFFERENT thread.
 *
 * EXCEPTION HANDLING:
 * ───────────────────
 * exceptionally(ex → fallback)    → catch exception, return fallback value
 * handle((result, ex) → value)    → handles BOTH success and error
 * whenComplete((result, ex) → {}) → side-effect, doesn't change the result
 */
public class D_CompletableFuture1 {

    // ========================================================================
    // DEMO 1: supplyAsync + thenApply — async pipeline
    // ========================================================================
    static void demo1_SupplyAsyncThenApply() throws Exception {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 1: supplyAsync → thenApply → thenAccept");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * supplyAsync runs on ForkJoinPool.commonPool() by default.
         * Each thenApply/thenAccept adds a stage to the pipeline.
         * No blocking! The chain executes as each stage completes.
         */

        CompletableFuture<Void> pipeline = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("  [Step 1] Fetching data on " + Thread.currentThread().getName());
                    sleep(500);
                    return "Raw Data";
                })
                .thenApply(data -> {
                    System.out.println("  [Step 2] Processing: " + data + " on " + Thread.currentThread().getName());
                    return data.toUpperCase();
                })
                .thenApply(processed -> {
                    System.out.println("  [Step 3] Enriching: " + processed);
                    return processed + " [ENRICHED]";
                })
                .thenAccept(result -> {
                    System.out.println("  [Step 4] Final result: " + result);
                });

        pipeline.join(); // wait for completion (non-blocking in real apps)
        System.out.println();
    }

    // ========================================================================
    // DEMO 2: runAsync — no return value
    // ========================================================================
    static void demo2_RunAsync() throws Exception {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 2: runAsync — fire-and-forget async task");
        System.out.println("═══════════════════════════════════════════════════");

        CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {
            System.out.println("  [Async] Background cleanup on " + Thread.currentThread().getName());
            sleep(300);
            System.out.println("  [Async] Cleanup done");
        });

        System.out.println("  [Main] Continuing immediately...");
        task.join();
        System.out.println();
    }

    // ========================================================================
    // DEMO 3: thenApply vs thenApplyAsync — same thread vs different thread
    // ========================================================================
    static void demo3_AsyncVariants() throws Exception {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 3: thenApply vs thenApplyAsync");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * thenApply      — may run on the same thread or the completing thread
         * thenApplyAsync  — guaranteed to run on a different thread (from pool)
         * thenApplyAsync(fn, executor) — runs on YOUR custom executor
         */

        ExecutorService myPool = Executors.newFixedThreadPool(1, r -> {
            Thread t = new Thread(r, "MyCustomPool-Thread");
            t.setDaemon(true);
            return t;
        });

        CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("  [Supply] on " + Thread.currentThread().getName());
                    return "data";
                })
                .thenApply(s -> {
                    System.out.println("  [thenApply] on " + Thread.currentThread().getName());
                    return s;
                })
                .thenApplyAsync(s -> {
                    System.out.println("  [thenApplyAsync] on " + Thread.currentThread().getName());
                    return s;
                })
                .thenApplyAsync(s -> {
                    System.out.println("  [thenApplyAsync(myPool)] on " + Thread.currentThread().getName());
                    return s;
                }, myPool)
                .join();

        myPool.shutdown();
        System.out.println();
    }

    // ========================================================================
    // DEMO 4: exceptionally — catching errors in the pipeline
    // ========================================================================
    static void demo4_Exceptionally() throws Exception {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 4: exceptionally() — error recovery");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * If any stage throws an exception:
         *   - Downstream thenApply/thenAccept are SKIPPED
         *   - exceptionally() catches the error and provides a fallback value
         *   - Downstream stages after exceptionally() continue normally
         */

        String result = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("  [Step 1] Fetching from API...");
                    if (true) throw new RuntimeException("API is down!");
                    return "data";
                })
                .thenApply(data -> {
                    System.out.println("  [Step 2] This is SKIPPED because Step 1 failed");
                    return data.toUpperCase();
                })
                .exceptionally(ex -> {
                    System.out.println("  [Recovery] Caught: " + ex.getMessage());
                    return "FALLBACK DATA";
                })
                .thenApply(data -> {
                    System.out.println("  [Step 3] Continues with: " + data);
                    return data + " [PROCESSED]";
                })
                .join();

        System.out.println("  Final: " + result);
        System.out.println();
    }

    // ========================================================================
    // DEMO 5: handle — handles both success and error in one place
    // ========================================================================
    static void demo5_Handle() throws Exception {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 5: handle() — success + error in one method");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * handle((result, exception) -> newValue)
         *   - Called on BOTH success and failure
         *   - If success: result has the value, exception is null
         *   - If failure: result is null, exception has the error
         *   - Returns a new value (can transform or recover)
         */

        // Success case
        String successResult = CompletableFuture
                .supplyAsync(() -> "Hello World")
                .handle((result, ex) -> {
                    if (ex != null) return "Error: " + ex.getMessage();
                    return result.toUpperCase();
                })
                .join();
        System.out.println("  Success case: " + successResult);

        // Failure case
        String failResult = CompletableFuture
                .<String>supplyAsync(() -> { throw new RuntimeException("Boom!"); })
                .handle((result, ex) -> {
                    if (ex != null) return "Recovered from: " + ex.getCause().getMessage();
                    return result.toUpperCase();
                })
                .join();
        System.out.println("  Failure case: " + failResult);
        System.out.println();
    }

    // ========================================================================
    // DEMO 6: whenComplete — side-effect without changing result
    // ========================================================================
    static void demo6_WhenComplete() throws Exception {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 6: whenComplete() — side-effect (logging)");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * whenComplete((result, ex) -> { }) 
         *   - Called on both success and failure
         *   - Cannot change the result (unlike handle)
         *   - Use for: logging, metrics, cleanup
         */

        String result = CompletableFuture
                .supplyAsync(() -> "Task Result")
                .whenComplete((res, ex) -> {
                    if (ex != null) System.out.println("  [Log] Failed: " + ex.getMessage());
                    else System.out.println("  [Log] Completed with: " + res);
                })
                .join();

        System.out.println("  Result unchanged: " + result);
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
        System.out.println("║   PHASE 2D: COMPLETABLEFUTURE — Part 1           ║");
        System.out.println("╚═══════════════════════════════════════════════════╝\n");

        demo1_SupplyAsyncThenApply();
        demo2_RunAsync();
        demo3_AsyncVariants();
        demo4_Exceptionally();
        demo5_Handle();
        demo6_WhenComplete();

        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("1. supplyAsync — run async, return value");
        System.out.println("2. runAsync — run async, no return");
        System.out.println("3. thenApply — transform (T→U), thenAccept — consume (T→void), thenRun — fire (→void)");
        System.out.println("4. *Async variants run callback on a different thread");
        System.out.println("5. exceptionally — catch error, provide fallback");
        System.out.println("6. handle — handles both success and error");
        System.out.println("7. whenComplete — side-effect only (logging, metrics)");
        System.out.println("8. Next: Part 2 — compose, combine, allOf, anyOf");
    }
}
