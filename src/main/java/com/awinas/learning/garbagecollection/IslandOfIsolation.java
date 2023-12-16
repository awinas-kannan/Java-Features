package com.awinas.learning.garbagecollection;

public class IslandOfIsolation {

	IslandOfIsolation i;
	String name;

	public IslandOfIsolation(String name) {
		this.name = name;
	}

	public static void main(String[] args) {
		IslandOfIsolation t1 = new IslandOfIsolation("Obj 1");
		IslandOfIsolation t2 = new IslandOfIsolation("Obj 2");

		// Object of t1 gets a copy of t2
		t1.i = t2;

		// Object of t2 gets a copy of t1
		t2.i = t1;

		// Till now no object eligible for garbage collection
		// t1 internally has reference of t2
		t1 = null;

		// now two objects are eligible for
		// garbage collection
		// comment this below line and check o/p
		t2 = null;

		// calling garbage collector
		System.gc();

		// t1 = null; do not delete new IslandOfIsolation("Obj 1"); in gc
		// because t2.i has its reference
		// only t1 is made null (which means still new IslandOfIsolation("Obj 1")
		// is present in HEAP in form of t2.i

	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println("Finalize method called for " + this.name);
	}
}
