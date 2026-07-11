package com.awinas.learning.dp.paymentapp;

import com.awinas.learning.dp.paymentapp.service.OrderService;
import com.awinas.learning.dp.paymentapp.strategy.CardStrategy;
import com.awinas.learning.dp.paymentapp.strategy.NetBankingStrategy;
import com.awinas.learning.dp.paymentapp.strategy.UpiStrategy;

/**
 * ================================================================
 *  PAYMENT DESIGN PATTERNS - Demo
 * ================================================================
 *
 *  Pattern          Used In
 *  ---------------  -----------------------------------------------
 *  Factory          PaymentFactory.getFactory("RAZORPAY")
 *                   -> Returns the right Abstract Factory by name
 *
 *  Abstract Factory RazorpayFactory / StripeFactory
 *                   -> Creates PaymentService + RefundService together
 *                   -> Guarantees both belong to the same provider
 *
 *  Adapter          RazorpayPaymentAdapter, StripeRefundAdapter, etc.
 *                   -> Translates our interface into SDK-specific calls
 *                   -> Hidden inside the Abstract Factory
 *
 *  Strategy         UpiStrategy, CardStrategy, NetBankingStrategy
 *                   -> Carries payment method details
 *                   -> Swappable without changing OrderService
 * ================================================================
 */
public class Main {

    public static void main(String[] args) {

        OrderService orderService = new OrderService();

        // ---- Demo 1: Razorpay + UPI ----
        System.out.println("\n======== DEMO 1: Razorpay + UPI ========");
        String txn1 = orderService.processPayment(
                "ORD-001", 499.0, "RAZORPAY",
                new UpiStrategy("john@oksbi")
        );

        // ---- Demo 2: Stripe + Card ----
        System.out.println("\n======== DEMO 2: Stripe + Card ========");
        String txn2 = orderService.processPayment(
                "ORD-002", 1299.0, "STRIPE",
                new CardStrategy("4111111111111111", "12/27", "123")
        );

        // ---- Demo 3: Razorpay + NetBanking ----
        System.out.println("\n======== DEMO 3: Razorpay + NetBanking ========");
        String txn3 = orderService.processPayment(
                "ORD-003", 850.0, "RAZORPAY",
                new NetBankingStrategy("HDFC", "John Doe")
        );

        // ---- Demo 4: Stripe + UPI ----
        // Same UPI strategy, different provider - no code change in strategy or OrderService
        System.out.println("\n======== DEMO 4: Stripe + UPI ========");
        String txn4 = orderService.processPayment(
                "ORD-004", 300.0, "STRIPE",
                new UpiStrategy("jane@paytm")
        );

        // ---- Demo 5: Refund via Razorpay ----
        System.out.println("\n======== DEMO 5: Refund - Razorpay ========");
        orderService.processRefund("ORD-001", txn1, 499.0, "RAZORPAY");

        // ---- Demo 6: Refund via Stripe ----
        System.out.println("\n======== DEMO 6: Refund - Stripe ========");
        orderService.processRefund("ORD-002", txn2, 1299.0, "STRIPE");
    }
}
