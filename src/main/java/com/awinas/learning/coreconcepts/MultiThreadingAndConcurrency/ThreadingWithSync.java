
package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency;

import java.util.Arrays;

public class ThreadingWithSync {

	public static void main(String[] args) throws InterruptedException {
		String[] arr = { "1", "2", "3", "4", "5", "6" };
		HashMapProcessorSync hmp = new HashMapProcessorSync(arr);
		Thread t1 = new Thread(hmp, "t1");
		Thread t2 = new Thread(hmp, "t2");
		Thread t3 = new Thread(hmp, "t3");
		long start = System.currentTimeMillis();
		// start all the threads
		t1.start();
		t2.start();
		t3.start();
		// wait for threads to finish
		t1.join();
		t2.join();
		t3.join();
		System.out.println("Time taken= " + (System.currentTimeMillis() - start));
		// check the shared variable value now
		System.out.println(Arrays.asList(hmp.getMap()));
	}

}

class HashMapProcessorSync implements Runnable {

	private String[] strArr = null;
	private Object lock = new Object();

	public HashMapProcessorSync(String[] m) {
		this.strArr = m;
	}

	public String[] getMap() {
		return strArr;
	}

	@Override
	public void run() {
		processArr(Thread.currentThread().getName());
	}

	private void processArr(String name) {
		for (int i = 0; i < strArr.length; i++) {
			// process data and append thread name
			processSomething(i);
			addThreadName(i, name);
		}
	}

	
	//Block level sync with lock object
	private void addThreadName(int i, String name) {
	//	synchronized (HashMapProcessorSync) {
	//	synchronized (strArr) {
		synchronized (lock) {
			strArr[i] = strArr[i] + ":" + name;
		}
	}

	private void processSomething(int index) {
		// processing some job
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
