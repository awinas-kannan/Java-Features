package com.awinas.learning.oop.interfaces;

public class DefaultUtilizser implements Idefault {

	@Override
	public void normalMethod() {
		System.out.println("Normal method in Child");

	}

	@Override
	public void defMethod() {
		System.out.println("Default method in Child");

	}

}
