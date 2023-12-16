package com.awinas.learning.garbagecollection;

public class AnonymousObject {

	// to store object name
	String obj_name;

	public AnonymousObject(String obj_name) {
		this.obj_name = obj_name;
	}

	// Driver method
	public static void main(String args[]) {
		// anonymous object without reference id
		new AnonymousObject("t1");

		// calling garbage collector
		System.gc();
	}

	@Override

	protected void finalize() throws Throwable {
		System.out.println(this.obj_name + " successfully garbage collected");
	}

}
