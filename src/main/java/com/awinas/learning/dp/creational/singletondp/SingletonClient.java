package com.awinas.learning.dp.creational.singletondp;

public class SingletonClient {

	public static void main(String[] args) {

		EagerInitializedSingleton.getInstance();
		// After EagerInitializedSingleton class loaded .
		// Check output
		EagerInitializedSingleton.getInstance();

		StaticBlockSingleton.getInstance();
		LazyInitializedSingleton.getInstance();
		ThreadSafeSingleton.getInstance();
		// ThreadSafeSingletonUsingDoubleLocking.getInstance();
		// ThreadSafeSingletonUsingDoubleLocking.getInstance();

		BillPughSingleton.getInstance();
		BillPughSingleton.getInstance();

		System.out.println("*******************");

		Runnable run = new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ThreadSafeSingletonUsingDoubleLocking.getInstance();
			}
		};

		Runnable run2 = new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ThreadSafeSingletonUsingDoubleLocking.getInstance();
			}
		};

		Thread t = new Thread(run);
		Thread t1 = new Thread(run2);
		t.start();
		t1.start();

	}

}
