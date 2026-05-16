package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.phase2_juc_core;

import java.util.*;
import java.util.concurrent.*;

/**
 * ============================================================================
 * C - CALLABLE & FUTURE: Getting Results from Threads
 * ============================================================================
 *
 * THE PROBLEM WITH RUNNABLE:
 * ──────────────────────────
 * Runnable.run() returns void — no way to get a result or throw checked exceptions.
 *
 * CALLABLE<V> — the solution:
 * ───────────────────────────
 * ┌───────────────┬────────────────────────────────────┐
 * │ Runnable      │ Callable<V>                        │
 * ├───────────────┼────────────────────────────────────┤
 * │ void run()    │ V call() throws Exception          │
 * │ No return     │ Returns a value of type V          │
 * │ No exceptions │ Can throw checked exceptions       │
 * │ execute()     │ submit() → returns Future<V>       │
 * └───────────────┴────────────────────────────────────┘
 *
 * FUTURE<V> — a promise for the result:
 * ──────────────────────────────────────
 * When you submit a Callable, you get a Future — a handle to the eventual result.
 *
 * ┌───────────────────┬───────────────────────────────────────────────┐
 * │ Method            │ Behavior                                      │
 * ├───────────────────┼───────────────────────────────────────────────┤
 * │ get()             │ Blocks until result is ready, then returns it │
 * │ get(timeout, unit)│ Blocks up to timeout, throws TimeoutException │
 * │ isDone()          │ true if task completed (success, error, cancel│
 * │ isCancelled()     │ true if task was cancelled                    │
 * │ cancel(mayInterrupt)│ Attempts to cancel the task                 │
 * └───────────────────┴───────────────────────────────────────────────┘
 *
 * SUBMIT METHODS:
 * ───────────────
 * execute(Runnable)     → fire-and-forget, no result, no Future
 * submit(Callable)      → returns Future<V>
 * submit(Runnable)      → returns Future<?> (result is null)
 * invokeAll(Collection) → submits ALL, blocks until ALL complete, returns List<Future>
 * invokeAny(Collection) → submits ALL, returns result of the FIRST one that completes
 */
public class C_CallableFuture {

    // ========================================================================
    // DEMO 1: Callable + Future — basic usage
    // ========================================================================
    static void demo1_BasicCallableFuture() throws Exception {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 1: Callable + Future — returning a value");
        System.out.println("═══════════════════════════════════════════════════");

        ExecutorService pool = Executors.newFixedThreadPool(2);

        Callable<Integer> sumTask = () -> {
            System.out.println("  [Worker] Calculating sum...");
            Thread.sleep(5000);
            int sum = 0;
            for (int i = 1; i <= 100; i++) sum += i;
            return sum;
        };

        Future<Integer> future = pool.submit(sumTask);

        System.out.println("  [Main] Task submitted. isDone? " + future.isDone());
        System.out.println("  [Main] Doing other work while waiting...");

        // get() blocks until result is available
        Integer result = future.get();
        System.out.println("  [Main] Result: " + result + ". isDone? " + future.isDone());

        pool.shutdown();
        System.out.println();
    }

    // ========================================================================
    // DEMO 2: Future.get() with timeout
    // ========================================================================
    static void demo2_FutureTimeout() throws Exception {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 2: Future.get(timeout) — don't wait forever");
        System.out.println("═══════════════════════════════════════════════════");

        ExecutorService pool = Executors.newSingleThreadExecutor();

        Future<String> future = pool.submit(() -> {
            Thread.sleep(5000); // takes 5 seconds
            return "Done!";
        });

        try {
            String result = future.get(2, TimeUnit.SECONDS); // only wait 2 seconds
            System.out.println("  Result: " + result);
        } catch (TimeoutException e) {
            System.out.println("  TimeoutException! Task took too long.");
            System.out.println("  Cancelling task...");
            future.cancel(true); // interrupt the running task
            System.out.println("  Cancelled? " + future.isCancelled());
        }

        pool.shutdown();
        System.out.println();
    }

    // ========================================================================
    // DEMO 3: Future.cancel() — cancelling a task
    // ========================================================================
    static void demo3_CancelTask() throws Exception {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 3: Cancelling a Future");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * cancel(false) — don't interrupt if running, just prevent it from starting
         * cancel(true)  — interrupt if running (thread gets InterruptedException)
         *
         * After cancel:
         *   isDone() = true, isCancelled() = true
         *   get() throws CancellationException
         */

        ExecutorService pool = Executors.newSingleThreadExecutor();

        Future<String> future = pool.submit(() -> {
            for (int i = 1; i <= 10; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("  [Worker] Interrupted! Cleaning up...");
                    return "Cancelled";
                }
                System.out.println("  [Worker] Step " + i);
                Thread.sleep(300);
            }
            return "Completed";
        });

        Thread.sleep(1000);
        System.out.println("  [Main] Cancelling task...");
        future.cancel(true); // interrupt the running thread

        System.out.println("  isDone: " + future.isDone());
        System.out.println("  isCancelled: " + future.isCancelled());

        try {
            future.get();
        } catch (CancellationException e) {
            System.out.println("  get() threw CancellationException (expected)");
        }

        pool.shutdown();
        pool.awaitTermination(2, TimeUnit.SECONDS);
        System.out.println();
    }

    // ========================================================================
    // DEMO 4: invokeAll — submit all, wait for all
    // ========================================================================
    static void demo4_InvokeAll() throws Exception {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 4: invokeAll() — submit all, wait for all");
        System.out.println("═══════════════════════════════════════════════════");

        ExecutorService pool = Executors.newFixedThreadPool(3);

        List<Callable<String>> tasks = List.of(
                () -> { Thread.sleep(1000); return "Task-A done (1s)"; },
                () -> { Thread.sleep(2000);  return "Task-B done (2.0s)"; },
                () -> { Thread.sleep(4500); return "Task-C done (4.5s)"; }
        );

        long start = System.currentTimeMillis();

        // invokeAll blocks until ALL tasks complete
        List<Future<String>> futures = pool.invokeAll(tasks);

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("  All tasks completed in " + elapsed + "ms");

        for (Future<String> f : futures) {
            System.out.println("  Result: " + f.get());
        }

        pool.shutdown();
        System.out.println("  → Total time ~4.5s (slowest task), not 7.5s (sum of all)\n");
    }

    // ========================================================================
    // DEMO 5: invokeAny — submit all, get first result
    // ========================================================================
    static void demo5_InvokeAny() throws Exception {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 5: invokeAny() — first to complete wins");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * invokeAny:
         *   - Submits all tasks
         *   - Returns the result of the FIRST one that completes successfully
         *   - Cancels remaining tasks
         *   - Use case: redundant calls to multiple servers, take fastest response
         */

        ExecutorService pool = Executors.newFixedThreadPool(3);

        List<Callable<String>> tasks = List.of(
                () -> { Thread.sleep(2000); return "Server-A responded (2s)"; },
                () -> { Thread.sleep(500);  return "Server-B responded (0.5s)"; },
                () -> { Thread.sleep(1000); return "Server-C responded (1s)"; }
        );

        long start = System.currentTimeMillis();
        String fastest = pool.invokeAny(tasks);
        long elapsed = System.currentTimeMillis() - start;

        System.out.println("  Winner: " + fastest + " in " + elapsed + "ms");
        System.out.println("  → Other tasks were cancelled automatically");

        pool.shutdown();
        System.out.println();
    }

    // ========================================================================
    // DEMO 6: Exception handling in Callable
    // ========================================================================
    static void demo6_ExceptionHandling() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 6: Exception Handling in Callable/Future");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * If a Callable throws an exception:
         *   - Future.isDone() returns true
         *   - Future.get() throws ExecutionException wrapping the original exception
         *   - The original exception is in e.getCause()
         */

        ExecutorService pool = Executors.newSingleThreadExecutor();

        Future<Integer> future = pool.submit(() -> {
            System.out.println("  [Worker] About to fail...");
            throw new RuntimeException("Something went wrong!");
        });

        try {
            future.get();
        } catch (ExecutionException e) {
            System.out.println("  ExecutionException caught!");
            System.out.println("  Cause: " + e.getCause().getClass().getSimpleName()
                    + " — " + e.getCause().getMessage());
        }

        pool.shutdown();
        System.out.println();
    }

    // ========================================================================
    // MAIN
    // ========================================================================
    public static void main(String[] args) throws Exception {
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║   PHASE 2C: CALLABLE & FUTURE                    ║");
        System.out.println("╚═══════════════════════════════════════════════════╝\n");

        demo1_BasicCallableFuture();
        demo2_FutureTimeout();
        demo3_CancelTask();
        demo4_InvokeAll();
        demo5_InvokeAny();
        demo6_ExceptionHandling();

        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("1. Callable<V> = Runnable that returns a value and throws exceptions");
        System.out.println("2. Future<V> = a handle to the eventual result");
        System.out.println("3. get() blocks, get(timeout) throws TimeoutException");
        System.out.println("4. cancel(true) interrupts a running task");
        System.out.println("5. invokeAll — submit all, wait for all to complete");
        System.out.println("6. invokeAny — submit all, take the fastest result");
        System.out.println("7. Exceptions are wrapped in ExecutionException");
        System.out.println("8. Next: CompletableFuture (non-blocking, chainable)");
    }
}
