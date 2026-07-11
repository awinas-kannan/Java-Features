package com.awinas.learning.dp.structural.proxy;

public class ProxyPatternDemo {

    public static void main(String[] args) {

        // Proxy wraps the real gateway — caller uses PaymentGateway interface
        // Caller has no idea a proxy is involved
        PaymentGateway gateway = new PaymentGatewayProxy(new RealPaymentGateway());

        // ---- Normal payment ----
        System.out.println("=== Normal Payment ===");
        String txn1 = gateway.pay("USER-001", 1500.0);

        // ---- Exceeds single transaction limit (> Rs.5000) ----
        System.out.println("\n=== Exceeds Single Txn Limit ===");
        String txn2 = gateway.pay("USER-001", 6000.0);
        System.out.println("Result: " + txn2);

        // ---- Accumulate to hit daily limit ----
        System.out.println("\n=== Approaching Daily Limit ===");
        gateway.pay("USER-001", 3000.0);
        gateway.pay("USER-001", 3000.0);
        gateway.pay("USER-001", 3000.0);   // this pushes over Rs.10,000 daily limit

        // ---- Refund ----
        System.out.println("\n=== Refund ===");
        gateway.refund(txn1, 1500.0);
    }
}
