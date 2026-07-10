package com.awinas.learning.Java.Java15;

public class MainSealed {
	public static void main(String[] args) {

		Account s = new Account();
		System.out.println(s.getClass().isSealed());
		System.out.println(s.getClass().getPermittedSubclasses());
		
	}
}
