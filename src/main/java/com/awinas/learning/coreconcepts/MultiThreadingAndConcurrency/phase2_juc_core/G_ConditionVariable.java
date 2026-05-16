package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.phase2_juc_core;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.*;

/**
 * ============================================================================
 * G - CONDITION VARIABLES: Modern Replacement for wait/notify
 * ============================================================================
 *
 * PROBLEM WITH wait/notify:
 * ─────────────────────────
 * One object = one wait set. If producers wait on "not full" and consumers
 * wait on "not empty", they're ALL in the SAME wait set.
 * notify() may wake the WRONG thread.
 *
 * CONDITION VARIABLES — the fix:
 * ──────────────────────────────
 * With ReentrantLock, you can create MULTIPLE Conditions on the same lock.
 * Each Condition has its OWN wait set.
 *
 *   Lock lock = new ReentrantLock();
 *   Condition notFull  = lock.newCondition();  // producers wait here
 *   Condition notEmpty = lock.newCondition();  // consumers wait here
 *
 * MAPPING — wait/notify → Condition:
 * ───────────────────────────────────
 * ┌────────────────────┬────────────────────────┐
 * │ Object (Phase 1)   │ Condition (Phase 2)    │
 * ├────────────────────┼────────────────────────┤
 * │ synchronized(lock) │ lock.lock()            │
 * │ lock.wait()        │ condition.await()      │
 * │ lock.wait(ms)      │ condition.await(ms, u) │
 * │ lock.notify()      │ condition.signal()     │
 * │ lock.notifyAll()   │ condition.signalAll()  │
 * └────────────────────┴────────────────────────┘
 *
 * ADVANTAGES OVER wait/notify:
 * ────────────────────────────
 * 1. Multiple conditions per lock (separate wait sets)
 * 2. signal() wakes from the RIGHT condition (no wrong-thread problem)
 * 3. awaitNanos/awaitUntil for precise timing
 * 4. Works with ReentrantLock (tryLock, fairness, interruptible)
 */
public class G_ConditionVariable {

    // ========================================================================
    // DEMO 1: Basic await/signal — replacing wait/notify
    // ========================================================================
    static void demo1_BasicCondition() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 1: Basic Condition — await() / signal()");
        System.out.println("═══════════════════════════════════════════════════");

        ReentrantLock lock = new ReentrantLock();
        Condition dataReady = lock.newCondition();
        final String[] data = {null};

        Thread consumer = new Thread(() -> {
            lock.lock();
            try {
                while (data[0] == null) {
                    System.out.println("  [Consumer] No data. Calling await()...");
                    dataReady.await(); // releases lock, waits for signal
                    System.out.println("  [Consumer] Woke up! Re-checking condition...");
                }
                System.out.println("  [Consumer] Got data: " + data[0]);
            } catch (InterruptedException e) { Thread.currentThread().interrupt();
            } finally { lock.unlock(); }
        });

        Thread producer = new Thread(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            lock.lock();
            try {
                data[0] = "Hello from Producer!";
                System.out.println("  [Producer] Data set. Calling signal()...");
                dataReady.signal(); // wakes ONE thread waiting on this condition
            } finally { lock.unlock(); }
        });

        consumer.start();
        producer.start();
        consumer.join();
        producer.join();
        System.out.println();
    }

    // ========================================================================
    // DEMO 2: Multiple Conditions — Producer-Consumer (the proper way)
    // ========================================================================
    static void demo2_ProducerConsumerWithConditions() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 2: Producer-Consumer with TWO Conditions");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * TWO separate conditions:
         *   notFull  — producer waits here when buffer is full
         *   notEmpty — consumer waits here when buffer is empty
         *
         * signal() targets the RIGHT condition:
         *   After producing → signal notEmpty (wake consumer)
         *   After consuming → signal notFull (wake producer)
         *
         * No wrong-thread wakeup!
         */

        final int CAPACITY = 3;
        final Queue<String> buffer = new LinkedList<>();
        final ReentrantLock lock = new ReentrantLock();
        final Condition notFull = lock.newCondition();
        final Condition notEmpty = lock.newCondition();

        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 8; i++) {
                lock.lock();
                try {
                    while (buffer.size() == CAPACITY) {
                        System.out.println("    [Producer] Buffer FULL. Waiting on 'notFull' condition...");
                        notFull.await();
                    }
                    String item = "Item-" + i;
                    buffer.add(item);
                    System.out.println("    [Producer] Added " + item + " | Buffer: " + buffer);
                    notEmpty.signal(); // signal consumer: "buffer is not empty now"
                } catch (InterruptedException e) { Thread.currentThread().interrupt(); return;
                } finally { lock.unlock(); }

                try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 8; i++) {
                lock.lock();
                try {
                    while (buffer.isEmpty()) {
                        System.out.println("    [Consumer] Buffer EMPTY. Waiting on 'notEmpty' condition...");
                        notEmpty.await();
                    }
                    String item = buffer.poll();
                    System.out.println("    [Consumer] Took " + item + " | Buffer: " + buffer);
                    notFull.signal(); // signal producer: "buffer is not full now"
                } catch (InterruptedException e) { Thread.currentThread().interrupt(); return;
                } finally { lock.unlock(); }

                try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        }, "Consumer");

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println();
    }

    // ========================================================================
    // DEMO 3: await with timeout — don't wait forever
    // ========================================================================
    static void demo3_AwaitWithTimeout() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 3: await with timeout");
        System.out.println("═══════════════════════════════════════════════════");

        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Thread waiter = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("  [Waiter] Waiting max 2 seconds...");
                boolean signaled = condition.await(2, java.util.concurrent.TimeUnit.SECONDS);
                if (signaled) {
                    System.out.println("  [Waiter] Was signaled!");
                } else {
                    System.out.println("  [Waiter] Timeout! Nobody signaled me in 2 seconds.");
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt();
            } finally { lock.unlock(); }
        });

        waiter.start();
        waiter.join();
        System.out.println("  → await(timeout) returns false if timed out, true if signaled\n");
    }

    // ========================================================================
    // DEMO 4: Why multiple conditions matter — the wrong-thread problem
    // ========================================================================
    static void demo4_WhyMultipleConditions() {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 4: Why Multiple Conditions Solve the Wrong-Thread Problem");
        System.out.println("═══════════════════════════════════════════════════");

        System.out.println("""
          With wait/notify (ONE wait set):
          ─────────────────────────────────
          Producer waits: "buffer not full?"   ─┐
          Consumer waits: "buffer not empty?"  ─┤ SAME wait set!
                                                │
          notify() → wakes ONE random thread   ─┘
          → May wake Producer instead of Consumer → goes back to wait
          → Consumer NEVER wakes up → HANG
        
          With Condition (SEPARATE wait sets):
          ─────────────────────────────────────
          Producer waits on: notFull condition   → own wait set
          Consumer waits on: notEmpty condition  → own wait set
        
          After producing:  notEmpty.signal() → wakes Consumer (correct!)
          After consuming:  notFull.signal()  → wakes Producer (correct!)
        
          → Right thread is ALWAYS woken up. No wrong-thread problem.
        """);
    }

    // ========================================================================
    // DEMO 5: Comparison — old way vs new way
    // ========================================================================
    static void demo5_OldVsNew() {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 5: Old Way (wait/notify) vs New Way (Condition)");
        System.out.println("═══════════════════════════════════════════════════");

        System.out.println("""
          OLD WAY (Phase 1):                    NEW WAY (Phase 2):
          ──────────────────                    ──────────────────
          synchronized (lock) {                 lock.lock();
              while (!ready) {                  try {
                  lock.wait();                      while (!ready) {
              }                                         cond.await();
              // use data                           }
          }                                         // use data
                                                } finally { lock.unlock(); }
        
          notify on same object:                signal on specific condition:
          synchronized (lock) {                 lock.lock();
              ready = true;                     try {
              lock.notifyAll();                     ready = true;
          }                                         cond.signal();
                                                } finally { lock.unlock(); }
        
          Advantages of new way:
          ✓ Multiple conditions per lock
          ✓ signal() targets the right waiters
          ✓ await(timeout) returns boolean
          ✓ Works with tryLock, fairness, interruptible
        """);
    }

    // ========================================================================
    // MAIN
    // ========================================================================
    public static void main(String[] args) throws InterruptedException {
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║   PHASE 2G: CONDITION VARIABLES                  ║");
        System.out.println("╚═══════════════════════════════════════════════════╝\n");

        demo1_BasicCondition();
        demo2_ProducerConsumerWithConditions();
        demo3_AwaitWithTimeout();
        demo4_WhyMultipleConditions();
        demo5_OldVsNew();

        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("1. Condition = modern replacement for wait/notify");
        System.out.println("2. Multiple conditions per lock → separate wait sets");
        System.out.println("3. signal() wakes the RIGHT thread (no wrong-thread problem)");
        System.out.println("4. await() releases lock just like wait()");
        System.out.println("5. await(timeout) returns boolean (true=signaled, false=timeout)");
        System.out.println("6. ALWAYS use while loop around await() (same as wait)");
        System.out.println("7. ALWAYS use try-finally with lock/unlock");
        System.out.println("8. Phase 3 covers BlockingQueue — does all of this automatically!");
    }
}
