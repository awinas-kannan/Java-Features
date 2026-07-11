package com.awinas.learning.designpatterns.paymentapp.adapter;

import com.awinas.learning.designpatterns.paymentapp.sdk.StripeSDK;
import com.awinas.learning.designpatterns.paymentapp.strategy.PaymentStrategy;

/**
 * ADAPTER PATTERN - Stripe Payment Adapter
 *
 * Translates:
 *   PaymentService.pay(amount, strategy)
 *     -> StripeSDK.stripeCharge(amount, paymentMethod, details)
 *
 * Same interface as RazorpayPaymentAdapter.
 * OrderService calls pay() and never knows which SDK runs underneath.
 */
public class StripePaymentAdapter implements PaymentService {

    private final StripeSDK stripeSDK;

    public StripePaymentAdapter(StripeSDK stripeSDK) {
        this.stripeSDK = stripeSDK;
    }

    @Override
    public String pay(double amount, PaymentStrategy strategy) {
        // Adapt: Stripe calls the method "stripeCharge" with "paymentMethod" param
        return stripeSDK.stripeCharge(amount, strategy.getMethod(), strategy.getDetails());
    }
}
