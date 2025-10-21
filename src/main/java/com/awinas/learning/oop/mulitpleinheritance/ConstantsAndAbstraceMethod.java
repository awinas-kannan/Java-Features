package com.awinas.learning.oop.mulitpleinheritance;

public class ConstantsAndAbstraceMethod implements Interface1, Interface2 {

	@Override
	public void method() {

		System.out.println("Hi");
//		 System.out.println(param);
		System.out.println(Interface1.param);
		System.out.println(Interface2.param);
	}

	// The return type is incompatible with Interface1.method2()
	// Un comment method2() interface1 and check
	@Override
	public void method2() {

	}

	@Override
	public void method3(int i, int f) {
		System.out.println("properly loaded");
	}

	@Override
	public void method3(int i, float f) {
		System.out.println("properly loaded");
	}

}
