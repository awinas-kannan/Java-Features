package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.phase1_foundations;

/**
 * ============================================================================
 * D - SYNCHRONIZED: The First Line of Defense Against Race Conditions
 * ============================================================================
 *
 * WHAT IS SYNCHRONIZED?
 * ─────────────────────
 * The `synchronized` keyword provides MUTUAL EXCLUSION (mutex):
 *   → Only ONE thread can execute a synchronized block/method at a time.
 *   → Other threads trying to enter the same synchronized section BLOCK and wait.
 *
 * HOW IT WORKS — INTRINSIC LOCKS (MONITORS):
 * ───────────────────────────────────────────
 * Every Java object has an INTRINSIC LOCK (also called a "monitor").
 * When a thread enters a synchronized block:
 *   1. It ACQUIRES the object's lock
 *   2. Executes the code
 *   3. RELEASES the lock (even if exception is thrown!)
 *
 * Other threads trying to acquire the SAME lock → BLOCKED state.
 *
 * TWO FORMS:
 * ──────────
 * 1. Synchronized METHOD     → locks on `this` (instance) or `Class` object (static)
 * 2. Synchronized BLOCK      → locks on ANY object you specify (more flexible)
 *
 * LOCK TYPES:
 * ───────────
 * ┌──────────────────────────────────────────────────────────────────────┐
 * │ synchronized void method()          → locks on `this` (instance)   │
 * │ static synchronized void method()   → locks on `ClassName.class`   │
 * │ synchronized(this) { }              → locks on `this` (instance)   │
 * │ synchronized(someObj) { }           → locks on someObj             │
 * │ synchronized(ClassName.class) { }   → locks on class object        │
 * └──────────────────────────────────────────────────────────────────────┘
 *
 * CRITICAL INSIGHT:
 *   synchronized methods on the SAME object share the SAME lock (this).
 *   If Thread-A is in syncMethodA(), Thread-B CANNOT enter syncMethodB()
 *   on the SAME object — they share the `this` lock!
 *
 * SYNCHRONIZED GUARANTEES:
 * ────────────────────────
 * 1. ATOMICITY    — The block executes as an indivisible unit
 * 2. VISIBILITY   — Changes made inside are visible to the next thread
 *                   that acquires the same lock (happens-before)
 * 3. ORDERING     — Instructions inside are not reordered across the lock boundary
 */
public class D_Synchronized {

    // ========================================================================
    // DEMO 1: Synchronized method — fixing the counter race condition
    // ========================================================================
    static void demo1_SynchronizedMethod() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 1: Synchronized Method — Fixing counter++");
        System.out.println("═══════════════════════════════════════════════════");

        class Counter {
            private int count = 0;

            // synchronized on `this` — only one thread can increment at a time
            synchronized void increment() {
                count++; // Now safe: READ → MODIFY → WRITE is atomic
            }

            synchronized int getCount() {
                return count;
            }
        }

        Counter counter = new Counter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) counter.increment();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) counter.increment();
        });

        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("  Expected: 200000");
        System.out.println("  Actual:   " + counter.getCount());
        System.out.println("  → ALWAYS 200000 with synchronized! No lost updates.\n");
    }

    // ========================================================================
    // DEMO 2: Synchronized block — finer-grained locking
    // ========================================================================
    static void demo2_SynchronizedBlock() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 2: Synchronized Block — Lock only what's needed");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * WHY use synchronized BLOCK over METHOD?
         *
         * Synchronized METHOD locks the ENTIRE method — even non-critical code.
         * Synchronized BLOCK lets you lock ONLY the critical section.
         *
         * Less time holding the lock = better concurrency performance.
         *
         *   // BAD: Locks during sleep too (unnecessary)
         *   synchronized void process() {
         *       expensiveComputation();  // doesn't need lock
         *       counter++;               // needs lock
         *       logResult();             // doesn't need lock
         *   }
         *
         *   // GOOD: Only lock the critical section
         *   void process() {
         *       expensiveComputation();        // runs concurrently
         *       synchronized(this) { counter++; }  // only this is locked
         *       logResult();                   // runs concurrently
         *   }
         */

        class BetterCounter {
            private int count = 0;
            private final Object lock = new Object();

            void incrementWithWork() {
                // Simulated non-critical work (can run in parallel)
                double x = Math.sqrt(Math.random());

                // ONLY lock the critical section
                synchronized (lock) {
                    count++;
                }

                // More non-critical work
                double y = x * 2;
            }

            int getCount() {
                synchronized (lock) {
                    return count;
                }
            }
        }

        BetterCounter counter = new BetterCounter();
        long start = System.currentTimeMillis();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) counter.incrementWithWork();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) counter.incrementWithWork();
        });

        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("  Result: " + counter.getCount() + " (expected 200000)");
        System.out.println("  Time: " + (System.currentTimeMillis() - start) + "ms");
        System.out.println("  → Synchronized block is more efficient than synchronized method\n");
    }

    // ========================================================================
    // DEMO 3: this lock vs Class lock — instance vs static
    // ========================================================================
    static void demo3_InstanceVsClassLock() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 3: Instance Lock vs Class Lock");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * INSTANCE LOCK (synchronized on `this`):
         *   - Each object has its OWN lock
         *   - Thread-A locks obj1, Thread-B can still access obj2
         *   - Two threads on SAME object → one blocks
         *   - Two threads on DIFFERENT objects → both run simultaneously
         *
         * CLASS LOCK (synchronized on `ClassName.class` or static synchronized):
         *   - There's only ONE class object per class in JVM
         *   - ALL threads block, regardless of which instance they use
         *   - Use for protecting static shared state
         */

        class Printer {
            // WAY 1: synchronized METHOD — locks on `this` implicitly
            synchronized void printWithSyncMethod(String msg) {
                System.out.print("    [" + Thread.currentThread().getName() + "] ");
                for (char c : msg.toCharArray()) {
                    System.out.print(c);
                    try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                }
                System.out.println();
            }

            // WAY 2: synchronized(this) BLOCK — same as above, but explicit
            void printWithSyncThis(String msg) {
                synchronized (this) {
                    System.out.print("    [" + Thread.currentThread().getName() + "] ");
                    for (char c : msg.toCharArray()) {
                        System.out.print(c);
                        try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    }
                    System.out.println();
                }
            }

            // WAY 3: synchronized(ClassName.class) — CLASS-LEVEL lock
            void printWithClassLock(String msg) {
                synchronized (Printer.class) {
                    System.out.print("    [" + Thread.currentThread().getName() + "] ");
                    for (char c : msg.toCharArray()) {
                        System.out.print(c);
                        try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    }
                    System.out.println();
                }
            }
        }

        // --- PART A: synchronized method (locks on `this`) ---
        // SAME object → threads are serialized (one waits for other)
        System.out.println("  [A] synchronized method — Same object (serialized):");
        Printer samePrinter = new Printer();
        Thread t1 = new Thread(() -> samePrinter.printWithSyncMethod("HELLO"), "T1");
        Thread t2 = new Thread(() -> samePrinter.printWithSyncMethod("WORLD"), "T2");
        t1.start(); t2.start();
        t1.join(); t2.join();
        System.out.println();

        // DIFFERENT objects → threads run in parallel (different `this` locks!)
        System.out.println("  [A] synchronized method — Different objects (parallel — different locks!):");
        Printer printerA1 = new Printer();
        Printer printerA2 = new Printer();
        Thread t3 = new Thread(() -> printerA1.printWithSyncMethod("AAAA"), "T3");
        Thread t4 = new Thread(() -> printerA2.printWithSyncMethod("BBBB"), "T4");
        t3.start(); t4.start();
        t3.join(); t4.join();
        System.out.println("  → INTERLEAVED! Different objects = different locks.\n");

        // --- PART B: synchronized(this) block — behaves exactly like synchronized method ---
        System.out.println("  [B] synchronized(this) block — Same object (serialized):");
        Thread t5 = new Thread(() -> samePrinter.printWithSyncThis("HELLO"), "T5");
        Thread t6 = new Thread(() -> samePrinter.printWithSyncThis("WORLD"), "T6");
        t5.start(); t6.start();
        t5.join(); t6.join();
        System.out.println("  → Same behavior as synchronized method. Both lock on `this`.\n");

        // --- PART C: synchronized(ClassName.class) — class-level lock ---
        // Even DIFFERENT objects are serialized — because there's only ONE Printer.class
        System.out.println("  [C] synchronized(Printer.class) — Different objects (STILL serialized!):");
        Printer printerC1 = new Printer();
        Printer printerC2 = new Printer();
        Thread t7 = new Thread(() -> printerC1.printWithClassLock("XXXX"), "T7");
        Thread t8 = new Thread(() -> printerC2.printWithClassLock("YYYY"), "T8");
        t7.start(); t8.start();
        t7.join(); t8.join();
        System.out.println("  → NOT interleaved! Class lock = one lock for ALL instances.\n");
    }

    // ========================================================================
    // DEMO 4: Static synchronized — class-level lock
    // ========================================================================
    static int staticCounter = 0;

    static synchronized void staticIncrement() {
        staticCounter++;
    }

    static void demo4_StaticSynchronized() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 4: Static Synchronized — Class-Level Lock");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * static synchronized void method()
         * is equivalent to:
         * void method() { synchronized(ClassName.class) { ... } }
         *
         * There's only ONE ClassName.class object → global lock for all instances.
         * Use when protecting STATIC shared state.
         */

        staticCounter = 0;

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) staticIncrement();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) staticIncrement();
        });

        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("  Static counter: " + staticCounter + " (expected 200000)");
        System.out.println("  → static synchronized locks on D_Synchronized.class\n");
    }

    // ========================================================================
    // DEMO 5: Lock on custom object — most flexible approach
    // ========================================================================
    static void demo5_CustomLockObject() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 5: Custom Lock Objects — Independent Locks");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * Problem with `synchronized(this)`:
         *   ALL synchronized methods/blocks on the same object share ONE lock.
         *   If methodA() and methodB() access DIFFERENT data,
         *   they still block each other unnecessarily!
         *
         * Solution: Use separate lock objects for separate resources.
         */

        class TwoResources {
            private final Object lockA = new Object();
            private final Object lockB = new Object();
            private int resourceA = 0;
            private int resourceB = 0;

            void incrementA() {
                synchronized (lockA) {
                    resourceA++;
                }
            }

            void incrementB() {
                synchronized (lockB) {
                    resourceB++;
                }
            }

            int getA() { synchronized (lockA) { return resourceA; } }
            int getB() { synchronized (lockB) { return resourceB; } }
        }

        TwoResources res = new TwoResources();
        long start = System.currentTimeMillis();

        // These two threads can run SIMULTANEOUSLY because they use different locks
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 500_000; i++) res.incrementA();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 500_000; i++) res.incrementB();
        });

        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("  Resource A: " + res.getA() + " (expected 500000)");
        System.out.println("  Resource B: " + res.getB() + " (expected 500000)");
        System.out.println("  Time: " + (System.currentTimeMillis() - start) + "ms");
        System.out.println("  → Separate locks allow TRUE parallelism for independent resources\n");
    }

    // ========================================================================
    // DEMO 6: Reentrant locking — same thread can re-acquire its own lock
    // ========================================================================
    static void demo6_ReentrantNature() {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 6: Reentrant Locking — A Thread Can Re-enter Its Own Lock");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * Java's intrinsic locks are REENTRANT.
         * If a thread already holds a lock, it can re-acquire the same lock
         * without deadlocking itself.
         *
         * Internally, Java maintains a "hold count":
         *   acquire → count=1
         *   acquire again → count=2
         *   release → count=1
         *   release → count=0 (lock is free)
         *
         * Without reentrancy, calling syncMethodA() from syncMethodB()
         * on the same object would DEADLOCK!
         */

        class ReentrantDemo {
            synchronized void methodA() {
                System.out.println("    Inside methodA — holds lock on `this`");
                methodB(); // calls another synchronized method — reentrancy!
            }

            synchronized void methodB() {
                System.out.println("    Inside methodB — re-entered the same lock!");
                methodC();
            }

            synchronized void methodC() {
                System.out.println("    Inside methodC — re-entered again! (hold count = 3)");
            }
        }

        new ReentrantDemo().methodA();
        System.out.println("  → No deadlock! Intrinsic locks are reentrant.\n");
    }

    // ========================================================================
    // DEMO 7: Fixing the bank account from C_RaceCondition
    // ========================================================================
    static void demo7_FixedBankAccount() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 7: Fixed Bank Account — No More Overdraft");
        System.out.println("═══════════════════════════════════════════════════");

        class SafeBankAccount {
            private int balance;

            SafeBankAccount(int balance) { this.balance = balance; }

            // synchronized makes check-then-act ATOMIC
            synchronized boolean withdraw(String atm, int amount) {
                if (balance >= amount) {
                    try { Thread.sleep(1); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    balance -= amount;
                    System.out.println("    " + atm + " withdrew $" + amount + ". Remaining: $" + balance);
                    return true;
                } else {
                    System.out.println("    " + atm + " DENIED. Balance: $" + balance);
                    return false;
                }
            }

            synchronized int getBalance() { return balance; }
        }

        for (int run = 1; run <= 3; run++) {
            SafeBankAccount account = new SafeBankAccount(1000);

            Thread atm1 = new Thread(() -> account.withdraw("ATM-1", 700));
            Thread atm2 = new Thread(() -> account.withdraw("ATM-2", 700));

            System.out.println("  Run " + run + ":");
            atm1.start(); atm2.start();
            atm1.join(); atm2.join();
            System.out.println("    Final: $" + account.getBalance()
                    + (account.getBalance() >= 0 ? " ✓ No overdraft!" : ""));
            System.out.println();
        }
    }

    // ========================================================================
    // MAIN — Run all demos
    // ========================================================================
    public static void main(String[] args) throws InterruptedException {
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║   PHASE 1D: SYNCHRONIZED KEYWORD                 ║");
        System.out.println("╚═══════════════════════════════════════════════════╝\n");

        demo1_SynchronizedMethod();
        demo2_SynchronizedBlock();
        demo3_InstanceVsClassLock();
        demo4_StaticSynchronized();
        demo5_CustomLockObject();
        demo6_ReentrantNature();
        demo7_FixedBankAccount();

        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("1. synchronized = mutual exclusion (only one thread at a time)");
        System.out.println("2. Synchronized METHOD locks on `this` or `Class` (for static)");
        System.out.println("3. Synchronized BLOCK is more flexible — lock only critical section");
        System.out.println("4. Use separate lock objects for independent resources (better parallelism)");
        System.out.println("5. Intrinsic locks are REENTRANT — same thread can re-acquire");
        System.out.println("6. Provides atomicity + visibility + ordering guarantees");
        System.out.println("7. DOWNSIDE: Can cause deadlocks (see file G) and performance bottlenecks");
    }
}
