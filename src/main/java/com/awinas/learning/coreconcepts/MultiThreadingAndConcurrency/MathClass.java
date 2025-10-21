package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency;

//Using sync in block of code with lock object (not in method directly)
public class MathClass {

	private Object lock = new Object();

	void printNumbers(int n) throws InterruptedException {

		// Both are correct
		synchronized (lock)
		// synchronized (this)
		{
			for (int i = 1; i <= n; i++) {
				System.out.println(Thread.currentThread().getName() + " :: " + i);
				Thread.sleep(500);
			}
		}
	}
}