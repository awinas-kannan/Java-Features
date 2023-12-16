package com.awinas.learning.solid.dipendencyinversion.good;

public class EmailService implements MessageService {

	@Override
	public void send() {
		System.out.println("sending mail");
	}

}
