package com.awinas.learning.MultiThreadingAndConcurrency.ExecuterFramework;

import java.util.concurrent.Callable;

public class CallableTask implements Callable<String> {

	int num = 0;

	public CallableTask(int i) {
		this.num = i;
	}

	@Override
	public String call() throws Exception {
		System.out.println();
		System.out.println("Task " + num + " running -> " + Thread.currentThread().getName());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("Interreption -> " + Thread.currentThread().getName());
			//e.printStackTrace();
		}
		for (int i = (num * 100); i <= (num * 100) + 99; i++) {
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.println("Task " + num + " completed");
		return "Task " + num + " completed";
	}

}
