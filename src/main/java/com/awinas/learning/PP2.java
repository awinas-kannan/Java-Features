package com.awinas.learning;

public class PP2 {

	public int var;

	public PP2(int var) {
		var = var;
	}

	public static void main(String[] args) {
		System.out.println(new PP2(6).var);
		
		A x = new B();
		System.out.println(x.s);
	}
	

}


class A{
	int s =100;
}


class B extends A{
	int s =200;
}