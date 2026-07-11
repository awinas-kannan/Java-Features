package com.awinas.learning.dp.structural.adapter;

import com.awinas.learning.dp.structural.adapter.thirdparty.PaypalSDK;
import com.awinas.learning.dp.structural.adapter.thirdparty.RazorpaySDK;
import com.awinas.learning.dp.structural.adapter.thirdparty.StripeSDK;

public class AdapterPatternDemo {

    public static void main(String[] args) {

        // CheckoutService never knows which SDK is underneath.
        // Swap the adapter = swap the provider. CheckoutService unchanged.

        // ---- Razorpay ----
        System.out.println("=== Razorpay ===");
        CheckoutService checkout1 = new CheckoutService(new RazorpayAdapter(new RazorpaySDK()));
        checkout1.placeOrder("ORD-001", 499.0, "UPI");

        // ---- Stripe ----
        System.out.println("\n=== Stripe ===");
        CheckoutService checkout2 = new CheckoutService(new StripeAdapter(new StripeSDK()));
        checkout2.placeOrder("ORD-002", 1299.0, "CARD");

        // ---- PayPal ----
        System.out.println("\n=== PayPal ===");
        CheckoutService checkout3 = new CheckoutService(new PaypalAdapter(new PaypalSDK()));
        checkout3.placeOrder("ORD-003", 850.0, "NETBANKING");

        // ---- Refund via Razorpay ----
        System.out.println("\n=== Refund ===");
        CheckoutService checkout4 = new CheckoutService(new RazorpayAdapter(new RazorpaySDK()));
        checkout4.cancelOrder("ORD-001", "RZP-999", 499.0);
    }
}
