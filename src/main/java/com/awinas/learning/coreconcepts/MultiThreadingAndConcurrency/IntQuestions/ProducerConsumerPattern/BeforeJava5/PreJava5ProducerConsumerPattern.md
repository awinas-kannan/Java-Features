# Producer Consumer Pattern - Complete Flow and Concepts (Interview Notes)

# Step 1: What is the Shared Resource?

```java
SharedBuffer buffer = new SharedBuffer(5);
```

Only one object is created:

```text
buffer
```

Both Producer and Consumer share this same object.

```java
Producer p = new Producer(buffer);
Consumer c = new Consumer(buffer);
```

Architecture:

```text
Producer ----> SharedBuffer <---- Consumer
```

---

# Step 2: Why are methods synchronized?

```java
public synchronized void produce()
public synchronized String consume()
```

This means:

```java
synchronized(this) {
   // method code
}
```

Here:

```text
this = buffer object
```

So both methods acquire:

```text
buffer object's monitor lock
```

Only one thread can execute inside `produce()` or `consume()` at a time.

---

# Step 3: What happens when Consumer enters first?

Assume:

```text
Queue = []
```

Consumer executes:

```java
buffer.consume();
```

Consumer acquires:

```text
buffer object's lock
```

State:

```text
Consumer -> RUNNING
Producer -> READY
```

---

# Step 4: Consumer checks queue

```java
while(queue.isEmpty())
```

Condition:

```text
true
```

Consumer executes:

```java
wait();
```

---

# Step 5: What exactly does wait() do?

`wait()` performs TWO actions:

```text
1. Releases the buffer object's lock.
2. Moves Consumer to WAITING state.
```

State becomes:

```text
Consumer -> WAITING
Producer -> READY
Lock -> FREE
```

This is the most important concept.

Without releasing the lock:

```text
Producer could never enter produce().
```

---

# Step 6: Producer enters

Since lock is now free:

```java
buffer.produce("Order-1");
```

Producer acquires:

```text
buffer object's lock
```

State:

```text
Producer -> RUNNING
Consumer -> WAITING
```

---

# Step 7: Producer adds item

```java
queue.add("Order-1");
```

Queue:

```text
[Order-1]
```

Producer executes:

```java
notifyAll();
```

---

# Step 8: What does notifyAll() do?

`notifyAll()`:

```text
Wakes all threads waiting on buffer object's monitor.
```

Consumer changes:

```text
WAITING -> BLOCKED/RUNNABLE
```

Important:

```text
notifyAll() DOES NOT release the lock.
```

Producer still owns the lock.

Consumer cannot continue immediately.

---

# Step 9: When is the lock released?

Producer exits:

```java
produce()
```

Since synchronized method ends:

```text
Producer releases buffer object's lock.
```

Now:

```text
Lock -> FREE
```

---

# Step 10: Consumer wakes up

Consumer competes for the lock.

Consumer acquires:

```text
buffer object's lock
```

Execution resumes immediately after:

```java
wait();
```

Consumer again checks:

```java
while(queue.isEmpty())
```

Queue:

```text
[Order-1]
```

Condition:

```text
false
```

Consumer consumes item.

---

# Step 11: Why do we use while instead of if?

Wrong:

```java
if(queue.isEmpty()) {
    wait();
}
```

Correct:

```java
while(queue.isEmpty()) {
    wait();
}
```

Reason:

Suppose:

```text
2 Consumers waiting.
```

Producer adds:

```text
1 Item
```

Producer executes:

```java
notifyAll();
```

Both consumers wake up.

Consumer-1 acquires lock first.

```text
Consumes the only item.
Queue becomes empty.
```

Consumer-2 now acquires lock.

If code used:

```java
if(queue.isEmpty())
```

Consumer-2 continues execution and attempts:

```java
queue.poll();
```

on an empty queue.

Using:

```java
while(queue.isEmpty())
```

Consumer checks again and safely waits.

---

# Step 12: Why notifyAll() instead of notify()?

Assume:

```text
2 Producers waiting.
2 Consumers waiting.
```

If:

```java
notify();
```

wakes another Producer while queue is still full:

```text
Producer wakes up.
Queue still full.
Producer waits again.
```

No progress occurs.

Using:

```java
notifyAll();
```

All threads wake up.

The correct thread proceeds.

---

# Step 13: What lock is acquired in synchronized methods?

Instance synchronized method:

```java
public synchronized void produce()
```

acquires:

```text
this object's monitor lock
```

Equivalent to:

```java
synchronized(this) {
}
```

Static synchronized method:

```java
public static synchronized void test()
```

acquires:

```text
Class object's lock
```

Equivalent to:

```java
synchronized(SharedBuffer.class) {
}
```

---

# Step 14: Why did Java 5 introduce BlockingQueue?

Problems with wait/notify:

* Complex code.
* Easy to make mistakes.
* Missed notifications.
* Deadlock possibilities.
* Difficult to maintain.

Java 5 solution:

```java
BlockingQueue<String> queue =
        new ArrayBlockingQueue<>(5);
```

Internally it uses:

```text
ReentrantLock
Condition
```

instead of:

```text
synchronized
wait()
notifyAll()
```

---

# Mapping

| Before Java 5 | Java 5+       |
| ------------- | ------------- |
| synchronized  | ReentrantLock |
| wait()        | await()       |
| notify()      | signal()      |
| notifyAll()   | signalAll()   |

---

# Complete Timeline

```text
Consumer enters consume()
        |
Acquires buffer lock
        |
Queue Empty?
        |
YES
        |
wait()
        |
Releases lock
        |
Consumer -> WAITING
        |
Producer enters
        |
Acquires lock
        |
Produces item
        |
notifyAll()
        |
Consumer wakes up
        |
Producer exits
        |
Releases lock
        |
Consumer acquires lock
        |
Consumes item
```

# Interview One-Liner

> `wait()` releases the monitor lock and moves the thread to WAITING state. `notifyAll()` wakes all waiting threads, but they can continue only after reacquiring the same object's monitor lock.
