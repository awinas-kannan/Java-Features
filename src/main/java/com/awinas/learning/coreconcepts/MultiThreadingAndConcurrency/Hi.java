package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency;

public class Hi extends Parent implements Runnable {

	@Override
	public void run() {
		try {
			show("*** Hi ***");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
