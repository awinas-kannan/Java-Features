package com.awinas.learning.interview.nielsen;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Threads {

	public static void main(String[] args) {

		Runnable run1 = new Runnable() {

			@Override
			public void run() {

				System.out.println("Some task run1 ");
			}
		};

		Runnable run2 = new Runnable() {

			@Override
			public void run() {

				System.out.println("Some task run2");
			}
		};

		Thread t = new Thread(run1);
		t.start();
		Thread t2 = new Thread(run2);
		t2.start();

		ExecutorService es = Executors.newFixedThreadPool(5);
		es.execute(() -> log("logging "));
	}

	private static Object log(String string) {
		//return System.out.println("awinas");
		return null;
	}

}
