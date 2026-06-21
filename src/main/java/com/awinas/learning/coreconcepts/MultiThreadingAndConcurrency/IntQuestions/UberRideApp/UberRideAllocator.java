package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.IntQuestions.UberRideApp;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class UberRideAllocator {

    private int democrats = 0;
    private int republicans = 0;

    private final ReentrantLock lock = new ReentrantLock();

    private final Semaphore democratQueue = new Semaphore(0);
    private final Semaphore republicanQueue = new Semaphore(0);

    private final CyclicBarrier barrier = new CyclicBarrier(4);

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

    private void seated(Party party) {
        System.out.println(
            Thread.currentThread().getName()
                + " seated -> " + party
        );
    }

    private void drive() {
        System.out.println(
            "CAB DEPARTING. Captain = "
                + Thread.currentThread().getName()
        );
    }

    public static void main(String[] args) throws Exception {

        UberRideAllocator uber = new UberRideAllocator();

        Thread[] riders = {
            new Thread(() -> ride(uber, Party.DEMOCRAT), "D1"),
            new Thread(() -> ride(uber, Party.DEMOCRAT), "D2"),
            new Thread(() -> ride(uber, Party.REPUBLICAN), "R1"),
            new Thread(() -> ride(uber, Party.REPUBLICAN), "R2"),

            new Thread(() -> ride(uber, Party.DEMOCRAT), "D3"),
            new Thread(() -> ride(uber, Party.DEMOCRAT), "D4"),
            new Thread(() -> ride(uber, Party.DEMOCRAT), "D5"),
            new Thread(() -> ride(uber, Party.DEMOCRAT), "D6"),

            new Thread(() -> ride(uber, Party.REPUBLICAN), "R3"),
            new Thread(() -> ride(uber, Party.REPUBLICAN), "R4"),
            new Thread(() -> ride(uber, Party.REPUBLICAN), "R5"),
            new Thread(() -> ride(uber, Party.REPUBLICAN), "R6")
        };

        for (Thread rider : riders) {
            rider.start();
        }
    }

    private static void ride(
            UberRideAllocator uber,
            Party party) {

        try {
            uber.seat(party);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}