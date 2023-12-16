package com.awinas.learning.MultiThreadingAndConcurrency;

public class DeadLockResolved {

	public static void main(String[] args) {

		final Object lock1 = new Object();
		final Object lock2 = new Object();

		// Thread-1
		Runnable block1 = new Runnable() {
			public void run() {
				synchronized (lock1) {
					try {
						// lock resources
						System.out.println("Thread 1: Holding lock 1...");
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// Thread-1 have A but need B also
					System.out.println("Thread 1: Waiting for lock 2...");
					synchronized (lock2) {
						System.out.println("Thread 1: Holding lock 1 & 2...");

					}
				}
			}
		};

		// Thread-2
		Runnable block2 = new Runnable() {
			public void run() {
				//This below line will not execute as lock1 is already locked
				//Will execute when thread 1 completes sync block with lock1
				synchronized (lock1) {
					System.out.println("Thread 2: Holding lock 1...");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					System.out.println("Thread 2: Waiting for lock 2...");
					synchronized (lock2) {
						System.out.println("Thread 2: Holding lock 1 & 2...");
					}
				}
			}
		};

		new Thread(block1).start();
		new Thread(block2).start();
	}

}