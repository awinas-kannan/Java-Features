package com.awinas.learning.solid.dipendencyinversion.bad;

public class OrderService {
	private CreditCardPayment creditCardPayment = new CreditCardPayment();
	private UPIPayment upiPayment = new UPIPayment();

	void processOrder(double amount, String paymentType) {
		if (paymentType.equals("credit")) {
			creditCardPayment.pay(amount);
		} else if (paymentType.equals("upi")) {
			upiPayment.pay(amount);
		}
	}
}


class CreditCardPayment {
	void pay(double amount) {
		System.out.println("Paid $" + amount + " using Credit Card.");
	}
}

class UPIPayment {
	void pay(double amount) {
		System.out.println("Paid $" + amount + " using UPI.");
	}
}

