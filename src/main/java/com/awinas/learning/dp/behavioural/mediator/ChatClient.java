package com.awinas.learning.dp.behavioural.mediator;

public class ChatClient {

	public static void main(String[] args) {
		ChatMediator mediator = new ChatMediatorImpl();
		User user1 = new UserImpl(mediator, "Awi");
		User user2 = new UserImpl(mediator, "Gnanam");
		User user3 = new UserImpl(mediator, "Fareed");
		User user4 = new UserImpl(mediator, "BP");
		User user5 = new UserImpl(mediator, "Aravind");
		mediator.addUser(user1);
		mediator.addUser(user2);
		mediator.addUser(user3);
		mediator.addUser(user4);
		mediator.addUser(user5);

		user1.send("Hi All");
		System.out.println("*******************");
		user5.send("Hello");
	}

}