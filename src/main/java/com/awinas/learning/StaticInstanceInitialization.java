package com.awinas.learning;

public class StaticInstanceInitialization extends ParentInstanceInit {

	StaticInstanceInitialization() {
		super(); //By default jvm will add even if not provided
		System.out.println("Inside Constructor");
	}

	{
		System.out.println("instance initializer block 1");
	}

	static {
		System.out.println("Inside static Initializer block 1");
		System.out.println("Executed only once before all initializer & constructor contents");
	}

	{
		System.out.println("instance initializer block 2");
	}

	static {
		System.out.println("Inside static Initializer block 2");
	}

	public static void main(String[] args) {

		new StaticInstanceInitialization();
		System.out.println("<<<<<<<<<<Inside main>>>>>>>>>>>>");
		new StaticInstanceInitialization();
	}

}
