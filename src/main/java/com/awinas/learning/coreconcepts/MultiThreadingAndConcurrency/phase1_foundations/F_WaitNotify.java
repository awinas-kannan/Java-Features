package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.phase1_foundations;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ============================================================================
 * F - WAIT / NOTIFY: Inter-Thread Communication
 * ============================================================================
 *
 * THE PROBLEM:
 * ────────────
 * Threads often need to COORDINATE with each other:
 *   - Producer creates data, Consumer processes it
 *   - Thread-A finishes a task, Thread-B needs to know
 *
 * Busy-waiting (spinning in a loop checking a flag) WASTES CPU!
 *   while (!dataReady) { } // Burns CPU doing nothing useful
 *
 * SOLUTION: wait() and notify()
 * ─────────────────────────────
 * These methods are on java.lang.Object (because every object has a monitor/lock).
 *
 * ┌────────────────┬──────────────────────────────────────────────────────────┐
 * │ Method         │ What it does                                             │
 * ├────────────────┼──────────────────────────────────────────────────────────┤
 * │ wait()         │ Releases the lock and puts the thread to WAITING state.  │
 * │                │ Thread stays dormant until notify()/notifyAll() is called│
 * │                │ on the SAME object.                                      │
 * │                │                                                          │
 * │ notify()       │ Wakes up ONE waiting thread (chosen arbitrarily).        │
 * │                │ The awakened thread must re-acquire the lock before       │
 * │                │ continuing.                                              │
 * │                │                                                          │
 * │ notifyAll()    │ Wakes up ALL waiting threads. They compete for the lock. │
 * │                │ Use when multiple threads may be waiting.                 │
 * │                │                                                          │
 * │ wait(ms)       │ Like wait() but with timeout — wakes up after ms if not  │
 * │                │ notified.                                                │
 * └────────────────┴──────────────────────────────────────────────────────────┘
 *
 * CRITICAL RULES:
 * ───────────────
 * 1. MUST call wait()/notify() inside a synchronized block on the SAME object
 *    Otherwise → IllegalMonitorStateException
 *
 * 2. wait() RELEASES the lock (unlike sleep()!)
 *    This allows other threads to enter the synchronized block.
 *
 * 3. After waking from wait(), thread must RE-ACQUIRE the lock before continuing.
 *
 * 4. ALWAYS use wait() in a WHILE loop (not if):
 *      while (conditionNotMet) { lock.wait(); }
 *    Because of SPURIOUS WAKEUPS — threads can wake up without being notified!
 *
 * FLOW DIAGRAM:
 * ─────────────
 *   Producer:                    Consumer:
 *     synchronized(lock) {         synchronized(lock) {
 *       // produce data              while (noData) {
 *       lock.notify();        ←───     lock.wait();  // releases lock!
 *     }                              }
 *                                    // consume data
 *                                  }
 *
 * WAIT vs SLEEP:
 * ──────────────
 * ┌────────────────┬──────────────────────┬──────────────────────┐
 * │                │ wait()               │ sleep()              │
 * ├────────────────┼──────────────────────┼──────────────────────┤
 * │ Releases lock? │ YES                  │ NO                   │
 * │ On which class?│ Object               │ Thread               │
 * │ Needs sync?    │ YES (mandatory)      │ NO                   │
 * │ Wakes by       │ notify()/notifyAll() │ timeout              │
 * │ Purpose        │ Thread coordination  │ Pause execution      │
 * └────────────────┴──────────────────────┴──────────────────────┘
 */
public class F_WaitNotify {

    // ========================================================================
    // DEMO 1: Basic wait/notify — simple signal between two threads
    // ========================================================================
    static void demo1_BasicWaitNotify() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 1: Basic wait() / notify() — Simple Signal");
        System.out.println("═══════════════════════════════════════════════════");

        final Object signal = new Object();

        Thread waiter = new Thread(() -> {
            synchronized (signal) {
                System.out.println("  [Waiter] Waiting for signal...");
                try {
                    signal.wait(); // releases the lock, goes to WAITING state
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("  [Waiter] Got the signal! Continuing...");
            }
        }, "Waiter");

        Thread notifier = new Thread(() -> {
            try { Thread.sleep(5000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            synchronized (signal) {
                System.out.println("  [Notifier] Sending signal!");
                signal.notify(); // wakes up ONE waiting thread
            }
        }, "Notifier");

        waiter.start();
        notifier.start();
        waiter.join();
        notifier.join();

        System.out.println("  → Waiter was dormant (no CPU wasted), woke up on notify\n");
    }

    // ========================================================================
    // DEMO 2: Producer-Consumer with wait/notify (the classic pattern)
    // ========================================================================
    static void demo2_ProducerConsumer() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 2: Producer-Consumer Pattern");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * Scenario: A kitchen (producer) makes dishes, a waiter (consumer) serves them.
         * The counter between them has limited space (bounded buffer, capacity=3).
         *
         * Producer: if counter is FULL → wait. After producing → notify consumer.
         * Consumer: if counter is EMPTY → wait. After consuming → notify producer.
         */

        final int CAPACITY = 3;
        final Queue<String> buffer = new LinkedList<>();
        final Object lock = new Object();

        System.out.println("    ┌─────────────────────────────────────────────────────────┐");
        System.out.println("    │ SETUP: Kitchen(Producer) → Counter(Buffer, max=3) → Waiter(Consumer) │");
        System.out.println("    └─────────────────────────────────────────────────────────┘");
        System.out.println();

        // Producer — the kitchen
        Thread producer = new Thread(() -> {
            String[] dishes = {"Dish-1", "Dish-2", "Dish-3", "Dish-4", "Dish-5", "Dish-6", "Dish-7", "Dish-8", "Dish-9", "Dish-10"};
            for (int idx = 0; idx < dishes.length; idx++) {
                String dish = dishes[idx];
                synchronized (lock) {
                    System.out.println("    [Kitchen] Acquired lock. Checking buffer space...");
                    System.out.println("    [Kitchen] Buffer size=" + buffer.size() + ", capacity=" + CAPACITY);

                    while (buffer.size() == CAPACITY) {
                        System.out.println("    [Kitchen] ⛔ Buffer FULL! Calling wait() → releases lock, goes to WAITING");
                        try { lock.wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
                        System.out.println("    [Kitchen] ✓ Woke up from wait()! Re-acquired lock. Re-checking condition...");
                        System.out.println("    [Kitchen] Buffer size=" + buffer.size() + ", capacity=" + CAPACITY);
                    }

                    buffer.add(dish);
                    System.out.println("    [Kitchen] ✚ PRODUCED: " + dish + " | Buffer now: " + buffer + " (size=" + buffer.size() + ")");
                    System.out.println("    [Kitchen] Calling notifyAll() → wakes up waiting Consumer");
                    lock.notifyAll();
                    System.out.println("    [Kitchen] Releasing lock (exiting synchronized block)");
                }
                try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                System.out.println("    ────────────────────────────────────────────────");
            }
            System.out.println("    [Kitchen] 🏁 All dishes produced! Kitchen closing.");
        }, "Producer");

        // Consumer — the waiter
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                synchronized (lock) {
                    System.out.println("    [Waiter]  Acquired lock. Checking if buffer has items...");
                    System.out.println("    [Waiter]  Buffer size=" + buffer.size());

                    while (buffer.isEmpty()) {
                        System.out.println("    [Waiter]  ⛔ Buffer EMPTY! Calling wait() → releases lock, goes to WAITING");
                        try { lock.wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
                        System.out.println("    [Waiter]  ✓ Woke up from wait()! Re-acquired lock. Re-checking condition...");
                        System.out.println("    [Waiter]  Buffer size=" + buffer.size());
                    }

                    String dish = buffer.poll();
                    System.out.println("    [Waiter]  ✔ CONSUMED: " + dish + " | Buffer now: " + buffer + " (size=" + buffer.size() + ")");
                    System.out.println("    [Waiter]  Calling notifyAll() → wakes up waiting Producer");
                    lock.notifyAll();
                    System.out.println("    [Waiter]  Releasing lock (exiting synchronized block)");
                }
                try { Thread.sleep(400); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                System.out.println("    ────────────────────────────────────────────────");
            }
            System.out.println("    [Waiter]  🏁 All dishes served! Waiter done.");
        }, "Consumer");

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println();
    }

    // ========================================================================
    // DEMO 3: Why use WHILE loop (not IF) — spurious wakeups
    // ========================================================================
    static void demo3_WhyWhileNotIf() {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 3: Why while() Not if() — Spurious Wakeups");
        System.out.println("═══════════════════════════════════════════════════");

        System.out.println("""
          ❌ WRONG (using if):
             synchronized (lock) {
                 if (buffer.isEmpty()) {
                     lock.wait();
                 }
                 // DANGER: buffer might STILL be empty here!
                 // Another thread could have consumed the item
                 // between wakeup and re-acquiring the lock.
                 buffer.poll();  // → NullPointerException!
             }
        
          ✅ CORRECT (using while):
             synchronized (lock) {
                 while (buffer.isEmpty()) {
                     lock.wait();
                 }
                 // SAFE: condition is re-checked after every wakeup
                 buffer.poll();  // → guaranteed non-null
             }
        
          WHY?
          1. SPURIOUS WAKEUPS: JVM may wake a thread without notify() being called
          2. STOLEN WAKEUP: Another thread may have already consumed the data
             between your wakeup and lock re-acquisition
          3. The while loop re-checks the condition, handling both cases
        """);
    }

    // ========================================================================
    // DEMO 4: notify() vs notifyAll()
    // ========================================================================
    static void demo4_NotifyVsNotifyAll() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 4: notify() vs notifyAll()");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * notify()    → wakes ONE arbitrary waiting thread
         * notifyAll() → wakes ALL waiting threads (they compete for the lock)
         *
         * When to use which:
         *   notify()    — when ANY one waiter can handle the signal
         *                 (e.g., 10 identical consumers, any one can process)
         *   notifyAll() — when waiters have DIFFERENT conditions
         *                 (e.g., producers wait on "not full", consumers wait on "not empty")
         *                 If you use notify(), the wrong thread might wake up and go
         *                 back to waiting, while the right thread stays asleep!
         *
         * RULE OF THUMB: When in doubt, use notifyAll(). It's safer.
         */

        final Object lock = new Object();
        final boolean[] dataReady = {false};

        Runnable waiterTask = () -> {
            synchronized (lock) {
                while (!dataReady[0]) {
                    try {
                        System.out.println("    [" + Thread.currentThread().getName() + "] Waiting...");
                        lock.wait();
                    } catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
                }
                System.out.println("    [" + Thread.currentThread().getName() + "] Woke up and proceeding!");
            }
        };

        Thread w1 = new Thread(waiterTask, "Waiter-1");
        Thread w2 = new Thread(waiterTask, "Waiter-2");
        Thread w3 = new Thread(waiterTask, "Waiter-3");

        w1.start(); w2.start(); w3.start();
        Thread.sleep(500); // let all waiters start waiting

        // Using notifyAll — all 3 will wake up
        synchronized (lock) {
            dataReady[0] = true;
            System.out.println("    [Main] Calling notifyAll()...");
            lock.notifyAll();
        }

        w1.join(); w2.join(); w3.join();
        System.out.println("  → All 3 waiters woke up with notifyAll()\n");
    }

    // ========================================================================
    // DEMO 5: wait() releases the lock (unlike sleep!)
    // ========================================================================
    static void demo5_WaitReleasesLock() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 5: wait() Releases Lock (sleep() Doesn't!)");
        System.out.println("═══════════════════════════════════════════════════");

        final Object lock = new Object();

        Thread holder = new Thread(() -> {
            synchronized (lock) {
                System.out.println("    [Holder] Acquired lock, calling wait()...");
                try {
                    lock.wait(2000); // releases lock for up to 2 seconds
                } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                System.out.println("    [Holder] Resumed after wait");
            }
        });

        Thread checker = new Thread(() -> {
            try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            System.out.println("    [Checker] Trying to acquire lock...");
            synchronized (lock) {
                System.out.println("    [Checker] Got the lock! (wait() released it)");
                lock.notify(); // wake up holder
            }
        });

        holder.start();
        checker.start();
        holder.join();
        checker.join();

        System.out.println("  → If holder used sleep() instead of wait(),");
        System.out.println("    checker would be BLOCKED for the entire sleep duration.\n");
    }

    // ========================================================================
    // MAIN — Run all demos
    // ========================================================================
    public static void main(String[] args) throws InterruptedException {
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║   PHASE 1F: WAIT / NOTIFY                        ║");
        System.out.println("╚═══════════════════════════════════════════════════╝\n");

        demo1_BasicWaitNotify();
        demo2_ProducerConsumer();
        demo3_WhyWhileNotIf();
        demo4_NotifyVsNotifyAll();
        demo5_WaitReleasesLock();

        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("1. wait() — releases lock, thread sleeps until notified");
        System.out.println("2. notify() — wakes ONE waiting thread");
        System.out.println("3. notifyAll() — wakes ALL waiting threads (prefer this)");
        System.out.println("4. MUST call inside synchronized block on the SAME object");
        System.out.println("5. ALWAYS use while loop around wait() (spurious wakeups)");
        System.out.println("6. wait() RELEASES the lock; sleep() does NOT");
        System.out.println("7. Producer-Consumer is the classic use case");
        System.out.println("8. Phase 3 shows BlockingQueue — a MUCH easier alternative!");
    }
}
