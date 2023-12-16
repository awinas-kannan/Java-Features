package com.awinas.learning.solid.dipendencyinversion.bad;


//Object created in processor
//hard to write test case
public class MessageProcessor {

	public void process() {
		EmailService service = new EmailService();
		service.send();

		SMSService service1 = new SMSService();
		service1.send();
	}
}
