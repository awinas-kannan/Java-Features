package com.awinas.learning.solid.dipendencyinversion.good;

//Object injected from outside
//Easy to handle
public class MessageProcessor {

	private MessageService serivce;

	public MessageProcessor(MessageService service) {
		this.serivce = service;
	}

	public void process() {
		serivce.send();
	}
}
