package com.awinas.learning.solid.dipendencyinversion.bad;

public class BadDesign {

	public static void main(String[] args) {
		MessageProcessor p = new MessageProcessor();
		p.process();

	}

}
