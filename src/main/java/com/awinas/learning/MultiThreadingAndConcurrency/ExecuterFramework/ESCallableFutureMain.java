package com.awinas.learning.MultiThreadingAndConcurrency.ExecuterFramework;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ESCallableFutureMain {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		ExecutorService es = Executors.newFixedThreadPool(3);

		Callable<String> t0 = new CallableTask(0);
		Callable<String> t1 = new CallableTask(1);
		Callable<String> t2 = new CallableTask(2);

		Future<String> f0 = es.submit(t0); // Runs the thread
		Future<String> f1 = es.submit(t1);
		Future<String> f2 = es.submit(t2);
		Future<String> f3 = es.submit(new CallableTask(3));
		Future<String> f4 = es.submit(new CallableTask(4));
		Future<String> f5 = es.submit(new CallableTask(5));
		Future<String> f6 = es.submit(new CallableTask(6));
		Future<String> f7 = es.submit(new CallableTask(7));

		// This code will be executed bofore thread completion itself
		System.out.println("After executer service codes ");

		// Once f5 value is fetched ... Main thread execution is proceeded ( next line
		// is printed)
		System.out.println("** Result ** " + f5.get());

		System.out.println("**********************  Main End *************************");

		// this is to stop the execution of executer service once all threads are
		// executed
		// If commented you can notice , the program still runs in console
		// red dot
		es.shutdown();

	}

}
