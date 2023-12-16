package com.awinas.learning.interview.optum;
public class A {

	private static int count = 0;

	public A() throws Exception {

		System.out.println(this.getClass().getSimpleName());
		System.out.println(count++);
//		if (count > 1) {
//			throw new Exception("Already Created");
//		}
	}

}
