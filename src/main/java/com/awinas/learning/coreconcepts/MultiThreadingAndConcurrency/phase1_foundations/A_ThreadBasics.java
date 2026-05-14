package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.phase1_foundations;

/**
 * ============================================================================
 * A - THREAD BASICS: The Foundation of Multi-Threading in Java
 * ============================================================================
 *
 * WHAT IS A THREAD?
 * -----------------
 * A thread is the smallest unit of execution within a process.
 * Every Java program has at least ONE thread — the "main" thread that runs main().
 *
 * Think of it like a restaurant:
 *   - The restaurant = Process (your Java application)
 *   - Each waiter  = Thread (handles tasks concurrently)
 *   - One waiter can serve one table at a time, but multiple waiters
 *     can serve multiple tables SIMULTANEOUSLY.
 *
 * WHY MULTI-THREADING?
 * --------------------
 * 1. Performance   — Utilize multiple CPU cores
 * 2. Responsiveness — Keep UI responsive while doing background work
 * 3. Resource sharing — Threads share the same memory space (heap)
 * 4. Efficiency    — Thread creation is cheaper than process creation
 *
 * PROCESS vs THREAD
 * -----------------
 * ┌─────────────────────────────────────────────────────────┐
 * │  Process                                                │
 * │  - Own memory space (heap, stack)                       │
 * │  - Heavyweight, expensive to create                     │
 * │  - Isolated from other processes                        │
 * │  - Inter-process communication is complex               │
 * │                                                         │
 * │  Thread                                                 │
 * │  - Shares heap memory with other threads in same process│
 * │  - Lightweight, cheap to create                         │
 * │  - Each thread has its OWN stack                        │
 * │  - Communication is easy (shared variables)             │
 * │    but DANGEROUS (race conditions!)                     │
 * └─────────────────────────────────────────────────────────┘
 *
 * TWO WAYS TO CREATE A THREAD:
 * ----------------------------
 * 1. Extend Thread class     — Simple, but you lose inheritance (Java = single inheritance)
 * 2. Implement Runnable      — Preferred! Keeps inheritance free, promotes composition
 * 3. (Bonus) Lambda / Anonymous class — Concise syntax for Runnable
 *
 * THREAD LIFECYCLE (STATES):
 * --------------------------
 *
 *    new Thread()         start()          run() finishes
 *        │                  │                    │
 *        ▼                  ▼                    ▼
 *      [NEW] ──────► [RUNNABLE] ──────────► [TERMINATED]
 *                       │    ▲
 *           ┌───────────┘    └──────────────┐
 *           ▼                               │
 *      [BLOCKED]    [WAITING]    [TIMED_WAITING]
 *      (waiting     (wait(),     (sleep(ms),
 *       for lock)    join())      join(ms),
 *                                 wait(ms))
 *
 * States explained:
 *   NEW            — Thread object created, start() NOT yet called
 *   RUNNABLE       — start() called, thread is eligible to run (may or may not be running)
 *   BLOCKED        — Waiting to acquire a lock (synchronized block)
 *   WAITING        — Indefinitely waiting for another thread (wait(), join())
 *   TIMED_WAITING  — Waiting for a specified time (sleep(), join(timeout))
 *   TERMINATED     — run() method completed or exception thrown
 *
 * IMPORTANT: Calling run() directly does NOT start a new thread!
 *            It runs in the CURRENT thread. Always use start().
 */
public class A_ThreadBasics {

    // ========================================================================
    // WAY 1: Extend Thread class
    // ========================================================================
    static class MyThread extends Thread {
        private final String taskName;

        MyThread(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 3; i++) {
                System.out.println("[" + getName() + "] " + taskName + " — iteration " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("[" + getName() + "] " + taskName + " — DONE");
        }
    }

    // ========================================================================
    // WAY 2: Implement Runnable interface (PREFERRED)
    // ========================================================================
    static class MyRunnable implements Runnable {
        private final String taskName;

        MyRunnable(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 3; i++) {
                System.out.println("[" + Thread.currentThread().getName() + "] "
                        + taskName + " — iteration " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("[" + Thread.currentThread().getName() + "] " + taskName + " — DONE");
        }
    }

    // ========================================================================
    // DEMO 1: Thread class vs Runnable interface
    // ========================================================================
    static void demo1_ThreadVsRunnable() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 1: Thread class vs Runnable interface");
        System.out.println("═══════════════════════════════════════════════════");

        // Way 1: Extending Thread
        MyThread t1 = new MyThread("Cooking");
        t1.setName("Chef-Thread");

        // Way 2: Implementing Runnable (pass to Thread constructor)
        Thread t2 = new Thread(new MyRunnable("Cleaning"), "Cleaner-Thread");

        // Way 3: Lambda (since Runnable is a functional interface with single abstract method)
        Thread t3 = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println("[" + Thread.currentThread().getName() + "] Serving — iteration " + i);
                try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
            System.out.println("[" + Thread.currentThread().getName() + "] Serving — DONE");
        }, "Waiter-Thread");

        // Way 4: Anonymous inner class
        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("[" + Thread.currentThread().getName() + "] Billing — quick task DONE");
            }
        }, "Cashier-Thread");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // Wait for all to finish before moving to next demo
        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println();
    }

    // ========================================================================
    // DEMO 2: Thread Lifecycle — observing all states
    // ========================================================================
    static void demo2_ThreadLifecycle() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 2: Thread Lifecycle — Observing States");
        System.out.println("═══════════════════════════════════════════════════");

        final Object lock = new Object();

        Thread worker = new Thread(() -> {
            // This thread will try to acquire 'lock'
            synchronized (lock) {
                try {
                    Thread.sleep(2000);  // TIMED_WAITING
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Worker");

        // STATE 1: NEW — created but not started
        System.out.println("After new Thread()     → " + worker.getState());  // NEW

        // Hold the lock so 'worker' will be BLOCKED when it tries to acquire it
        synchronized (lock) {
            worker.start();

            // STATE 2: RUNNABLE — started, but will quickly become BLOCKED
            Thread.sleep(100); // give it time to attempt lock acquisition
            System.out.println("After start() + lock held → " + worker.getState());  // BLOCKED
        }
        // Lock released — worker enters synchronized block and sleeps

        Thread.sleep(100); // let worker enter sleep()
        // STATE 3: TIMED_WAITING — sleeping
        System.out.println("While sleeping         → " + worker.getState());  // TIMED_WAITING

        worker.join(); // wait for it to finish

        // STATE 4: TERMINATED — run() completed
        System.out.println("After join()           → " + worker.getState());  // TERMINATED

        System.out.println();
    }

    // ========================================================================
    // DEMO 3: MISTAKE — calling run() vs start()
    // ========================================================================
    static void demo3_RunVsStart() {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 3: run() vs start() — A COMMON MISTAKE");
        System.out.println("═══════════════════════════════════════════════════");

        Thread t = new Thread(() -> {
            System.out.println("  Executing in: " + Thread.currentThread().getName());
        }, "Worker-Thread");

        // WRONG: Calling run() directly — executes in MAIN thread, no new thread!
        System.out.println("Calling run():");
        t.run();
        // Output will show "main", not "Worker-Thread"

        // CORRECT: Calling start() — creates a new thread
        System.out.println("Calling start():");
        t.start();
        // Output will show "Worker-Thread"

        try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        System.out.println();
    }

    // ========================================================================
    // DEMO 4: Main thread info
    // ========================================================================
    static void demo4_MainThreadInfo() {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 4: Main Thread Information");
        System.out.println("═══════════════════════════════════════════════════");

        Thread mainThread = Thread.currentThread();

        System.out.println("Name      : " + mainThread.getName());
        System.out.println("ID        : " + mainThread.getId());
        System.out.println("Priority  : " + mainThread.getPriority());
        System.out.println("State     : " + mainThread.getState());
        System.out.println("Is Alive  : " + mainThread.isAlive());
        System.out.println("Is Daemon : " + mainThread.isDaemon());
        System.out.println("Group     : " + mainThread.getThreadGroup().getName());

        System.out.println();
    }

    // ========================================================================
    // DEMO 5: Why Runnable is preferred over Thread
    // ========================================================================
    static void demo5_WhyRunnableIsPreferred() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 5: Why Runnable is Preferred");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * REASON 1: Java supports single inheritance only.
         *   class MyThread extends Thread { }
         *   // Now MyThread CANNOT extend any other class!
         *
         *   class MyRunnable implements Runnable { }
         *   // MyRunnable can still extend another class
         *   class MyRunnable extends SomeBaseClass implements Runnable { }
         *
         * REASON 2: Separation of concerns
         *   Runnable = WHAT to run (the task)
         *   Thread   = HOW to run (the execution mechanism)
         *
         * REASON 3: Reusability
         *   Same Runnable can be passed to Thread, ExecutorService, etc.
         *
         * REASON 4: Resource sharing
         *   Multiple threads can share the SAME Runnable instance
         */

        Runnable sharedTask = () -> {
            System.out.println("  " + Thread.currentThread().getName() + " executing shared task");
        };

        // Same Runnable, different threads
        Thread t1 = new Thread(sharedTask, "Thread-A");
        Thread t2 = new Thread(sharedTask, "Thread-B");
        Thread t3 = new Thread(sharedTask, "Thread-C");

        t1.start(); t2.start(); t3.start();
        t1.join(); t2.join(); t3.join();

        System.out.println("  → Same Runnable instance was reused by 3 threads!");
        System.out.println();
    }

    // ========================================================================
    // MAIN — Run all demos
    // ========================================================================
    public static void main(String[] args) throws InterruptedException {
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║   PHASE 1A: THREAD BASICS                        ║");
        System.out.println("╚═══════════════════════════════════════════════════╝\n");

        demo4_MainThreadInfo();
        demo3_RunVsStart();
        demo1_ThreadVsRunnable();
        demo2_ThreadLifecycle();
        demo5_WhyRunnableIsPreferred();

        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("1. Two ways to create threads: extend Thread OR implement Runnable");
        System.out.println("2. Implement Runnable is PREFERRED (keeps inheritance free)");
        System.out.println("3. Always call start(), NEVER call run() directly");
        System.out.println("4. Thread lifecycle: NEW → RUNNABLE → (BLOCKED/WAITING/TIMED_WAITING) → TERMINATED");
        System.out.println("5. Every Java app has a 'main' thread by default");
        System.out.println("6. Lambdas make thread creation concise (since Runnable is @FunctionalInterface)");
    }
}
