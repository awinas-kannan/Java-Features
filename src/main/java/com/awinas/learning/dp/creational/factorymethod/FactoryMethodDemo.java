package com.awinas.learning.dp.creational.factorymethod;

/**
 * Demo: Customer selects a payment method at checkout.
 * The factory creates the right processor without the client
 * knowing the concrete class.
 */
public class FactoryMethodDemo {

    public static void main(String[] args) {
        System.out.println("=== Customer 1: Pays via Credit Card ===");
        PaymentProcessor processor1 = PaymentProcessorFactory.createProcessor("CREDIT_CARD");
        processor1.processPayment(2499.00);

        System.out.println("\n=== Customer 2: Pays via UPI ===");
        PaymentProcessor processor2 = PaymentProcessorFactory.createProcessor("UPI");
        processor2.processPayment(899.00);

        System.out.println("\n=== Customer 3: Pays via Wallet ===");
        PaymentProcessor processor3 = PaymentProcessorFactory.createProcessor("WALLET");
        processor3.processPayment(499.00);

        System.out.println("\n=== Customer 4: Cash on Delivery ===");
        PaymentProcessor processor4 = PaymentProcessorFactory.createProcessor("COD");
        processor4.processPayment(1299.00);
    }
}
