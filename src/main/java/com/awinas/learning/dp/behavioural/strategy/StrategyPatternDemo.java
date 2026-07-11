package com.awinas.learning.dp.behavioural.strategy;

public class StrategyPatternDemo {

    public static void main(String[] args) {

        // ---- UPI ----
        System.out.println("=== UPI ===");
        CheckoutService checkout = new CheckoutService(new UpiStrategy("john@oksbi"));
        checkout.pay("ORD-001", 499.0);

        // ---- Card ----
        System.out.println("\n=== Card ===");
        checkout.setPaymentStrategy(new CardStrategy("4111111111111111", "12/27"));
        checkout.pay("ORD-002", 1299.0);

        // ---- Net Banking ----
        System.out.println("\n=== Net Banking ===");
        checkout.setPaymentStrategy(new NetBankingStrategy("HDFC"));
        checkout.pay("ORD-003", 850.0);
    }
}
