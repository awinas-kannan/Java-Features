package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.phase1_foundations;

/**
 * ============================================================================
 * B - THREAD METHODS: Controlling Thread Behavior
 * ============================================================================
 *
 * ESSENTIAL THREAD METHODS:
 * ─────────────────────────
 * ┌────────────────────┬─────────────────────────────────────────────────────┐
 * │ Method             │ What it does                                        │
 * ├────────────────────┼─────────────────────────────────────────────────────┤
 * │ start()            │ Creates new OS thread and calls run()               │
 * │ run()              │ The task body (don't call directly!)                │
 * │ sleep(ms)          │ Pauses CURRENT thread for ms milliseconds          │
 * │ join()             │ Caller waits until this thread dies                 │
 * │ join(ms)           │ Caller waits at most ms milliseconds               │
 * │ yield()            │ Hint to scheduler: "I can pause, let others run"   │
 * │ interrupt()        │ Sets the interrupt flag on the target thread        │
 * │ isInterrupted()    │ Checks interrupt flag WITHOUT clearing it          │
 * │ interrupted()      │ STATIC — checks AND CLEARS interrupt flag          │
 * │ isAlive()          │ true if thread started and not yet terminated       │
 * │ setDaemon(bool)    │ Mark as daemon (must call BEFORE start())          │
 * │ isDaemon()         │ Check if thread is daemon                          │
 * │ setPriority(int)   │ Set priority 1-10 (hint to OS scheduler)           │
 * │ setName(String)    │ Give thread a meaningful name (for debugging)      │
 * │ getId()            │ Unique long ID for this thread                     │
 * │ getState()         │ Returns current Thread.State enum                  │
 * └────────────────────┴─────────────────────────────────────────────────────┘
 *
 * DAEMON vs USER THREADS:
 * ───────────────────────
 * User Thread (default):
 *   - JVM waits for ALL user threads to finish before shutting down
 *   - Your main() method runs on a user thread
 *
 * Daemon Thread:
 *   - Background "service" threads (GC, finalizer, etc.)
 *   - JVM does NOT wait for daemon threads — kills them on shutdown
 *   - If all user threads finish, daemon threads are abruptly terminated
 *   - Use case: background logging, monitoring, cleanup tasks
 *
 *   ⚠ RULE: setDaemon(true) MUST be called BEFORE start()
 *           Otherwise → IllegalThreadStateException
 */
public class B_ThreadMethods {

    // ========================================================================
    // DEMO 1: sleep() — Pausing the current thread
    // ========================================================================
    static void demo1_Sleep() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 1: sleep() — Pausing execution");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * sleep(ms) does:
         *   1. Pauses the CURRENT thread (not some other thread!)
         *   2. Thread enters TIMED_WAITING state
         *   3. Does NOT release locks (important for synchronization!)
         *   4. After ms milliseconds, thread becomes RUNNABLE again
         *   5. Throws InterruptedException if interrupted while sleeping
         */

        Thread worker = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println("  [Worker] Step " + i + " at " + System.currentTimeMillis() % 10000 + "ms");
                try {
                    Thread.sleep(1000); // pause 1 second between steps
                } catch (InterruptedException e) {
                    System.out.println("  [Worker] I was interrupted during sleep!");
                    return;
                }
            }
        }, "Worker");

        worker.start();
        worker.join();
        System.out.println("  → Notice ~1000ms gap between each step\n");
    }

    // ========================================================================
    // DEMO 2: join() — Waiting for another thread to complete
    // ========================================================================
    static void demo2_Join() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 2: join() — Wait for thread to finish");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * join() does:
         *   1. CALLER thread (e.g., main) blocks until the target thread finishes
         *   2. Calling thread enters WAITING state
         *   3. join(ms) — waits at most ms milliseconds (then continues regardless)
         *
         * Without join:  main continues immediately, may print results before worker finishes
         * With join:     main waits, guarantees worker is done before continuing
         */

        // --- WITHOUT join ---
        System.out.println("  [Without join]:");
        Thread t1 = new Thread(() -> {
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            System.out.println("    Worker finished");
        });
        t1.start();
        System.out.println("    Main continues immediately (worker may not be done!)");
        t1.join(); // still join here to prevent output interleaving with next demo

        System.out.println();

        // --- WITH join ---
        System.out.println("  [With join]:");
        Thread t2 = new Thread(() -> {
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            System.out.println("    Worker finished");
        });
        t2.start();
        t2.join(); // main WAITS here until t2 is done
        System.out.println("    Main continues AFTER worker is done (guaranteed!)");

        System.out.println();

        // --- join with timeout ---
        System.out.println("  [join with timeout]:");
        Thread t3 = new Thread(() -> {
            try { Thread.sleep(5000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            System.out.println("    Worker finished (you may not see this if we moved on)");
        });
        t3.start();
        t3.join(1000); // wait at most 1 second
        System.out.println("    Main waited 1s, then moved on. Worker alive? " + t3.isAlive());
        t3.interrupt(); // clean up
        System.out.println();
    }

    // ========================================================================
    // DEMO 3: yield() — Suggesting scheduler give others a turn
    // ========================================================================
    static void demo3_Yield() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 3: yield() — Hint to scheduler");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * yield() does:
         *   1. Tells the thread scheduler: "I'm willing to pause, let equal-priority threads run"
         *   2. It's just a HINT — the scheduler may ignore it completely
         *   3. Thread stays in RUNNABLE state (doesn't go to WAITING)
         *   4. Rarely used in practice — mostly for fine-tuning performance
         *
         * Think of it as a polite "after you" at a door — no guarantee the other person goes first.
         */

        Runnable task = () -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println("  " + Thread.currentThread().getName() + " — step " + i);
                Thread.yield(); // politely suggest other threads can run
            }
        };

        Thread t1 = new Thread(task, "Thread-A");
        Thread t2 = new Thread(task, "Thread-B");
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("  → The interleaving may vary — yield() is just a hint\n");
    }

    // ========================================================================
    // DEMO 4: interrupt() — Cooperative cancellation
    // ========================================================================
    static void demo4_Interrupt() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 4: interrupt() — Cooperative Cancellation");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * interrupt() does NOT forcefully stop a thread!
         * It sets a boolean flag (interrupt flag) on the target thread.
         *
         * The thread must COOPERATE by checking this flag:
         *   - If sleeping/waiting → InterruptedException is thrown, flag is cleared
         *   - If running normally → must manually check Thread.interrupted() or isInterrupted()
         *
         * This is Java's designed way to stop threads.
         * Thread.stop() is DEPRECATED — never use it (unsafe, can corrupt data).
         *
         * PATTERN:
         *   while (!Thread.currentThread().isInterrupted()) {
         *       // do work
         *   }
         */

        // Scenario A: Interrupt a sleeping thread
        System.out.println("  [Scenario A: Interrupt during sleep]");
        Thread sleeper = new Thread(() -> {
            try {
                System.out.println("    Sleeper: Going to sleep for 10 seconds...");
                Thread.sleep(10000);
                System.out.println("    Sleeper: Woke up naturally (you won't see this)");
            } catch (InterruptedException e) {
                System.out.println("    Sleeper: Interrupted during sleep! Exiting gracefully.");
            }
        });
        sleeper.start();
        Thread.sleep(500); // let it start sleeping
        sleeper.interrupt(); // wake it up!
        sleeper.join();

        System.out.println();

        // Scenario B: Interrupt a busy-working thread
        System.out.println("  [Scenario B: Interrupt a busy thread]");
        Thread busyWorker = new Thread(() -> {
            long count = 0;
            while (!Thread.currentThread().isInterrupted()) {
                count++;
                if (count % 100_000_000 == 0) {
                    System.out.println("    BusyWorker: processed " + count + " iterations");
                }
            }
            System.out.println("    BusyWorker: Interrupt detected! Stopping after " + count + " iterations.");
        });
        busyWorker.start();
        Thread.sleep(500); // let it work for 500ms
        busyWorker.interrupt();
        busyWorker.join();

        System.out.println();
    }

    // ========================================================================
    // DEMO 5: Daemon vs User threads
    // ========================================================================
    static void demo5_DaemonThread() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 5: Daemon vs User Threads");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * USER THREAD (default):
         *   - JVM stays alive as long as any user thread is running
         *   - main thread is a user thread
         *
         * DAEMON THREAD:
         *   - JVM exits when all user threads finish (daemons are killed)
         *   - Useful for: GC, background monitoring, log flushing
         *   - setDaemon(true) MUST be called BEFORE start()
         *
         * In this demo, we keep it contained so it doesn't affect other demos.
         */

        Thread daemon = new Thread(() -> {
            int count = 0;
            while (true) {
                count++;
                if (count <= 3) {
                    System.out.println("    [Daemon] Background heartbeat #" + count);
                }
                try { Thread.sleep(300); } catch (InterruptedException e) { return; }
            }
        }, "BackgroundMonitor");

        daemon.setDaemon(true); // MUST be before start()!
        System.out.println("  Is daemon? " + daemon.isDaemon());

        Thread userThread = new Thread(() -> {
            try {
                System.out.println("    [User] Doing important work...");
                Thread.sleep(1000);
                System.out.println("    [User] Work done!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "ImportantWork");

        daemon.start();
        userThread.start();

        userThread.join();
        System.out.println("  → Daemon thread would be killed here if this were the only user thread left");
        daemon.interrupt(); // clean up for our demo
        System.out.println();
    }

    // ========================================================================
    // DEMO 6: Thread Priority & Naming
    // ========================================================================
    static void demo6_PriorityAndNaming() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 6: Thread Priority & Naming");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * PRIORITY (1-10):
         *   Thread.MIN_PRIORITY  = 1
         *   Thread.NORM_PRIORITY = 5  (default)
         *   Thread.MAX_PRIORITY  = 10
         *
         * Priority is a HINT to the OS scheduler. Higher priority threads
         * MAY get more CPU time, but it's NOT guaranteed.
         * Different OS handle priorities differently.
         *
         * NAMING:
         *   Always name your threads! Makes debugging 100x easier.
         *   "Thread-0" tells you nothing. "OrderProcessor-1" tells you everything.
         */

        Thread low = new Thread(() -> {
            long sum = 0;
            for (int i = 0; i < 50_000_000; i++) sum += i;
            System.out.println("  " + Thread.currentThread().getName()
                    + " (priority=" + Thread.currentThread().getPriority() + ") finished");
        }, "Low-Priority-Thread");

        Thread high = new Thread(() -> {
            long sum = 0;
            for (int i = 0; i < 50_000_000; i++) sum += i;
            System.out.println("  " + Thread.currentThread().getName()
                    + " (priority=" + Thread.currentThread().getPriority() + ") finished");
        }, "High-Priority-Thread");

        low.setPriority(Thread.MIN_PRIORITY);   // 1
        high.setPriority(Thread.MAX_PRIORITY);   // 10

        low.start();
        high.start();
        low.join();
        high.join();

        System.out.println("  → Higher priority MIGHT finish first, but no guarantee\n");
    }

    // ========================================================================
    // MAIN — Run all demos
    // ========================================================================
    public static void main(String[] args) throws InterruptedException {
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║   PHASE 1B: THREAD METHODS                       ║");
        System.out.println("╚═══════════════════════════════════════════════════╝\n");

        demo1_Sleep();
        demo2_Join();
        demo3_Yield();
        demo4_Interrupt();
        demo5_DaemonThread();
        demo6_PriorityAndNaming();

        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("1. sleep(ms) — pauses current thread, does NOT release locks");
        System.out.println("2. join()    — caller blocks until target thread finishes");
        System.out.println("3. yield()   — polite hint to scheduler (rarely used)");
        System.out.println("4. interrupt() — sets a flag, thread must COOPERATE to stop");
        System.out.println("5. Daemon threads die when all user threads finish");
        System.out.println("6. setDaemon(true) MUST be before start()");
        System.out.println("7. Priority is a HINT, not a guarantee");
        System.out.println("8. ALWAYS name your threads for easier debugging");
    }
}
