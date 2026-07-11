package com.awinas.learning.dp.paymentapp.service;

import com.awinas.learning.dp.paymentapp.abstractfactory.PaymentProviderFactory;
import com.awinas.learning.dp.paymentapp.adapter.PaymentService;
import com.awinas.learning.dp.paymentapp.adapter.RefundService;
import com.awinas.learning.dp.paymentapp.factory.PaymentFactory;
import com.awinas.learning.dp.paymentapp.strategy.PaymentStrategy;

/**
 * OrderService - The Business Layer
 *
 * This class shows all 4 patterns working together.
 * Notice what OrderService does NOT know about:
 *   - RazorpaySDK or StripeSDK          (hidden by Adapter)
 *   - RazorpayPaymentAdapter etc.        (hidden by Abstract Factory)
 *   - RazorpayFactory or StripeFactory   (hidden by Factory)
 *   - UpiStrategy / CardStrategy internals (hidden by Strategy interface)
 *
 * It only depends on 3 abstractions:
 *   PaymentFactory, PaymentService/RefundService, PaymentStrategy
 *
 * Flow:
 *   1. PaymentFactory.getFactory(provider)  -> Factory Pattern
 *   2. factory.createPaymentService()       -> Abstract Factory Pattern
 *   3. paymentService.pay(amount, strategy) -> Adapter + Strategy Pattern
 */
public class OrderService {

    public String processPayment(String orderId, double amount, String provider, PaymentStrategy strategy) {
        System.out.println("\n[OrderService] Processing payment for Order: " + orderId
                + " | Amount: $" + amount + " | Provider: " + provider
                + " | Method: " + strategy.getMethod());

        // Step 1: Factory Pattern - get the right provider factory by name
        PaymentProviderFactory factory = PaymentFactory.getFactory(provider);

        // Step 2: Abstract Factory - create payment service (adapter internally)
        PaymentService paymentService = factory.createPaymentService();

        // Step 3: Adapter + Strategy - pay() call is uniform regardless of SDK
        String transactionId = paymentService.pay(amount, strategy);

        System.out.println("[OrderService] Payment SUCCESS | Order: " + orderId
                + " | TxnId: " + transactionId);
        return transactionId;
    }

    public void processRefund(String orderId, String transactionId, double amount, String provider) {
        System.out.println("\n[OrderService] Processing refund for Order: " + orderId
                + " | TxnId: " + transactionId + " | Provider: " + provider);

        // Factory + Abstract Factory to get refund service
        PaymentProviderFactory factory = PaymentFactory.getFactory(provider);
        RefundService refundService = factory.createRefundService();

        boolean success = refundService.refund(transactionId, amount);

        System.out.println("[OrderService] Refund " + (success ? "SUCCESS" : "FAILED")
                + " | Order: " + orderId);
    }
}
