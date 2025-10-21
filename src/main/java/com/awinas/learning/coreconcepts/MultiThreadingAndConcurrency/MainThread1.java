package com.awinas.learning.coreconcepts.MultiThreadingAndConcurrency;


public class MainThread1 {

	public static void main(String[] args) throws InterruptedException {

		Runnable hi = new Hi();
		Runnable hello = new Hello();

		Thread t1 = new Thread(hi, "Thread 1");
		Thread t2 = new Thread(hello, "Thread 2");

		long l = System.currentTimeMillis();
		System.out.println(l);
		t1.start();
		t2.start();
		System.out.println("Thread 1 status " + t1.isAlive());
		t1.join();
		t2.join();
		System.out.println("Thread 1 status  " + t1.isAlive());
		// If join is not given , then below sysout will be executed while processing
		// thread itself
		// If join is given , then it waits for the thread to complete and then execute
		// next statements
		System.out.println("With threading ......");
		System.out.println(System.currentTimeMillis() - l + " Sec");

		// with threading --- 5 sec
		// Without threading --- 10 sec

		Parent p1 = new Parent();
		Parent p2 = new Parent();

		l = System.currentTimeMillis();
		System.out.println(l);
		p1.show("### Hi ###");
		p2.show("### Hello ###");
		System.out.println("Without threading ......");
		System.out.println(System.currentTimeMillis() - l + " Sec");
	}

}
