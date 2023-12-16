package com.awinas.learning;

public class ParentInstanceInit {

	public ParentInstanceInit() {
		System.out.println("In ParentClass Constructor");
	}

	// Instance Initializer
	{
		System.out.println("In ParentClass Instance Initializer");
	}
	static {
		System.out.println("In ParentClass Static Instance Initializer");
	}
}
