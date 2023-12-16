package com.awinas.learning.interview.nielsen;

public class InheritanceExampleSubClass extends InheritanceExample {

	public void method1() {

		System.out.println("This is method1 of InheritanceExample subclass");
	}

	public void method2() {
		this.method2();
		// super.method2();
		System.out.println("This is method2 of InheritanceExample subclass");
	}

	public static void main(String args[]) {
		// run time class case exception
		// InheritanceExampleSubClass inh1 = (InheritanceExampleSubClass) new
		// InheritanceExample();
		InheritanceExampleSubClass inh2 = new InheritanceExampleSubClass();

		inh2.method2();
	}

}