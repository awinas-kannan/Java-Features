package com.awinas.learning.garbagecollection;

//https://www.geeksforgeeks.org/how-to-make-object-eligible-for-garbage-collection/
public class ReassigningTheReferenceVariable {

	// to store object name
	String obj_name;

	public ReassigningTheReferenceVariable(String obj_name) {
		this.obj_name = obj_name;
	}

	// Driver method
	public static void main(String args[]) {

		ReassigningTheReferenceVariable t1 = new ReassigningTheReferenceVariable("t1");
		ReassigningTheReferenceVariable t2 = new ReassigningTheReferenceVariable("t2");

		// t1 now referred to t2
		t1 = t2;

		// calling garbage collector
		System.gc();
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println(this.obj_name + " successfully garbage collected");
	}

}
