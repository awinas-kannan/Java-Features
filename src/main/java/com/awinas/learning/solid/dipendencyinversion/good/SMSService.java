package com.awinas.learning.solid.dipendencyinversion.good;

public class SMSService implements MessageService {

	@Override
	public void send() {
		System.out.println("sending sms");
	}

}
