package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency;

//Add both sync and nonsync method in same thread run
//Give same result as sync and non sync in two two diff threads
public class MainThread2 {

	public static void main(String[] args) throws InterruptedException {

		Parent obj = new Parent();
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				obj.nonSyncIncreament();
				obj.syncIncreament();
			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				obj.nonSyncIncreament();
				obj.syncIncreament();
			}
		});

		t1.start();
		t2.start();
		t1.join();
		t2.join();

		System.out.println("Non Syncronized counter result " + obj.nonSyncCounter);
		System.out.println("Syncronized counter result " + obj.syncCounter);

		System.out.println(" ********************************* ");
		Thread t3 = new Thread(new Runnable() {

			@Override
			public void run() {
				obj.syncIncreament();
			}
		});

		Thread t4 = new Thread(new Runnable() {

			@Override
			public void run() {
				obj.syncIncreament();
			}
		});

		t3.start();
		t4.start();

		t3.join();
		t4.join();

		System.out.println("Syncronized counter result " + obj.syncCounter);
	}
}
