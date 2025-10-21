package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency;

public class Hello extends Parent implements Runnable {

	@Override
	public void run() {
		try {
			show("*** Hello ***");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
