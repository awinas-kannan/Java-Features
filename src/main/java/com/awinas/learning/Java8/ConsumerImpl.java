package com.awinas.learning.Java8;

import java.util.function.Consumer;

public class ConsumerImpl<T> implements Consumer<T> {

	public ConsumerImpl() {
		System.out.println("inside consumer impl");
	}

	@Override
	public void accept(T t) {
		System.out.print(t + " ");

	}

}
