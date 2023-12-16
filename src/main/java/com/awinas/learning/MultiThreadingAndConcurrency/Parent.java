package com.awinas.learning.MultiThreadingAndConcurrency;

public class Parent {

	public int nonSyncCounter = 0;
	public int syncCounter = 0;

	public void show(String inp) throws InterruptedException {
		for (int i = 0; i < 5; i++) {
			System.out.println(inp);
			Thread.sleep(1000); // 1sec
		}
	}

	public void nonSyncIncreament() {
		System.out.println("Init non sync counter val " + nonSyncCounter);
		for (int i = 0; i < 10000; i++) {
			nonSyncCounter++;
		}
	}

	public synchronized void syncIncreament() {
		System.out.println("Init sync counter val " + syncCounter);
		for (int i = 0; i < 10000; i++) {
			syncCounter++;
		}
	}

}
