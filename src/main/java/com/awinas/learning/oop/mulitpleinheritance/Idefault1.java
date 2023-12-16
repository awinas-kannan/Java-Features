package com.awinas.learning.oop.mulitpleinheritance;

public interface Idefault1 {
	default void work() {
		System.out.println("work in default 1");
	}
	
	default int he() {
		return 10;
	}
}
