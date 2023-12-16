package com.awinas.learning;

public class InstanceInitialization extends ParentInstanceInit{

	{
		System.out.println("instance initializer block 1");
	}

	private InstanceInitialization() {

		System.out.println("Inside constructor");
	}

	public static void main(String[] args) {
		
		new InstanceInitialization();
		
		System.out.println("Main Method");
	}

	{
		System.out.println("instance initializer block 2");
	}
}
