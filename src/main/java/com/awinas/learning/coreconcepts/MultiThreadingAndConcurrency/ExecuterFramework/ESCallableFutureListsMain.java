package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency.ExecuterFramework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ESCallableFutureListsMain {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// Press Ctrl + Shift + Esc to open Task Manager. Select the Performance tab to
		// see how many cores and logical processors your PC has
		System.out.println("No of processor ->" + Runtime.getRuntime().availableProcessors());
		ExecutorService es = Executors.newFixedThreadPool(3);

		// ThreadPoolExecutor tps = (ThreadPoolExecutor)
		// Executors.newFixedThreadPool(3);

		List<Callable<String>> cl = new ArrayList<>();
		cl = Arrays.asList(new CallableTask(0), new CallableTask(1), new CallableTask(2), new CallableTask(3),
				new CallableTask(4), new CallableTask(5), new CallableTask(6), new CallableTask(7),
				new CallableTask(8),new CallableTask(9));

		List<Future<String>> futuresResults = es.invokeAll(cl);

		System.out.println("After executer service codes ");
		// This will wait for all the thread to complete
		// Not only 3rs thread...
		System.out.println("** Result ** " + futuresResults.get(3).get());

		System.out.println("**********************  Main End *************************");

		// this is to stop the execution of executer service once all threads are
		// executed
		// If commented you can notice , the program still runs in console
		// red dot
		es.shutdown();

	}

}
