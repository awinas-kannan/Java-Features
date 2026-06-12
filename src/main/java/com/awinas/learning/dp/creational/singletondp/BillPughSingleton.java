package com.awinas.learning.dp.creational.singletondp;
public class BillPughSingleton {

    private BillPughSingleton(){
    	System.out.println("Creating BillPughSingleton Instance");
    }
    
    private static class SingletonHelper{
    	
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }
    
    public static BillPughSingleton getInstance(){
    	System.out.println("getting BillPughSingleton");
        return SingletonHelper.INSTANCE;
    }
}