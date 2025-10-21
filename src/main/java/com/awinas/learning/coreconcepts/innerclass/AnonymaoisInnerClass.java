package com.awinas.learning.coreconcepts.innerclass;

import java.util.HashSet;
import java.util.Set;

public class AnonymaoisInnerClass {

	public static void main(String[] args) {

		Testz t = new Testz() {

			@Override
			public void print() {
				System.out.println("hi bee ---" + b);
			}

			@Override
			void shout() {
				System.out.println("shouting........");

			}
		};
		t.print();
		t.printMethod();
		System.out.println(set);
	}

	// Annonymous inner class
	// The first brace creates a new anonymous inner class.
	static Set<String> set = new HashSet<String>() {

		{
			add("Ak");
			add(null);
			add("kann");
			add(null);
			add("Ak1");
			add("Ak");
		}
	};

}

abstract class Testz {
	private int a = 5;
	int b = 10;

	public abstract void print();

	abstract void shout();

	public void printMethod() {
		System.out.println(a);
	}

	// static abstract void pz();

}

class sucClass extends Testz {

	@Override
	public void print() {
		System.out.println("sub class print " + b);

	}

	@Override
	void shout() {
		System.out.println("sub class shout ");

	}

}