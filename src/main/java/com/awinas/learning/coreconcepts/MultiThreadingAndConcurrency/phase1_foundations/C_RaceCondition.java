package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.phase1_foundations;

/**
 * ============================================================================
 * C - RACE CONDITIONS: When Threads Step on Each Other's Toes
 * ============================================================================
 *
 * WHAT IS A RACE CONDITION?
 * ─────────────────────────
 * A race condition occurs when:
 *   1. Two or more threads ACCESS the same shared data
 *   2. At least one thread WRITES to it
 *   3. There is NO synchronization/coordination
 *
 * The result depends on the TIMING of thread execution — which is unpredictable.
 * Run the same code 10 times, get 10 different wrong answers.
 *
 * WHY DOES IT HAPPEN?
 * ───────────────────
 * The operation "counter++" LOOKS atomic but is actually THREE steps:
 *
 *   1. READ   — read current value of counter from memory
 *   2. MODIFY — add 1 to the value
 *   3. WRITE  — write new value back to memory
 *
 * If two threads do this simultaneously:
 *
 *   Thread A: READ counter (gets 5)
 *   Thread B: READ counter (gets 5)     ← Both read the SAME value!
 *   Thread A: WRITE counter = 6
 *   Thread B: WRITE counter = 6         ← One increment is LOST!
 *
 * This is called a "lost update" — one of the most common concurrency bugs.
 *
 * MEMORY LAYOUT — WHY THREADS CAN SEE STALE DATA:
 * ────────────────────────────────────────────────
 * ┌──────────────┐     ┌──────────────┐
 * │  Thread A    │     │  Thread B    │
 * │  ┌────────┐  │     │  ┌────────┐  │
 * │  │ CPU    │  │     │  │ CPU    │  │
 * │  │ Cache  │  │     │  │ Cache  │  │
 * │  └───┬────┘  │     │  └───┬────┘  │
 * └──────┼───────┘     └──────┼───────┘
 *        │                    │
 *        ▼                    ▼
 * ┌──────────────────────────────────────┐
 * │         MAIN MEMORY (Heap)           │
 * │         counter = ???                │
 * └──────────────────────────────────────┘
 *
 * Each CPU core has its own CACHE. Threads may read from cache
 * instead of main memory, seeing STALE values.
 */
public class C_RaceCondition {

    // Shared mutable state — the root of all concurrency evil
    private static int counter = 0;
    private static final int INCREMENTS_PER_THREAD = 100_000;

    // ========================================================================
    // DEMO 1: The classic race condition — counter increment
    // ========================================================================
    static void demo1_ClassicRaceCondition() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 1: Classic Race Condition — Lost Updates");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * Expected: 2 threads × 100,000 increments = 200,000
         * Actual:   Some number LESS than 200,000 (varies each run!)
         *
         * The difference = number of "lost updates" due to race condition.
         */

        for (int run = 1; run <= 3; run++) {
            counter = 0; // reset

            Thread t1 = new Thread(() -> {
                for (int i = 0; i < INCREMENTS_PER_THREAD; i++) {
                    counter++; // NOT atomic! READ → MODIFY → WRITE
                }
            }, "Incrementer-1");

            Thread t2 = new Thread(() -> {
                for (int i = 0; i < INCREMENTS_PER_THREAD; i++) {
                    counter++; // Race condition here!
                }
            }, "Incrementer-2");

            t1.start();
            t2.start();
            t1.join();
            t2.join();

            int expected = 2 * INCREMENTS_PER_THREAD;
            System.out.println("  Run " + run + ": Expected=" + expected
                    + ", Actual=" + counter
                    + ", Lost=" + (expected - counter));
        }
        System.out.println("  → Different result each time! That's a race condition.\n");
    }

    // ========================================================================
    // DEMO 2: Race condition with a bank account
    // ========================================================================
    static void demo2_BankAccountRace() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 2: Bank Account — Real-World Race Condition");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * Scenario: Two ATMs accessing the same bank account simultaneously.
         * Balance = $1000. Both try to withdraw $700.
         *
         * Without sync:
         *   ATM-A checks: balance(1000) >= 700? YES → withdraws
         *   ATM-B checks: balance(1000) >= 700? YES → withdraws
         *   Both succeed! Balance = -$400 (OVERDRAFT!)
         *
         * This is called "check-then-act" race condition.
         */

        class BankAccount {
            private int balance;

            BankAccount(int balance) { this.balance = balance; }

            // NOT thread-safe!
            boolean withdraw(String atm, int amount) {
                if (balance >= amount) {
                    // Simulate processing delay — increases chance of race
                    try { Thread.sleep(1); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    balance -= amount;
                    System.out.println("    " + atm + " withdrew $" + amount + ". Remaining: $" + balance);
                    return true;
                } else {
                    System.out.println("    " + atm + " DENIED. Balance: $" + balance);
                    return false;
                }
            }

            int getBalance() { return balance; }
        }

        for (int run = 1; run <= 3; run++) {
            BankAccount account = new BankAccount(1000);

            Thread atm1 = new Thread(() -> account.withdraw("ATM-1", 700), "ATM-1");
            Thread atm2 = new Thread(() -> account.withdraw("ATM-2", 700), "ATM-2");

            System.out.println("  Run " + run + " (Starting balance: $1000):");
            atm1.start();
            atm2.start();
            atm1.join();
            atm2.join();
            System.out.println("    Final balance: $" + account.getBalance()
                    + (account.getBalance() < 0 ? " ← OVERDRAFT BUG!" : ""));
            System.out.println();
        }
    }

    // ========================================================================
    // DEMO 3: Race condition with ArrayList (structural corruption)
    // ========================================================================
    static void demo3_ListRaceCondition() throws InterruptedException {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 3: ArrayList Race Condition — Structural Corruption");
        System.out.println("═══════════════════════════════════════════════════");

        /*
         * ArrayList is NOT thread-safe.
         * Multiple threads adding elements can cause:
         *   1. Lost elements (overwrites)
         *   2. ArrayIndexOutOfBoundsException (internal array resize race)
         *   3. Null elements in unexpected positions
         */

        java.util.List<Integer> unsafeList = new java.util.ArrayList<>();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) unsafeList.add(i);
        });
        Thread t2 = new Thread(() -> {
            for (int i = 10000; i < 20000; i++) unsafeList.add(i);
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (Exception e) {
            System.out.println("  Exception occurred: " + e.getMessage());
        }

        System.out.println("  Expected size: 20000");
        System.out.println("  Actual size:   " + unsafeList.size());
        if (unsafeList.size() < 20000) {
            System.out.println("  → Elements were LOST due to concurrent modification!");
        }

        // Count nulls
        long nulls = unsafeList.stream().filter(java.util.Objects::isNull).count();
        if (nulls > 0) {
            System.out.println("  → Found " + nulls + " null elements (memory corruption)!");
        }

        System.out.println("  → This is why you need ConcurrentCollections (Phase 3)\n");
    }

    // ========================================================================
    // DEMO 4: Identifying race condition types
    // ========================================================================
    static void demo4_TypesOfRaceConditions() {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("DEMO 4: Types of Race Conditions (Theory)");
        System.out.println("═══════════════════════════════════════════════════");

        System.out.println("""
          TYPE 1: Read-Modify-Write (counter++)
          ──────────────────────────────────────
          Thread A: READ(5)  → ADD 1 → WRITE(6)
          Thread B: READ(5)  → ADD 1 → WRITE(6)  ← Lost update!
        
          TYPE 2: Check-Then-Act (if balance >= amount → withdraw)
          ─────────────────────────────────────────────────────────
          Thread A: CHECK(1000 >= 700? YES) → ACT(withdraw)
          Thread B: CHECK(1000 >= 700? YES) → ACT(withdraw)  ← Both pass!
        
          TYPE 3: Compound Operations (put-if-absent)
          ────────────────────────────────────────────
          Thread A: map.containsKey("x")? NO → map.put("x", 1)
          Thread B: map.containsKey("x")? NO → map.put("x", 2)  ← Both insert!
        
          SOLUTIONS (covered in upcoming files):
          ───────────────────────────────────────
          1. synchronized keyword  → mutual exclusion (only one thread at a time)
          2. volatile keyword      → visibility guarantee (no stale cache reads)
          3. Atomic classes        → lock-free thread-safe operations (Phase 4)
          4. Concurrent collections → thread-safe data structures (Phase 3)
          5. Locks (ReentrantLock)  → more flexible than synchronized (Phase 2)
        """);
    }

    // ========================================================================
    // MAIN — Run all demos
    // ========================================================================
    public static void main(String[] args) throws InterruptedException {
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║   PHASE 1C: RACE CONDITIONS                      ║");
        System.out.println("╚═══════════════════════════════════════════════════╝\n");

        demo1_ClassicRaceCondition();
        demo2_BankAccountRace();
        demo3_ListRaceCondition();
        demo4_TypesOfRaceConditions();

        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("1. Race condition = unpredictable results from unsynchronized shared data");
        System.out.println("2. counter++ is NOT atomic (READ → MODIFY → WRITE)");
        System.out.println("3. 'Check-then-act' is another common race pattern");
        System.out.println("4. Results vary between runs — the hallmark of a concurrency bug");
        System.out.println("5. ArrayList, HashMap etc. are NOT thread-safe");
        System.out.println("6. Next file (D_Synchronized) shows how to FIX these problems");
    }
}
