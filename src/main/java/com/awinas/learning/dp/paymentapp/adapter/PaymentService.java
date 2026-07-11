package com.awinas.learning.dp.paymentapp.adapter;

import com.awinas.learning.dp.paymentapp.strategy.PaymentStrategy;

/**
 * ADAPTER PATTERN - Target Interface (Payment)
 *
 * Our application always calls this interface.
 * It never directly calls RazorpaySDK or StripeSDK.
 * Adapters translate this call into whatever the SDK expects.
 */
public interface PaymentService {
    String pay(double amount, PaymentStrategy strategy);
}
