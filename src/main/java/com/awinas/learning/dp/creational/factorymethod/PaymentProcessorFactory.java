package com.awinas.learning.dp.creational.factorymethod;

/**
 * Factory class that creates the appropriate PaymentProcessor
 * based on the payment type selected by the customer.
 */
public class PaymentProcessorFactory {

    public static PaymentProcessor createProcessor(String paymentType) {
        switch (paymentType.toUpperCase()) {
            case "CREDIT_CARD":
                return new CreditCardProcessor();
            case "UPI":
                return new UPIProcessor();
            case "WALLET":
                return new WalletProcessor();
            case "COD":
                return new CashOnDeliveryProcessor();
            default:
                throw new IllegalArgumentException("Unsupported payment type: " + paymentType);
        }
    }
}
