package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency;

public class DeadlockPrinter {

	private static final Object Lock1 = new Object(); // Represents Printer 1
	private static final Object Lock2 = new Object(); // Represents Printer 2

	public static void main(String[] args) {
		Thread t1 = new Thread(() -> {
			synchronized (Lock1) { // Employee A acquires Printer 1
				System.out.println("Thread 1: Holding Lock1...");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				System.out.println("Thread 1: Waiting for Lock2...");
				synchronized (Lock2) { // Employee A waits for Printer 2
					System.out.println("Thread 1: Acquired Lock2");
				}
			}
		});

		Thread t2 = new Thread(() -> {
			synchronized (Lock2) { // Employee B acquires Printer 2
				System.out.println("Thread 2: Holding Lock2...");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				System.out.println("Thread 2: Waiting for Lock1...");
				synchronized (Lock1) { // Employee B waits for Printer 1
					System.out.println("Thread 2: Acquired Lock1");
				}
			}
		});

		t1.start();
		t2.start();
	}
}
