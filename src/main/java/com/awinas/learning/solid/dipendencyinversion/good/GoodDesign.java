package com.awinas.learning.solid.dipendencyinversion.good;

import com.awinas.learning.solid.dipendencyinversion.good.MessageProcessor;

public class GoodDesign {

	public static void main(String[] args) {
		MessageService email = new EmailService();
		MessageProcessor p1 = new MessageProcessor(email);
		p1.process();

		MessageService sms = new SMSService();
		MessageProcessor p2 = new MessageProcessor(sms);
		p2.process();
	}

}
