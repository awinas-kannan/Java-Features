package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.phase1_foundations;

/**
 * ============================================================================
 * E - VOLATILE: Visibility Guarantee Without Locking
 * ============================================================================
 *
 * WHAT IS VOLATILE?
 * ─────────────────
 * The `volatile` keyword guarantees VISIBILITY of a variable across threads.
 * When a variable is declared volatile:
 *   - Every READ goes directly to MAIN MEMORY (not CPU cache)
 *   - Every WRITE goes directly to MAIN MEMORY (not CPU cache)
 *
 * THE VISIBILITY PROBLEM:
 * ───────────────────────
 * Without volatile, Thread A writes a variable, but Thread B may never see
 * the new value because it reads from its CPU CACHE (stale copy).
 *
 * ┌──────────────┐         ┌──────────────┐
 * │  Thread A    │         │  Thread B    │
 * │ ┌──────────┐ │         │ ┌──────────┐ │
 * │ │ Cache:   │ │         │ │ Cache:   │ │
 * │ │ flag=true│ │         │ │ flag=false│◄── STALE!
 * │ └──────────┘ │         │ └──────────┘ │
 * └──────┬───────┘         └──────────────┘
 *        │ writes flag=true
 *        ▼
 * ┌──────────────────────────────────────┐
 * │     MAIN MEMORY: flag = true         │
 * └──────────────────────────────────────┘
 *        ↑
 *     Thread B never reads from here! (uses cached false)
 *
 * With `volatile boolean flag`:
 *   - Thread A's write goes to main memory immediately
 *   - Thread B always reads from main memory
 *   - Thread B sees the new value!
 *
 * VOLATILE vs SYNCHRONIZED:
 * ─────────────────────────
 * ┌───────────────┬──────────────────────┬──────────────────────┐
 * │               │     volatile         │     synchronized     │
 * ├───────────────┼──────────────────────┼──────────────────────┤
 * │ Visibility    │        YES           │        YES           │
 * │ Atomicity     │   NO (only for       │        YES           │
 * │               │   single read/write) │                      │
 * │ Blocking      │        NO            │        YES           │
 * │ Performance   │       Fast           │       Slower         │
 * │ Use case      │  Flags, status vars  │  Compound operations │
 * │               │  (single read/write) │  (check-then-act,    │
 * │               │                      │   read-modify-write) │
 * └───────────────┴──────────────────────┴──────────────────────┘
 *
 * WHEN TO USE VOLATILE:
 * ─────────────────────
 * ✅ Simple flags (stop flag, ready flag, status indicator)
 * ✅ Variables written by ONE thread and read by MANY
 * ✅ Single read/write operations (long, double need volatile for atomicity on 32-bit JVMs)
 *
 * ❌ counter++ (READ + MODIFY + WRITE — volatile doesn't make this atomic!)
 * ❌ Check-then-act patterns
 * ❌ Any compound operation requiring atomicity
 *
 * HAPPENS-BEFORE GUARANTEE:
 * ─────────────────────────
 * A write to a volatile variable HAPPENS-BEFORE every subsequent read
 * of that variable. This means ALL writes done BEFORE the volatile write
 * are also visible to the thread that reads the volatile variable.
 */
public class E_Volatile {

    // ========================================================================
    // DEMO 1: The visibility problem — thread never sees the update
    // ========================================================================
    // NOT volatile — Thread B may never see the change
    private static boolean stopFlag = false;

    static void demo1_VisibilityProblem() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 1: Visibility Problem — Without volatile");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * The worker thread spins on `stopFlag`.
         * Without volatile, the JIT compiler may OPTIMIZE the check:
         *   while (!stopFlag) → while (true)  // hoists the read!
         *
         * This means the worker may NEVER stop, even after main sets stopFlag = true.
         *
         * NOTE: This is JVM/JIT dependent. On some runs it might stop,
         *       on others it might run forever. That's the danger — it's UNPREDICTABLE.
         */

        stopFlag = false;

        Thread worker = new Thread(() -> {
            long count = 0;
            while (!stopFlag) {
                count++;
            }
            System.out.println("  Worker stopped after " + count + " iterations");
        });

        worker.start();
        Thread.sleep(100);
        stopFlag = true;
        System.out.println("  Main set stopFlag = true");

        worker.join(2000); // wait max 2 seconds
        if (worker.isAlive()) {
            System.out.println("  ⚠ Worker is STILL running! It can't see the flag change.");
            System.out.println("    (This happens because JIT optimized the read away)");
            worker.interrupt(); // force stop for demo purposes
        }
        System.out.println();
    }

    // ========================================================================
    // DEMO 2: The fix — using volatile
    // ========================================================================
    private static volatile boolean volatileStopFlag = false;

    static void demo2_VolatileFix() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 2: Volatile Fix — Guaranteed Visibility");
        System.out.println("═══════════════════════════════════════════════════");

        volatileStopFlag = false;

        Thread worker = new Thread(() -> {
            long count = 0;
            while (!volatileStopFlag) { // reads from MAIN MEMORY every time
                count++;
            }
            System.out.println("  Worker stopped after " + count + " iterations");
        });

        worker.start();
        Thread.sleep(100);
        volatileStopFlag = true; // writes to MAIN MEMORY immediately
        System.out.println("  Main set volatileStopFlag = true");

        worker.join(2000);
        if (worker.isAlive()) {
            System.out.println("  ⚠ Still running? (Very unlikely with volatile)");
            worker.interrupt();
        } else {
            System.out.println("  → Worker stopped successfully! volatile ensured visibility.");
        }
        System.out.println();
    }

    // ========================================================================
    // DEMO 3: volatile does NOT fix counter++ (not atomic!)
    // ========================================================================
    private static volatile int volatileCounter = 0;

    static void demo3_VolatileDoesNotFixCompoundOps() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 3: volatile Does NOT Fix counter++");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * volatile guarantees you read/write the LATEST value.
         * BUT counter++ is still THREE steps:
         *   1. READ latest value (volatile ensures this)
         *   2. ADD 1
         *   3. WRITE new value (volatile ensures this)
         *
         * Two threads can still READ the same value between steps 1 and 3!
         *
         * volatile counter:
         *   Thread A: READ(5)                    ← reads latest (thanks to volatile)
         *   Thread B: READ(5)                    ← also reads latest
         *   Thread A: WRITE(6)                   ← writes latest
         *   Thread B: WRITE(6)                   ← overwrites! Lost update!
         *
         * For counter++, you need:
         *   - synchronized, OR
         *   - AtomicInteger (Phase 4)
         */

        volatileCounter = 0;

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) volatileCounter++;
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) volatileCounter++;
        });

        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("  Expected: 200000");
        System.out.println("  Actual:   " + volatileCounter);
        System.out.println("  → STILL wrong! volatile doesn't make counter++ atomic.");
        System.out.println("    Use synchronized or AtomicInteger instead.\n");
    }

    // ========================================================================
    // DEMO 4: Volatile for double-checked locking pattern (Singleton)
    // ========================================================================
    static void demo4_VolatileInSingleton() {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 4: Volatile in Double-Checked Locking (Singleton)");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * Classic use of volatile — the Double-Checked Locking Singleton:
         *
         *   class Singleton {
         *       private static volatile Singleton instance;  // ← MUST be volatile
         *
         *       static Singleton getInstance() {
         *           if (instance == null) {             // 1st check (no lock)
         *               synchronized (Singleton.class) {
         *                   if (instance == null) {     // 2nd check (with lock)
         *                       instance = new Singleton();
         *                   }
         *               }
         *           }
         *           return instance;
         *       }
         *   }
         *
         * WHY volatile here?
         *   `instance = new Singleton()` is actually 3 steps:
         *     a. Allocate memory
         *     b. Call constructor (initialize fields)
         *     c. Assign reference to `instance`
         *
         *   Without volatile, JVM may REORDER to: a → c → b
         *   Thread-B sees `instance != null` (step c done) but fields aren't initialized (step b not done)!
         *   volatile prevents this reordering.
         */

        System.out.println("  volatile prevents instruction reordering in object construction.");
        System.out.println("  Without it, another thread may see a partially-constructed object.");
        System.out.println("  See Phase 5 (F_ThreadSafeSingleton.java) for full implementation.\n");
    }

    // ========================================================================
    // DEMO 5: Happens-Before relationship
    // ========================================================================
    private static volatile boolean ready = false;
    private static int data = 0;

    static void demo5_HappensBefore() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 5: Happens-Before — Volatile Piggyback");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * HAPPENS-BEFORE rule for volatile:
         *   Everything BEFORE a volatile WRITE is visible
         *   to everything AFTER a volatile READ of the same variable.
         *
         *   Thread A:                  Thread B:
         *     data = 42;                 if (ready) {        // volatile READ
         *     ready = true; // volatile WRITE    // data is guaranteed to be 42!
         *                                   print(data);     // sees 42, not 0
         *                                 }
         *
         * The write to `data` (non-volatile) piggybacks on the volatile write to `ready`.
         * This is one of the most powerful and subtle aspects of the Java Memory Model.
         */

        data = 0;
        ready = false;

        Thread writer = new Thread(() -> {
            data = 42;        // non-volatile write
            ready = true;     // volatile write — flushes ALL previous writes to main memory
        });

        Thread reader = new Thread(() -> {
            while (!ready) { } // volatile read — spin until ready
            System.out.println("  Reader sees data = " + data);
        });

        writer.start();
        reader.start();
        writer.join();
        reader.join();

        System.out.println("  → data=42 is visible because it was written BEFORE the volatile write");
        System.out.println("    This is the happens-before guarantee.\n");
    }

    // ========================================================================
    // MAIN — Run all demos
    // ========================================================================
    public static void main(String[] args) throws InterruptedException {
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║   PHASE 1E: VOLATILE KEYWORD                     ║");
        System.out.println("╚═══════════════════════════════════════════════════╝\n");

        demo1_VisibilityProblem();
        demo2_VolatileFix();
        demo3_VolatileDoesNotFixCompoundOps();
        demo4_VolatileInSingleton();
        demo5_HappensBefore();

        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("1. volatile = visibility guarantee (reads/writes go to main memory)");
        System.out.println("2. volatile does NOT provide atomicity (counter++ is still broken!)");
        System.out.println("3. Use volatile for: flags, status variables, single read/write");
        System.out.println("4. Don't use volatile for: compound operations (use synchronized instead)");
        System.out.println("5. Happens-before: writes BEFORE volatile write are visible after volatile read");
        System.out.println("6. volatile is cheaper than synchronized (no blocking/locking)");
        System.out.println("7. Essential for Double-Checked Locking Singleton pattern");
    }
}
