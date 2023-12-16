package com.awinas.learning.MultiThreadingAndConcurrency.ExecuterFramework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecuterServiceSingleThreadMain {

	public static void main(String[] args) {

		ExecutorService es = Executors.newSingleThreadExecutor();

		Runnable t0 = new RunnableTask(0);
		Runnable t1 = new RunnableTask(1);
		Runnable t2 = new RunnableTask(2);

		es.execute(t0); // Runs the thread
		es.execute(t1);
		es.execute(t2);

		// This code will be executed before thread completion itself
		System.out.println("After executer service codes ");

		// this is to stop the execution of executer service once all threads are
		// executed

		// If commented you can notice , the program still runs in console
		// red dot
		es.shutdown();

	}

}
