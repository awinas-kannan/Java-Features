package com.awinas.learning.MultiThreadingAndConcurrency.ExecuterFramework;

public class RunnableTask implements Runnable {

	int num = 0;

	public RunnableTask(int i) {
		this.num = i;
	}

	@Override
	public void run() {
		System.out.println();
		System.out.println("Task " + num + " running");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = (num * 100); i <= (num * 100) + 99; i++) {
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.println("Task " + num + " completed");

	}

}
