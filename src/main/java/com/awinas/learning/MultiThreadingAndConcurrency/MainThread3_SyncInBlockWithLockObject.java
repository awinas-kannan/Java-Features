package com.awinas.learning.MultiThreadingAndConcurrency;

public class MainThread3_SyncInBlockWithLockObject {

	public static void main(String[] args) {
		final MathClass mathClass = new MathClass();
		 
        //first thread
        Runnable r = new Runnable() 
        {
            public void run() 
            {
                try {
                    mathClass.printNumbers(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
       
        new Thread(r, "ONE").start();
        
        //This below thing will be executed only after thread 
        //beacuse both uses same lock object
        //Thread one is locked
        //So thread two cant access that
        new Thread(r, "TWO").start();
	}

}
