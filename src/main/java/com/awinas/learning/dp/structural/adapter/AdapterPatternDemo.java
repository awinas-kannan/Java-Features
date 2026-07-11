package com.awinas.learning.dp.structural.adapter;

import com.awinas.learning.dp.structural.adapter.thirdparty.PaypalSDK;
import com.awinas.learning.dp.structural.adapter.thirdparty.RazorpaySDK;
import com.awinas.learning.dp.structural.adapter.thirdparty.StripeSDK;

public class AdapterPatternDemo {

    public static void main(String[] args) {

        // Our code always talks to PaymentService — never to SDK directly
        // Just swap the adapter to switch provider

        // ---- Razorpay ----
        System.out.println("=== Razorpay ===");
        PaymentService razorpay = new RazorpayAdapter(new RazorpaySDK());
        String rzpTxn = razorpay.pay(499.0, "UPI");
        razorpay.refund(rzpTxn, 499.0);

        // ---- Stripe ----
        System.out.println("\n=== Stripe ===");
        PaymentService stripe = new StripeAdapter(new StripeSDK());
        String strTxn = stripe.pay(1299.0, "CARD");
        stripe.refund(strTxn, 1299.0);

        // ---- PayPal ----
        System.out.println("\n=== PayPal ===");
        PaymentService paypal = new PaypalAdapter(new PaypalSDK());
        String pplTxn = paypal.pay(850.0, "NETBANKING");
        paypal.refund(pplTxn, 850.0);
    }
}
