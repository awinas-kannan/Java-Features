package com.awinas.learning.designpatterns.paymentapp.adapter;

import com.awinas.learning.designpatterns.paymentapp.sdk.RazorpaySDK;
import com.awinas.learning.designpatterns.paymentapp.strategy.PaymentStrategy;

/**
 * ADAPTER PATTERN - Razorpay Payment Adapter
 *
 * Translates:
 *   PaymentService.pay(amount, strategy)
 *     -> RazorpaySDK.razorpayPay(amount, method, details)
 *
 * The caller never knows it's talking to RazorpaySDK.
 */
public class RazorpayPaymentAdapter implements PaymentService {

    private final RazorpaySDK razorpaySDK;

    public RazorpayPaymentAdapter(RazorpaySDK razorpaySDK) {
        this.razorpaySDK = razorpaySDK;
    }

    @Override
    public String pay(double amount, PaymentStrategy strategy) {
        // Adapt: extract method and details from strategy, pass to Razorpay's signature
        return razorpaySDK.razorpayPay(amount, strategy.getMethod(), strategy.getDetails());
    }
}
