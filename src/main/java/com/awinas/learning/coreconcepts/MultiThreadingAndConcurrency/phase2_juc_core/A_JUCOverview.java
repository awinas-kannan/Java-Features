package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.phase2_juc_core;

/**
 * ============================================================================
 * A - java.util.concurrent (JUC) — Overview
 * ============================================================================
 *
 * WHY DO WE NEED JUC?
 * ────────────────────
 * Phase 1 used raw threads + synchronized + wait/notify. Problems:
 *   1. Manual thread creation is expensive (OS thread per task)
 *   2. synchronized is coarse-grained (can't try-lock, can't timeout)
 *   3. wait/notify is error-prone (while loops, spurious wakeups)
 *   4. No built-in way to return a value from a thread
 *   5. No built-in thread pool / reuse
 *
 * JUC (added in Java 5) solves ALL of these with production-grade utilities.
 *
 * THE JUC PACKAGE MAP:
 * ────────────────────
 * java.util.concurrent
 * ├── Executors & Thread Pools     → File B (manage thread lifecycle)
 * │   ├── ExecutorService
 * │   ├── ThreadPoolExecutor
 * │   └── ScheduledExecutorService
 * │
 * ├── Callable & Future            → File C (return values from threads)
 * │   ├── Callable<V>
 * │   ├── Future<V>
 * │   └── CompletableFuture<V>     → Files D, E
 * │
 * ├── Locks                        → File F (flexible locking)
 * │   ├── ReentrantLock
 * │   ├── ReadWriteLock
 * │   └── StampedLock
 * │
 * ├── Condition                    → File G (modern wait/notify)
 * │
 * ├── Synchronizers                → Phase 3
 * │   ├── CountDownLatch
 * │   ├── CyclicBarrier
 * │   ├── Semaphore
 * │   └── Phaser
 * │
 * ├── Concurrent Collections       → Phase 3
 * │   ├── ConcurrentHashMap
 * │   ├── CopyOnWriteArrayList
 * │   └── BlockingQueue variants
 * │
 * └── Atomic Classes               → Phase 4
 *     ├── AtomicInteger
 *     ├── AtomicReference
 *     └── LongAdder
 *
 * RAW THREADS vs JUC:
 * ───────────────────
 * ┌──────────────────────────┬────────────────────────────────────────┐
 * │ Raw Threads (Phase 1)    │ JUC (Phase 2+)                        │
 * ├──────────────────────────┼────────────────────────────────────────┤
 * │ new Thread(task).start() │ executor.submit(task)                  │
 * │ thread.join()            │ future.get()                           │
 * │ synchronized             │ ReentrantLock / StampedLock            │
 * │ wait() / notify()        │ Condition.await() / signal()           │
 * │ No return value          │ Callable<V> + Future<V>                │
 * │ No thread reuse          │ Thread pool (reuses threads)           │
 * │ Manual error handling    │ CompletableFuture.exceptionally()      │
 * │ Can't cancel easily      │ future.cancel()                        │
 * └──────────────────────────┴────────────────────────────────────────┘
 */
public class A_JUCOverview {

    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║   PHASE 2A: java.util.concurrent OVERVIEW        ║");
        System.out.println("╚═══════════════════════════════════════════════════╝\n");

        System.out.println("This file is theory-only. The concepts are demonstrated in files B through G.");
        System.out.println();
        System.out.println("Phase 2 Learning Path:");
        System.out.println("  B — Executor Framework (thread pools, pool types)");
        System.out.println("  C — Callable & Future (return values, cancellation)");
        System.out.println("  D — CompletableFuture Part 1 (async pipelines, exception handling)");
        System.out.println("  E — CompletableFuture Part 2 (compose, combine, allOf, anyOf)");
        System.out.println("  F — Locks (ReentrantLock, ReadWriteLock, StampedLock)");
        System.out.println("  G — Condition Variables (modern wait/notify replacement)");
    }
}
