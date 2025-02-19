package com.awinas.learning.solid.openclosedprinciple.bad;

/*
 * Imagine we have a class that processes different types of payments (Credit Card, UPI, PayPal, etc.).
 * 
 * 
Why is this bad?
Closed for Extension: If we want to add a new payment method (e.g., Bitcoin), we have to modify the existing PaymentProcessor class.
Violation of SRP: The class is handling multiple responsibilities instead of delegating them.
High Maintenance: Every time a new payment method is added, we need to modify the class, increasing the risk of breaking existing code.
 */
class PaymentProcessor {
	void processPayment(String paymentType) {
		if (paymentType.equals("CreditCard")) {
			System.out.println("Processing credit card payment...");
		} else if (paymentType.equals("UPI")) {
			System.out.println("Processing UPI payment...");
		} else if (paymentType.equals("PayPal")) {
			System.out.println("Processing PayPal payment...");
		} else {
			System.out.println("Invalid payment method!");
		}
	}
}

/*
 * 
 * Good Design (Following OCP) We can use abstraction (interfaces) and
 * polymorphism to make the class open for extension but closed for
 * modification.
 * 
Why is this a Good Design?
✅ Open for Extension: If we want to add a new payment method (e.g., Bitcoin), we simply create a new class implementing Payment without modifying existing code.
✅ Closed for Modification: The PaymentProcessor class remains untouched when a new payment type is introduced.
✅ Better Maintainability: Each payment method follows Single Responsibility Principle (SRP), making the code cleaner and easier to manage.


 */

interface Payment {
	void process();
}

class CreditCardPayment implements Payment {
	public void process() {
		System.out.println("Processing credit card payment...");
	}
}

class UPIPayment implements Payment {
	public void process() {
		System.out.println("Processing UPI payment...");
	}
}

class PayPalPayment implements Payment {
	public void process() {
		System.out.println("Processing PayPal payment...");
	}
}

class PaymentProcessorGood {
	void processPayment(Payment payment) {
		payment.process();
	}
}

class Main {
	public static void main(String[] args) {
		PaymentProcessorGood processor = new PaymentProcessorGood();

		Payment creditCard = new CreditCardPayment();
		processor.processPayment(creditCard);

		Payment upi = new UPIPayment();
		processor.processPayment(upi);

		Payment payPal = new PayPalPayment();
		processor.processPayment(payPal);
	}
}
