package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.phase1_foundations;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

/**
 * ============================================================================
 * G - DEADLOCK: When Threads Wait Forever
 * ============================================================================
 *
 * WHAT IS DEADLOCK?
 * ─────────────────
 * Deadlock occurs when two or more threads are BLOCKED FOREVER,
 * each waiting for a lock held by the other.
 *
 * Classic scenario:
 *   Thread-A holds Lock-1, waiting for Lock-2
 *   Thread-B holds Lock-2, waiting for Lock-1
 *   → Neither can proceed. Program hangs indefinitely.
 *
 * REAL-WORLD ANALOGY:
 * ───────────────────
 * Two cars meet at a narrow bridge from opposite sides.
 * Car A: "I'll cross after you move back."
 * Car B: "I'll cross after YOU move back."
 * → Both wait forever.
 *
 * FOUR CONDITIONS FOR DEADLOCK (ALL must be true):
 * ────────────────────────────────────────────────
 * 1. MUTUAL EXCLUSION   — Only one thread can hold a lock at a time
 * 2. HOLD AND WAIT      — Thread holds one lock while waiting for another
 * 3. NO PREEMPTION      — Locks can't be forcibly taken from a thread
 * 4. CIRCULAR WAIT      — A circular chain: A waits for B, B waits for A
 *
 * Break ANY ONE condition → no deadlock!
 *
 * PREVENTION STRATEGIES:
 * ──────────────────────
 * ┌─────────────────────┬────────────────────────────────────────────────────┐
 * │ Strategy            │ How it works                                       │
 * ├─────────────────────┼────────────────────────────────────────────────────┤
 * │ Lock Ordering       │ Always acquire locks in the SAME global order.     │
 * │                     │ (Breaks circular wait)                             │
 * │                     │                                                    │
 * │ Lock Timeout        │ Use tryLock(timeout) — give up if can't acquire.   │
 * │                     │ (Breaks hold-and-wait)                             │
 * │                     │                                                    │
 * │ Single Lock         │ Use one lock for both resources.                   │
 * │                     │ (Breaks hold-and-wait, but reduces concurrency)    │
 * │                     │                                                    │
 * │ Lock-Free Algorithms│ Use Atomic classes, CAS operations.                │
 * │                     │ (Breaks mutual exclusion)                          │
 * │                     │                                                    │
 * │ Deadlock Detection  │ Monitor thread states, detect cycles, recover.     │
 * │                     │ (Runtime detection, not prevention)                │
 * └─────────────────────┴────────────────────────────────────────────────────┘
 *
 * HOW TO DETECT DEADLOCKS:
 * ────────────────────────
 * 1. jstack <pid>          — prints thread dump, shows deadlock if detected
 * 2. jconsole / jvisualvm  — GUI tools, "Detect Deadlock" button
 * 3. ThreadMXBean          — programmatic detection (shown in Demo 4)
 */
public class G_Deadlock {

    // ========================================================================
    // DEMO 1: Classic deadlock — two threads, two locks, opposite order
    // ========================================================================
    static void demo1_ClassicDeadlock() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 1: Classic Deadlock (will timeout after 3s)");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         *   Thread-A: synchronized(lock1) { synchronized(lock2) { ... } }
         *   Thread-B: synchronized(lock2) { synchronized(lock1) { ... } }
         *
         *   Timeline:
         *   Thread-A acquires lock1  ─┐
         *   Thread-B acquires lock2  ─┤  Both hold one lock
         *   Thread-A waits for lock2 ─┤  Both wait for the other
         *   Thread-B waits for lock1 ─┘  → DEADLOCK!
         */

        final Object lock1 = new Object();
        final Object lock2 = new Object();

        Thread threadA = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("  [Thread-A] Holding lock1, wanting lock2...");
                try { Thread.sleep(100); } catch (InterruptedException e) { return; }
                synchronized (lock2) {
                    System.out.println("  [Thread-A] Got both locks! (you won't see this)");
                }
            }
        }, "Thread-A");

        Thread threadB = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("  [Thread-B] Holding lock2, wanting lock1...");
                try { Thread.sleep(100); } catch (InterruptedException e) { return; }
                synchronized (lock1) {
                    System.out.println("  [Thread-B] Got both locks! (you won't see this)");
                }
            }
        }, "Thread-B");

        threadA.start();
        threadB.start();

        // Wait max 3 seconds, then detect deadlock
        threadA.join(3000);
        threadB.join(3000);

        if (threadA.isAlive() && threadB.isAlive()) {
            System.out.println("  ⚠ DEADLOCK DETECTED! Both threads are stuck.");
            System.out.println("    Thread-A state: " + threadA.getState());
            System.out.println("    Thread-B state: " + threadB.getState());

            // Force stop for demo (in production, this is a serious bug to fix)
            threadA.interrupt();
            threadB.interrupt();
        }
        System.out.println();
    }

    // ========================================================================
    // DEMO 2: FIX — Lock ordering (always acquire in same order)
    // ========================================================================
    static void demo2_FixWithLockOrdering() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 2: FIX — Consistent Lock Ordering");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * RULE: Always acquire locks in the SAME ORDER.
         * If everyone acquires lock1 first, then lock2 → no circular wait → no deadlock.
         *
         * Before (deadlock):
         *   A: lock1 → lock2
         *   B: lock2 → lock1   ← different order!
         *
         * After (safe):
         *   A: lock1 → lock2
         *   B: lock1 → lock2   ← same order!
         */

        final Object lock1 = new Object();
        final Object lock2 = new Object();

        Thread threadA = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("  [Thread-A] Holding lock1, wanting lock2...");
                try { Thread.sleep(100); } catch (InterruptedException e) { return; }
                synchronized (lock2) {
                    System.out.println("  [Thread-A] Got both locks!");
                }
            }
        }, "Thread-A");

        Thread threadB = new Thread(() -> {
            // SAME ORDER as Thread-A: lock1 first, then lock2
            synchronized (lock1) {
                System.out.println("  [Thread-B] Holding lock1, wanting lock2...");
                try { Thread.sleep(100); } catch (InterruptedException e) { return; }
                synchronized (lock2) {
                    System.out.println("  [Thread-B] Got both locks!");
                }
            }
        }, "Thread-B");

        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();

        System.out.println("  → No deadlock! Both threads completed.");
        System.out.println("    Thread-B waited for Thread-A to release lock1 first.\n");
    }

    // ========================================================================
    // DEMO 3: FIX — tryLock with timeout (using ReentrantLock)
    // ========================================================================
    static void demo3_FixWithTryLock() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 3: FIX — tryLock() with Timeout");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * ReentrantLock.tryLock(timeout, unit):
         *   - Tries to acquire the lock
         *   - If can't get it within timeout → returns false (instead of blocking forever)
         *   - The thread can then release its held locks and retry or give up
         *
         * This breaks the "hold and wait" deadlock condition.
         */

        ReentrantLock lock1 = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();

        Runnable tryBothLocks = () -> {
            String name = Thread.currentThread().getName();
            boolean success = false;

            for (int attempt = 1; attempt <= 3 && !success; attempt++) {
                try {
                    boolean gotLock1 = lock1.tryLock(500, TimeUnit.MILLISECONDS);
                    if (gotLock1) {
                        try {
                            System.out.println("    [" + name + "] Got lock1 (attempt " + attempt + ")");
                            Thread.sleep(50); // simulate work

                            boolean gotLock2 = lock2.tryLock(500, TimeUnit.MILLISECONDS);
                            if (gotLock2) {
                                try {
                                    System.out.println("    [" + name + "] Got both locks! Doing work...");
                                    success = true;
                                } finally {
                                    lock2.unlock();
                                }
                            } else {
                                System.out.println("    [" + name + "] Couldn't get lock2, backing off...");
                            }
                        } finally {
                            lock1.unlock();
                        }
                    } else {
                        System.out.println("    [" + name + "] Couldn't get lock1, retrying...");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                if (!success) {
                    try { Thread.sleep((long)(Math.random() * 100)); }
                    catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
                }
            }

            if (!success) {
                System.out.println("    [" + name + "] Gave up after 3 attempts.");
            }
        };

        Thread t1 = new Thread(tryBothLocks, "Thread-A");
        Thread t2 = new Thread(tryBothLocks, "Thread-B");

        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("  → No deadlock! tryLock prevents infinite waiting.\n");
    }

    // ========================================================================
    // DEMO 4: Programmatic deadlock detection with ThreadMXBean
    // ========================================================================
    static void demo4_DeadlockDetection() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 4: Programmatic Deadlock Detection");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * java.lang.management.ThreadMXBean can detect deadlocks:
         *   - findDeadlockedThreads() → returns thread IDs involved in deadlock
         *   - getThreadInfo(ids) → get details about each thread
         *
         * In production, you might run this check periodically or on-demand.
         */

        final Object lock1 = new Object();
        final Object lock2 = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                try { Thread.sleep(100); } catch (InterruptedException e) { return; }
                synchronized (lock2) { }
            }
        }, "DeadlockVictim-1");

        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                try { Thread.sleep(100); } catch (InterruptedException e) { return; }
                synchronized (lock1) { }
            }
        }, "DeadlockVictim-2");

        t1.start(); t2.start();
        Thread.sleep(500); // let deadlock form

        // Detect using ThreadMXBean
        java.lang.management.ThreadMXBean tmx =
                java.lang.management.ManagementFactory.getThreadMXBean();
        long[] deadlockedIds = tmx.findDeadlockedThreads();

        if (deadlockedIds != null) {
            System.out.println("  ⚠ DEADLOCK FOUND! Threads involved:");
            java.lang.management.ThreadInfo[] infos = tmx.getThreadInfo(deadlockedIds, true, true);
            for (java.lang.management.ThreadInfo info : infos) {
                System.out.println("    - " + info.getThreadName()
                        + " (state: " + info.getThreadState()
                        + ", waiting for: " + info.getLockName()
                        + ", held by: " + info.getLockOwnerName() + ")");
            }
        } else {
            System.out.println("  No deadlock detected.");
        }

        // Cleanup
        t1.interrupt(); t2.interrupt();
        t1.join(1000); t2.join(1000);
        System.out.println();
    }

    // ========================================================================
    // DEMO 5: Real-world deadlock scenario — bank transfer
    // ========================================================================
    static void demo5_BankTransferDeadlock() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 5: Bank Transfer — Deadlock & Fix");
        System.out.println("═══════════════════════════════════════════════════");

        class BankAccount {
            final int id;
            private int balance;

            BankAccount(int id, int balance) {
                this.id = id;
                this.balance = balance;
            }

            int getBalance() { return balance; }

            // UNSAFE: Can deadlock if A→B and B→A transfer simultaneously
            void unsafeTransfer(BankAccount to, int amount) {
                synchronized (this) {
                    synchronized (to) {
                        if (this.balance >= amount) {
                            this.balance -= amount;
                            to.balance += amount;
                        }
                    }
                }
            }

            // SAFE: Always lock the account with LOWER ID first
            void safeTransfer(BankAccount to, int amount) {
                BankAccount first = this.id < to.id ? this : to;
                BankAccount second = this.id < to.id ? to : this;

                synchronized (first) {
                    synchronized (second) {
                        if (this.balance >= amount) {
                            this.balance -= amount;
                            to.balance += amount;
                        }
                    }
                }
            }
        }

        BankAccount alice = new BankAccount(1, 1000);
        BankAccount bob = new BankAccount(2, 1000);

        System.out.println("  [Safe Transfer — using lock ordering by account ID]");
        System.out.println("  Before: Alice=$" + alice.getBalance() + ", Bob=$" + bob.getBalance());

        // 1000 simultaneous transfers in both directions — no deadlock!
        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            final int idx = i;
            threads[i] = new Thread(() -> {
                if (idx % 2 == 0) {
                    alice.safeTransfer(bob, 10);
                } else {
                    bob.safeTransfer(alice, 10);
                }
            });
        }

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        System.out.println("  After:  Alice=$" + alice.getBalance() + ", Bob=$" + bob.getBalance());
        System.out.println("  Total:  $" + (alice.getBalance() + bob.getBalance()) + " (should be $2000)");
        System.out.println("  → No deadlock! Lock ordering by account ID prevents circular wait.\n");
    }

    // ========================================================================
    // DEMO 6: Livelock — threads are active but make no progress
    // ========================================================================
    static void demo6_Livelock() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 6: Livelock vs Deadlock (Theory + Example)");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * DEADLOCK:  Threads are BLOCKED (sleeping, doing nothing)
         * LIVELOCK:  Threads are ACTIVE (running, but making no progress)
         *
         * Analogy: Two people in a hallway keep stepping aside for each other.
         * Both keep moving but neither gets past.
         *
         * STARVATION: A thread can never acquire a resource because other threads
         *            always take it first (unfair scheduling).
         */

        class Hallway {
            volatile boolean personAMovedAside = false;
            volatile boolean personBMovedAside = false;
        }

        Hallway hallway = new Hallway();
        final int[] attempts = {0};

        Thread personA = new Thread(() -> {
            while (attempts[0] < 5) {
                hallway.personAMovedAside = true;
                System.out.println("    [Person A] Steps aside");
                while (hallway.personBMovedAside) {
                    // Keep waiting politely — but B is doing the same!
                    break;
                }
                hallway.personAMovedAside = false;
                attempts[0]++;
                try { Thread.sleep(100); } catch (InterruptedException e) { return; }
            }
        });

        Thread personB = new Thread(() -> {
            while (attempts[0] < 5) {
                hallway.personBMovedAside = true;
                System.out.println("    [Person B] Steps aside");
                while (hallway.personAMovedAside) {
                    break;
                }
                hallway.personBMovedAside = false;
                try { Thread.sleep(100); } catch (InterruptedException e) { return; }
            }
        });

        personA.start(); personB.start();
        personA.join(3000); personB.join(3000);
        personA.interrupt(); personB.interrupt();

        System.out.println("  → In a real livelock, this would go on forever.");
        System.out.println("    Fix: Add random backoff so one thread proceeds first.\n");
    }

    // ========================================================================
    // MAIN — Run all demos
    // ========================================================================
    public static void main(String[] args) throws InterruptedException {
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║   PHASE 1G: DEADLOCK                             ║");
        System.out.println("╚═══════════════════════════════════════════════════╝\n");

        demo1_ClassicDeadlock();
        demo2_FixWithLockOrdering();
        demo3_FixWithTryLock();
        demo4_DeadlockDetection();
        demo5_BankTransferDeadlock();
        demo6_Livelock();

        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("1. Deadlock = threads blocked forever, each waiting for the other's lock");
        System.out.println("2. Four conditions: mutual exclusion, hold-and-wait, no preemption, circular wait");
        System.out.println("3. FIX #1: Lock ordering — always acquire locks in the same global order");
        System.out.println("4. FIX #2: tryLock with timeout — give up and retry if lock unavailable");
        System.out.println("5. FIX #3: Single lock — use one lock for related resources");
        System.out.println("6. Use ThreadMXBean or jstack to DETECT deadlocks");
        System.out.println("7. Livelock = threads active but no progress (add random backoff to fix)");
        System.out.println("8. Starvation = a thread never gets the resource (use fair locks)");
    }
}
