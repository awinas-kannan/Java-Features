package com.awinas.learning.oop.mulitpleinheritance;

public class MiMain {

	public static void main(String[] args) {

		Interface1 i = new ConstantsAndAbstraceMethod();

		i.method();

		Interface2 i2 = new ConstantsAndAbstraceMethod();

		i2.method();

		System.out.println(i.param);
		System.out.println(i2.param);
		System.out.println(Interface1.param);
		System.out.println(Interface2.param);
	}

}
