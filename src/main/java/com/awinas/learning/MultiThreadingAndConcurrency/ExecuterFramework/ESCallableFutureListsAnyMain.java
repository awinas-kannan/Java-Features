package com.awinas.learning.MultiThreadingAndConcurrency.ExecuterFramework;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Getting error .... dont know ...

//if only 3 task no error
//List<Callable<String>> cl = Arrays.asList(new CallableTask(0), new CallableTask(1), new CallableTask(2));

public class ESCallableFutureListsAnyMain {

	public static void main(String[] args) {

		ExecutorService es = Executors.newFixedThreadPool(3);

		List<Callable<String>> cl = Arrays.asList(new CallableTask(0), new CallableTask(1), new CallableTask(2),
				new CallableTask(3), new CallableTask(4));

		String futuresResults = null;
		try {
			futuresResults = es.invokeAny(cl);
		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Interreption -> " + Thread.currentThread().getName());			
		}

		// This will wait for any of one the thread to complete
		System.out.println("After executer service codes ");
		System.out.println("** Result ** " + futuresResults);

		System.out.println("**********************  Main End *************************");

		// this is to stop the execution of executer service once all threads are
		// executed
		// If commented you can notice , the program still runs in console
		// red dot
		es.shutdown();

	}

}
