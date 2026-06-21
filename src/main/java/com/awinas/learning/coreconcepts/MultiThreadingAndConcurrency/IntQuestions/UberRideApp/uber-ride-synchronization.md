Below is a Markdown document that you can save as `uber-ride-synchronization.md`.

# Uber Ride Allocator - Concurrency Problem

## Problem Statement

A political conference has just ended, and attendees are leaving the venue by booking cab rides.

Each rider belongs to one of two political parties:

* Democrat
* Republican

Each rider request is represented as a separate thread.

### Cab Rules

Each cab can accommodate exactly **4 riders**.

To avoid conflicts, only the following combinations are allowed inside a cab:

1. 4 Democrats
2. 4 Republicans
3. 2 Democrats + 2 Republicans

Any other combination is invalid.

Examples:

| Combination                 | Valid? |
| --------------------------- | ------ |
| 4 Democrats                 | Yes    |
| 4 Republicans               | Yes    |
| 2 Democrats + 2 Republicans | Yes    |
| 3 Democrats + 1 Republican  | No     |
| 1 Democrat + 3 Republicans  | No     |

---

## Functional Requirements

Implement the following methods:

### `seat()`

Called by every rider thread.

Responsibilities:

* Rider should wait until it becomes part of a valid group.
* No rider should proceed until a valid cab composition is formed.

### `drive()`

Called by exactly one rider (captain).

Responsibilities:

* Start the cab ride.
* Should be invoked only after all 4 riders have been seated.

---

# Solution Overview

We use the following synchronization primitives:

1. `ReentrantLock`
2. `Semaphore`
3. `CyclicBarrier`

---

# Shared State

```java
private int democrats = 0;
private int republicans = 0;

private final ReentrantLock lock = new ReentrantLock();

private final Semaphore democratQueue = new Semaphore(0);
private final Semaphore republicanQueue = new Semaphore(0);

private final CyclicBarrier barrier = new CyclicBarrier(4);
```

---

# High Level Algorithm

When a rider arrives:

1. Acquire lock.
2. Increment party count.
3. Check if a valid group can be formed.
4. If not, wait on semaphore.
5. If yes:

    * Release exactly four riders.
    * Current thread becomes captain.
6. All selected riders call `seated()`.
7. All riders wait on a barrier.
8. Once all four reach the barrier:

    * Captain calls `drive()`.
9. Captain releases the lock.

---

# Final Java Solution

```java
public void seat(Party party) throws Exception {

    boolean captain = false;
    Semaphore myQueue;

    lock.lock();

    if (party == Party.DEMOCRAT) {

        democrats++;
        myQueue = democratQueue;

        if (democrats == 4) {

            democratQueue.release(4);
            democrats -= 4;
            captain = true;

        } else if (democrats >= 2 && republicans >= 2) {

            democratQueue.release(2);
            republicanQueue.release(2);

            democrats -= 2;
            republicans -= 2;

            captain = true;

        } else {
            lock.unlock();
        }

    } else {

        republicans++;
        myQueue = republicanQueue;

        if (republicans == 4) {

            republicanQueue.release(4);
            republicans -= 4;
            captain = true;

        } else if (republicans >= 2 && democrats >= 2) {

            republicanQueue.release(2);
            democratQueue.release(2);

            republicans -= 2;
            democrats -= 2;

            captain = true;

        } else {
            lock.unlock();
        }
    }

    myQueue.acquire();

    seated(party);

    barrier.await();

    if (captain) {
        drive();
        lock.unlock();
    }
}
```

---

# Execution Flow Example

Suppose the following riders arrive simultaneously:

```text
D1 D2 R1 R2 D3 D4 D5 D6
```

---

## Step 1

D1 arrives.

```text
democrats = 1
```

No valid group.

D1 blocks on:

```java
democratQueue.acquire()
```

---

## Step 2

D2 arrives.

```text
democrats = 2
```

Still no valid group.

D2 blocks.

---

## Step 3

R1 arrives.

```text
republicans = 1
```

Still no valid group.

R1 blocks.

---

## Step 4

R2 arrives.

Now:

```text
democrats = 2
republicans = 2
```

A valid group is formed.

R2 becomes captain.

```java
democratQueue.release(2);
republicanQueue.release(2);
```

The following riders wake up:

```text
D1
D2
R1
R2
```

---

## Step 5

All four riders execute:

```java
seated()
```

---

## Step 6

All four wait at:

```java
barrier.await()
```

Barrier count:

```text
D1 -> 1/4
D2 -> 2/4
R1 -> 3/4
R2 -> 4/4
```

Barrier opens.

---

## Step 7

Captain executes:

```java
drive()
```

Cab departs.

---

# Core Concurrency Concepts Used

# 1. ReentrantLock

## Purpose

Protect shared variables from race conditions.

Example:

```java
lock.lock();

democrats++;

lock.unlock();
```

Without lock:

```text
D1 reads democrats = 0
D2 reads democrats = 0

D1 writes 1
D2 writes 1
```

Final value:

```text
democrats = 1
```

Incorrect.

Lock ensures:

```text
Only one thread updates counters at a time.
```

---

# 2. Semaphore

## Purpose

Block riders until a valid group is formed.

Example:

```java
Semaphore sem = new Semaphore(0);
```

Initially:

```text
permits = 0
```

Thread:

```java
sem.acquire();
```

Since permits are zero:

```text
Thread blocks.
```

Another thread:

```java
sem.release(2);
```

Permits become:

```text
2
```

Two waiting threads wake up.

---

## Uber Example

```java
democratQueue.release(2);
```

Exactly two Democrats are allowed to continue.

---

# 3. CyclicBarrier

## Purpose

Ensure all four selected riders are seated before the cab starts.

Example:

```java
CyclicBarrier barrier = new CyclicBarrier(4);
```

Threads:

```java
barrier.await();
```

Execution:

```text
Thread 1 -> waiting
Thread 2 -> waiting
Thread 3 -> waiting
Thread 4 -> arrives
```

When the fourth thread arrives:

```text
Barrier opens.
```

All four continue together.

---

# Why Use CyclicBarrier?

Without barrier:

```text
D1 seated
D2 seated

Captain drives

R1 and R2 still boarding
```

Incorrect.

Barrier guarantees:

```text
All four seated
THEN
drive()
```

---

# Complexity

## Time Complexity

```text
O(1)
```

per rider.

## Space Complexity

```text
O(number of waiting riders)
```

---

# Interview Summary

* `ReentrantLock` → protects shared counters.
* `Semaphore` → blocks riders until selected.
* `CyclicBarrier` → ensures all four riders are seated.
* Captain thread invokes `drive()`.
* Only valid combinations are allowed.

You can copy this into a file named **`uber-ride-synchronization.md`** and use it as interview preparation notes.
