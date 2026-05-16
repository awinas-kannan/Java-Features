package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.phase2_juc_core;

import java.util.concurrent.*;

/**
 * ============================================================================
 * B - EXECUTOR FRAMEWORK: Thread Pool Management
 * ============================================================================
 *
 * WHY THREAD POOLS?
 * ─────────────────
 * Creating a new Thread for every task is expensive:
 *   - OS thread creation overhead (~1MB stack per thread)
 *   - Context switching cost
 *   - No upper bound — 10,000 tasks = 10,000 threads = crash (OOM)
 *
 * Thread pools REUSE a fixed set of threads to execute many tasks.
 * Like a restaurant with 5 waiters serving 100 customers — waiters are reused.
 *
 * HIERARCHY:
 * ──────────
 * Executor               → execute(Runnable) — fire and forget
 *   └── ExecutorService  → submit(), invokeAll(), invokeAny(), shutdown()
 *         └── ScheduledExecutorService → schedule(), scheduleAtFixedRate()
 *
 * POOL TYPES (Executors factory methods):
 * ───────────────────────────────────────
 * ┌──────────────────────────┬───────┬──────────────────────────────────────┐
 * │ Method                   │ Cores │ Use Case                             │
 * ├──────────────────────────┼───────┼──────────────────────────────────────┤
 * │ newFixedThreadPool(n)    │   n   │ Known load, CPU-bound tasks          │
 * │ newCachedThreadPool()    │ 0→∞   │ Many short-lived tasks, I/O-bound   │
 * │ newSingleThreadExecutor()│   1   │ Sequential execution, ordering       │
 * │ newScheduledThreadPool(n)│   n   │ Delayed/periodic tasks (cron-like)   │
 * │ newWorkStealingPool()    │ cores │ Parallel CPU work (Java 8+)          │
 * └──────────────────────────┴───────┴──────────────────────────────────────┘
 *
 * SHUTDOWN:
 * ─────────
 * shutdown()       → no new tasks accepted, existing tasks complete
 * shutdownNow()    → attempts to cancel running tasks, returns pending ones
 * awaitTermination → blocks until all tasks finish or timeout
 *
 * ALWAYS shut down your ExecutorService! Otherwise JVM won't exit.
 */
public class B_ExecutorFramework {

    // ========================================================================
    // DEMO 1: FixedThreadPool — fixed number of worker threads
    // ========================================================================
    static void demo1_FixedThreadPool() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 1: FixedThreadPool — 3 threads handling 6 tasks");
        System.out.println("═══════════════════════════════════════════════════");

        ExecutorService pool = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 6; i++) {
            final int taskId = i;
            pool.execute(() -> {
                System.out.println("  Task-" + taskId + " running on " + Thread.currentThread().getName());
                try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                System.out.println("  Task-" + taskId + " done");
            });
        }

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("  → 6 tasks completed by 3 threads (threads were REUSED)\n");
    }

    // ========================================================================
    // DEMO 2: CachedThreadPool — creates threads on demand, reuses idle ones
    // ========================================================================
    static void demo2_CachedThreadPool() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 2: CachedThreadPool — elastic pool");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * - Creates new threads as needed
         * - Reuses idle threads (idle for 60 seconds → removed)
         * - No upper limit! Danger: can create thousands of threads under heavy load
         * - Best for: many short-lived I/O tasks (HTTP calls, DB queries)
         */

        ExecutorService pool = Executors.newCachedThreadPool();

        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            pool.execute(() -> {
                System.out.println("  Task-" + taskId + " on " + Thread.currentThread().getName());
                try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            });
        }

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("  → Notice different thread names — pool created threads as needed\n");
    }

    // ========================================================================
    // DEMO 3: SingleThreadExecutor — sequential, guaranteed ordering
    // ========================================================================
    static void demo3_SingleThreadExecutor() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 3: SingleThreadExecutor — guaranteed ordering");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * - Only ONE thread
         * - Tasks execute in submission order (FIFO)
         * - If the thread dies (exception), a new one is created
         * - Use for: logging, sequential DB writes, event processing
         */

        ExecutorService pool = Executors.newSingleThreadExecutor();

        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            pool.execute(() -> {
                System.out.println("  Task-" + taskId + " on " + Thread.currentThread().getName());
            });
        }

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("  → All tasks ran on the SAME thread, in order 1→2→3→4→5\n");
    }

    // ========================================================================
    // DEMO 4: ScheduledThreadPool — delayed and periodic execution
    // ========================================================================
    static void demo4_ScheduledThreadPool() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 4: ScheduledThreadPool — delayed & periodic tasks");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * THREE scheduling methods:
         *
         * ┌───────────────────────────────────────────────────────────────────────┐
         * │ Method                  │ initialDelay │ period/delay │ Behavior      │
         * ├─────────────────────────┼──────────────┼──────────────┼───────────────┤
         * │ schedule(task, delay)   │ delay        │ —            │ Run ONCE      │
         * │ scheduleAtFixedRate     │ initialDelay │ period       │ Run REPEATEDLY│
         * │ scheduleWithFixedDelay  │ initialDelay │ delay        │ Run REPEATEDLY│
         * └───────────────────────────────────────────────────────────────────────┘
         *
         * DELAY vs PERIOD — the key difference:
         * ─────────────────────────────────────
         *
         * scheduleAtFixedRate(task, initialDelay=0, period=1000ms):
         *   "Start a new run every 1000ms from the BEGINNING of the previous run"
         *
         *   Time:  0ms      1000ms    2000ms    3000ms
         *          |─task───|─task───|─task───|─task───
         *          ↑        ↑        ↑        ↑
         *          start    start    start    start
         *          (period is measured from START to START)
         *
         *   If task takes 300ms:
         *     Run1: 0ms─300ms, then waits 700ms
         *     Run2: 1000ms─1300ms, then waits 700ms
         *     → Runs every 1000ms regardless of task duration
         *
         *   If task takes 1500ms (LONGER than period):
         *     Run1: 0ms─1500ms (exceeds period!)
         *     Run2: starts immediately at 1500ms (no wait, plays catch-up)
         *     → Won't overlap, but next run starts immediately after
         *
         *
         * scheduleWithFixedDelay(task, initialDelay=0, delay=1000ms):
         *   "Wait 1000ms AFTER the previous run FINISHES before starting next"
         *
         *   Time:  0ms   300ms         1300ms  1600ms         2600ms
         *          |─task─|────delay────|─task──|────delay─────|─task─
         *          ↑      ↑             ↑      ↑              ↑
         *          start  end           start  end            start
         *          (delay is measured from END to next START)
         *
         *   If task takes 300ms:
         *     Run1: 0ms─300ms, then delay 1000ms
         *     Run2: 1300ms─1600ms, then delay 1000ms
         *     → Gap between runs is always 1000ms
         *
         *   If task takes 1500ms:
         *     Run1: 0ms─1500ms, then delay 1000ms
         *     Run2: 2500ms─4000ms, then delay 1000ms
         *     → Gap is still always 1000ms after completion
         *
         *
         * SUMMARY:
         *   FixedRate  → period between STARTS (consistent frequency)
         *   FixedDelay → delay between END and next START (consistent gap)
         *
         * initialDelay = how long to wait before the FIRST run
         */

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        long startTime = System.currentTimeMillis();

        // ─── schedule() — run ONCE after a delay ───
        System.out.println("  [schedule] One-time task: initialDelay=1s");
        scheduler.schedule(() -> {
            long elapsed = System.currentTimeMillis() - startTime;
            System.out.println("  [One-time] Fired at +" + elapsed + "ms (expected ~1000ms)");
        }, 1, TimeUnit.SECONDS);

        // ─── scheduleAtFixedRate — period between STARTS ───
        System.out.println("  [FixedRate] initialDelay=0, period=1000ms, task takes 300ms");
        ScheduledFuture<?> fixedRate = scheduler.scheduleAtFixedRate(() -> {
            long elapsed = System.currentTimeMillis() - startTime;
            System.out.println("  [FixedRate] START at +" + elapsed + "ms on " + Thread.currentThread().getName());
            try { Thread.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, 0, 1000, TimeUnit.MILLISECONDS);

        Thread.sleep(3500);
        fixedRate.cancel(false);
        System.out.println("  [FixedRate] Cancelled. Notice: runs at ~0, ~1000, ~2000, ~3000 (period from start)\n");

        Thread.sleep(500);

        // ─── scheduleWithFixedDelay — delay between END and next START ───
        long startTime2 = System.currentTimeMillis();
        System.out.println("  [FixedDelay] initialDelay=0, delay=1000ms, task takes 300ms");
        ScheduledFuture<?> fixedDelay = scheduler.scheduleWithFixedDelay(() -> {
            long elapsed = System.currentTimeMillis() - startTime2;
            System.out.println("  [FixedDelay] START at +" + elapsed + "ms on " + Thread.currentThread().getName());
            try { Thread.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, 0, 1000, TimeUnit.MILLISECONDS);

        Thread.sleep(4500);
        fixedDelay.cancel(false);
        System.out.println("  [FixedDelay] Cancelled. Notice: runs at ~0, ~1300, ~2600, ~3900 (delay from end)\n");

        scheduler.shutdown();
        scheduler.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("  COMPARISON (task takes 300ms, interval=1000ms):");
        System.out.println("  ┌─────────────┬──────────────────────────────────────────┐");
        System.out.println("  │ FixedRate   │ Runs at: 0, 1000, 2000, 3000 (every 1s) │");
        System.out.println("  │ FixedDelay  │ Runs at: 0, 1300, 2600, 3900 (1s gap)   │");
        System.out.println("  └─────────────┴──────────────────────────────────────────┘");
        System.out.println("  → FixedRate: consistent FREQUENCY (good for heartbeats)");
        System.out.println("  → FixedDelay: consistent GAP (good for polling after work)\n");
    }

    // ========================================================================
    // DEMO 5: WorkStealingPool — parallel, uses all CPU cores
    // ========================================================================
    static void demo5_WorkStealingPool() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 5: WorkStealingPool — uses all CPU cores");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * - Uses ForkJoinPool internally
         * - Default parallelism = number of available CPU cores
         * - Idle threads STEAL tasks from busy threads' queues
         * - Best for: CPU-intensive, parallelizable tasks
         */

        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("  Available cores: " + cores);

        ExecutorService pool = Executors.newWorkStealingPool();

        for (int i = 1; i <= 100; i++) {
            final int taskId = i;
            pool.execute(() -> {
                long sum = 0;
                for (int j = 0; j < 10_000_000; j++) sum += j;
                System.out.println("  Task-" + taskId + " done on " + Thread.currentThread().getName());
            });
        }

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("  → Work-stealing distributes load across all cores\n");
    }

    // ========================================================================
    // DEMO 6: ThreadPoolExecutor — full control over pool configuration
    // ========================================================================
    static void demo6_CustomThreadPool() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 6: ThreadPoolExecutor — full control");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, unit, workQueue)
         *
         * corePoolSize:  minimum threads always alive (even if idle)
         * maxPoolSize:   maximum threads the pool can grow to
         * keepAliveTime: how long idle threads above corePoolSize survive
         * workQueue:     queue to hold tasks when all core threads are busy
         *
         * Flow:
         *   1. task arrives → core thread available? → assign to core thread
         *   2. all core threads busy? → put in queue
         *   3. queue full? → create new thread (up to maxPoolSize)
         *   4. max threads reached + queue full? → RejectedExecutionHandler
         */

        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                2,                           // corePoolSize
                4,                           // maxPoolSize
                30, TimeUnit.SECONDS,        // keepAliveTime for idle threads
                new ArrayBlockingQueue<>(2), // bounded queue (capacity 2)
                new ThreadPoolExecutor.CallerRunsPolicy() // rejection policy
        );

        System.out.println("  Core pool: 2, Max pool: 4, Queue capacity: 2");
        System.out.println("  → Can handle 6 concurrent tasks (4 threads + 2 in queue)");
        System.out.println();

        for (int i = 1; i <= 8; i++) {
            final int taskId = i;
            pool.execute(() -> {
                System.out.println("  Task-" + taskId + " on " + Thread.currentThread().getName()
                        + " | Pool size: " + pool.getPoolSize()
                        + " | Queue: " + pool.getQueue().size());
                try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            });
        }

        pool.shutdown();
        pool.awaitTermination(15, TimeUnit.SECONDS);
        System.out.println("  → Tasks 7-8 ran on main thread (CallerRunsPolicy)\n");
    }

    // ========================================================================
    // DEMO 7: Shutdown — the right way
    // ========================================================================
    static void demo7_ProperShutdown() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 7: Proper Shutdown Pattern");
        System.out.println("═══════════════════════════════════════════════════");

        ExecutorService pool = Executors.newFixedThreadPool(2);

        pool.execute(() -> {
            System.out.println("  Task-1 running...");
            try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            System.out.println("  Task-1 done");
        });
        pool.execute(() -> {
            System.out.println("  Task-2 running...");
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            System.out.println("  Task-2 done");
        });

        // The proper shutdown pattern (from Java docs)
        pool.shutdown(); // stop accepting new tasks
        System.out.println("  shutdown() called — no new tasks accepted");

        if (!pool.awaitTermination(3, TimeUnit.SECONDS)) {
            System.out.println("  Tasks didn't finish in 3s — forcing shutdown");
            pool.shutdownNow(); // cancel running tasks
        } else {
            System.out.println("  All tasks completed gracefully");
        }
        System.out.println();
    }

    // ========================================================================
    // MAIN
    // ========================================================================
    public static void main(String[] args) throws InterruptedException {
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║   PHASE 2B: EXECUTOR FRAMEWORK                   ║");
        System.out.println("╚═══════════════════════════════════════════════════╝\n");

        demo1_FixedThreadPool();
        demo2_CachedThreadPool();
        demo3_SingleThreadExecutor();
        demo4_ScheduledThreadPool();
        demo5_WorkStealingPool();
        demo6_CustomThreadPool();
        demo7_ProperShutdown();

        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("1. Thread pools REUSE threads — no creation overhead per task");
        System.out.println("2. FixedThreadPool — known load, predictable threads");
        System.out.println("3. CachedThreadPool — elastic, short I/O tasks (danger: unbounded)");
        System.out.println("4. SingleThread — sequential, ordered execution");
        System.out.println("5. ScheduledThreadPool — delayed/periodic tasks (cron-like)");
        System.out.println("6. WorkStealingPool — CPU-bound parallel work");
        System.out.println("7. ThreadPoolExecutor — full control (core, max, queue, rejection)");
        System.out.println("8. ALWAYS call shutdown() — otherwise JVM won't exit");
    }
}
