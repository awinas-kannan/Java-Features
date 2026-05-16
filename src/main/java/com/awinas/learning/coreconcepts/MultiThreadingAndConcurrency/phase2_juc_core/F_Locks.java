package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.phase2_juc_core;

import java.util.concurrent.locks.*;
import java.util.concurrent.TimeUnit;

/**
 * ============================================================================
 * F - LOCKS: ReentrantLock, ReadWriteLock, StampedLock
 * ============================================================================
 *
 * WHY LOCKS OVER SYNCHRONIZED?
 * ────────────────────────────
 * synchronized is simple but limited:
 *   ✗ Can't try to acquire (blocks forever)
 *   ✗ Can't timeout while waiting
 *   ✗ Can't interrupt a waiting thread
 *   ✗ Can't have separate read/write locks
 *   ✗ No fairness control
 *   ✗ Must release in the same block you acquired
 *
 * java.util.concurrent.locks fixes ALL of these.
 *
 * LOCK TYPES:
 * ───────────
 * ┌──────────────────┬────────────────────────────────────────────────┐
 * │ Lock Type        │ Use Case                                       │
 * ├──────────────────┼────────────────────────────────────────────────┤
 * │ ReentrantLock    │ General replacement for synchronized           │
 * │ ReadWriteLock    │ Many readers, few writers (read-heavy apps)    │
 * │ StampedLock      │ Optimistic reading (even faster reads)         │
 * └──────────────────┴────────────────────────────────────────────────┘
 *
 * CRITICAL RULE:
 *   Always use try-finally with explicit locks!
 *   lock.lock();
 *   try { ... }
 *   finally { lock.unlock(); }
 *
 *   synchronized releases automatically. Explicit locks do NOT.
 *   Forget unlock() → deadlock forever.
 */
public class F_Locks {

    // ========================================================================
    // DEMO 1: ReentrantLock — basic usage
    // ========================================================================
    static void demo1_ReentrantLock() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 1: ReentrantLock — basic lock/unlock");
        System.out.println("═══════════════════════════════════════════════════");

        ReentrantLock lock = new ReentrantLock();
        int[] counter = {0};

        Runnable task = () -> {
            for (int i = 0; i < 100_000; i++) {
                lock.lock();
                try {
                    counter[0]++;
                } finally {
                    lock.unlock(); // ALWAYS in finally!
                }
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("  Counter: " + counter[0] + " (expected 200000)");
        System.out.println("  → Same as synchronized, but with explicit lock/unlock\n");
    }

    // ========================================================================
    // DEMO 2: tryLock — non-blocking attempt
    // ========================================================================
    static void demo2_TryLock() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 2: tryLock() — don't block, try and move on");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * tryLock()             → returns immediately: true if acquired, false if not
         * tryLock(time, unit)   → waits up to timeout, then returns false
         *
         * Unlike synchronized which blocks forever!
         */

        ReentrantLock lock = new ReentrantLock();

        Thread holder = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("  [Holder] Holding lock for 2 seconds...");
                Thread.sleep(2000);
            } catch (InterruptedException e) { Thread.currentThread().interrupt();
            } finally { lock.unlock(); }
        });

        Thread tryThread = new Thread(() -> {
            try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

            // tryLock() — instant check
            boolean got = lock.tryLock();
            System.out.println("  [TryThread] tryLock() = " + got);

            // tryLock with timeout
            try {
                boolean gotWithTimeout = lock.tryLock(3, TimeUnit.SECONDS);
                if (gotWithTimeout) {
                    try {
                        System.out.println("  [TryThread] Got lock after waiting!");
                    } finally { lock.unlock(); }
                } else {
                    System.out.println("  [TryThread] Gave up waiting");
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        holder.start(); tryThread.start();
        holder.join(); tryThread.join();
        System.out.println();
    }

    // ========================================================================
    // DEMO 3: Fair lock — FIFO ordering
    // ========================================================================
    static void demo3_FairLock() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 3: Fair Lock — longest waiting thread goes first");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * new ReentrantLock(true)  → FAIR: threads acquire in FIFO order
         * new ReentrantLock(false) → UNFAIR (default): no ordering guarantee
         *
         * Fair locks prevent STARVATION but are SLOWER (overhead of maintaining queue).
         */

        ReentrantLock fairLock = new ReentrantLock(true); // fair = true

        Runnable task = () -> {
            for (int i = 0; i < 3; i++) {
                fairLock.lock();
                try {
                    System.out.println("  " + Thread.currentThread().getName() + " — iteration " + (i + 1));
                    Thread.sleep(100);
                } catch (InterruptedException e) { Thread.currentThread().interrupt();
                } finally { fairLock.unlock(); }
            }
        };

        Thread t1 = new Thread(task, "Thread-A");
        Thread t2 = new Thread(task, "Thread-B");
        Thread t3 = new Thread(task, "Thread-C");
        t1.start(); t2.start(); t3.start();
        t1.join(); t2.join(); t3.join();

        System.out.println("  → With fair lock, threads take turns (A→B→C→A→B→C...)\n");
    }

    // ========================================================================
    // DEMO 4: lockInterruptibly — can be interrupted while waiting
    // ========================================================================
    static void demo4_LockInterruptibly() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 4: lockInterruptibly — cancellable waiting");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * lock.lock()              → blocks forever, cannot be interrupted
         * lock.lockInterruptibly() → blocks but CAN be interrupted
         *
         * Use when you want to cancel a thread that's waiting for a lock.
         */

        ReentrantLock lock = new ReentrantLock();
        lock.lock(); // main holds the lock

        Thread waiter = new Thread(() -> {
            try {
                System.out.println("  [Waiter] Trying lockInterruptibly...");
                lock.lockInterruptibly(); // will block, but can be interrupted
                try {
                    System.out.println("  [Waiter] Got the lock (you won't see this)");
                } finally { lock.unlock(); }
            } catch (InterruptedException e) {
                System.out.println("  [Waiter] Interrupted while waiting for lock! Exiting.");
            }
        });

        waiter.start();
        Thread.sleep(500);
        System.out.println("  [Main] Interrupting waiter...");
        waiter.interrupt();
        waiter.join();
        lock.unlock();
        System.out.println();
    }

    // ========================================================================
    // DEMO 5: ReadWriteLock — many readers, exclusive writer
    // ========================================================================
    static void demo5_ReadWriteLock() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 5: ReadWriteLock — concurrent reads, exclusive writes");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * Problem: synchronized locks out ALL threads, even readers.
         *   10 threads just reading? Only 1 can read at a time! Wasteful.
         *
         * ReadWriteLock:
         *   Read lock:  MULTIPLE threads can hold simultaneously (if no writer)
         *   Write lock: EXCLUSIVE — only one thread, no readers allowed
         *
         * Perfect for read-heavy data: config caches, lookup tables, etc.
         */

        ReadWriteLock rwLock = new ReentrantReadWriteLock();
        final String[] data = {"Initial Value"};

        // Readers — can run in parallel
        Runnable reader = () -> {
            rwLock.readLock().lock();
            try {
                System.out.println("  [Reader " + Thread.currentThread().getName() + "] Read: " + data[0]);
                Thread.sleep(200);
            } catch (InterruptedException e) { Thread.currentThread().interrupt();
            } finally { rwLock.readLock().unlock(); }
        };

        // Writer — exclusive access
        Runnable writer = () -> {
            rwLock.writeLock().lock();
            try {
                System.out.println("  [Writer] Writing...");
                Thread.sleep(500);
                data[0] = "Updated by " + Thread.currentThread().getName();
                System.out.println("  [Writer] Done: " + data[0]);
            } catch (InterruptedException e) { Thread.currentThread().interrupt();
            } finally { rwLock.writeLock().unlock(); }
        };

        Thread r1 = new Thread(reader, "R1");
        Thread r2 = new Thread(reader, "R2");
        Thread r3 = new Thread(reader, "R3");
        Thread w1 = new Thread(writer, "W1");

        long start = System.currentTimeMillis();
        r1.start(); r2.start(); r3.start();
        Thread.sleep(50);
        w1.start();

        r1.join(); r2.join(); r3.join(); w1.join();
        System.out.println("  Time: " + (System.currentTimeMillis() - start) + "ms");
        System.out.println("  → All 3 readers ran in parallel, writer waited for exclusive access\n");
    }

    // ========================================================================
    // DEMO 6: StampedLock — optimistic reading
    // ========================================================================
    static void demo6_StampedLock() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 6: StampedLock — optimistic reads (fastest)");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * StampedLock (Java 8) adds OPTIMISTIC READ:
         *   1. tryOptimisticRead() → returns a stamp (no actual locking!)
         *   2. Read the data
         *   3. validate(stamp) → check if data was modified during read
         *   4. If valid → use the data (no lock was needed!)
         *   5. If invalid → fall back to read lock
         *
         * Optimistic read is LOCK-FREE — fastest possible read when writes are rare.
         *
         * WARNING: StampedLock is NOT reentrant! Do not call from recursive code.
         */

        StampedLock stampedLock = new StampedLock();
        double[] point = {1.0, 2.0};

        // Optimistic read
        Runnable optimisticReader = () -> {
            for (int i = 0; i < 5; i++) {
                long stamp = stampedLock.tryOptimisticRead(); // no lock acquired!
                double x = point[0], y = point[1]; // read shared data

                if (stampedLock.validate(stamp)) {
                    System.out.println("  [OptRead] (" + x + ", " + y + ") — optimistic read succeeded");
                } else {
                    // Data was modified during read — fall back to read lock
                    stamp = stampedLock.readLock();
                    try {
                        x = point[0]; y = point[1];
                        System.out.println("  [OptRead] (" + x + ", " + y + ") — fell back to read lock");
                    } finally { stampedLock.unlockRead(stamp); }
                }
            }
        };

        // Writer
        Runnable writer = () -> {
            for (int i = 0; i < 3; i++) {
                long stamp = stampedLock.writeLock();
                try {
                    point[0] += 1; point[1] += 1;
                    System.out.println("  [Writer] Updated to (" + point[0] + ", " + point[1] + ")");
                    Thread.sleep(100);
                } catch (InterruptedException e) { Thread.currentThread().interrupt();
                } finally { stampedLock.unlockWrite(stamp); }
            }
        };

        Thread r = new Thread(optimisticReader);
        Thread w = new Thread(writer);
        r.start(); w.start();
        r.join(); w.join();
        System.out.println();
    }

    // ========================================================================
    // MAIN
    // ========================================================================
    public static void main(String[] args) throws InterruptedException {
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║   PHASE 2F: LOCKS                                ║");
        System.out.println("╚═══════════════════════════════════════════════════╝\n");

        demo1_ReentrantLock();
        demo2_TryLock();
        demo3_FairLock();
        demo4_LockInterruptibly();
        demo5_ReadWriteLock();
        demo6_StampedLock();

        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("1. ReentrantLock = synchronized but with tryLock, timeout, fairness, interruptible");
        System.out.println("2. ALWAYS use try-finally with explicit locks (forget unlock = deadlock)");
        System.out.println("3. tryLock() — non-blocking, tryLock(timeout) — bounded wait");
        System.out.println("4. Fair lock (true) — FIFO order, prevents starvation, but slower");
        System.out.println("5. lockInterruptibly — can cancel a thread waiting for a lock");
        System.out.println("6. ReadWriteLock — multiple readers OR one writer (read-heavy apps)");
        System.out.println("7. StampedLock — optimistic reads (lock-free), fastest but not reentrant");
    }
}
