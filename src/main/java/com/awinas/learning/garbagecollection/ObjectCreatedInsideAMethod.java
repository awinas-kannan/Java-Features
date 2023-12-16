package com.awinas.learning.garbagecollection;

//https://www.geeksforgeeks.org/how-to-make-object-eligible-for-garbage-collection/

/* Java program to demonstrate that 
objects created inside a method will becomes
eligible for gc after method execution terminate */
public class ObjectCreatedInsideAMethod {

	// to store object name
	String obj_name;

	public ObjectCreatedInsideAMethod(String obj_name) {
		this.obj_name = obj_name;
	}

	static void show() {
		System.out.println("show");
		// object t1 inside method becomes unreachable when show() removed
		ObjectCreatedInsideAMethod t1 = new ObjectCreatedInsideAMethod("t1");
		display();
		// return t1;

	}

	static void display() {
		System.out.println("display");
		// object t2 inside method becomes unreachable when display() removed
		ObjectCreatedInsideAMethod t2 = new ObjectCreatedInsideAMethod("t2");
	}

	// Driver method
	public static void main(String args[]) {
		// calling show()
		// ObjectCreatedInsideAMethod x = show();
		show();
		System.out.println("Calling garbage collector");
		// calling garbage collector
		System.gc();
	}

	@Override
	/*
	 * Overriding finalize method to check which object is garbage collected
	 */
	protected void finalize() throws Throwable {
		System.out.println(this.obj_name + " successfully garbage collected");
	}

}
