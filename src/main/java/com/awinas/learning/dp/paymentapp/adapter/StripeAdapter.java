package com.awinas.learning.dp.paymentapp.adapter;

import com.awinas.learning.dp.paymentapp.sdk.StripeSDK;
import com.awinas.learning.dp.paymentapp.strategy.PaymentStrategy;

/**
 * ADAPTER PATTERN - Stripe Adapter
 *
 * Implements both PaymentService and RefundService because:
 *   - Both use the same StripeSDK instance
 *   - Payment and refund on the same SDK change together
 *   - No reason to split — single class is the right level of abstraction
 *
 * Translates:
 *   pay(amount, strategy)      ->  stripeSDK.stripeCharge(amount, method, details)
 *   refund(txnId, amount)      ->  stripeSDK.stripeRefund(chargeId, amount)
 */
public class StripeAdapter implements PaymentService, RefundService {

    private final StripeSDK stripeSDK;

    public StripeAdapter(StripeSDK stripeSDK) {
        this.stripeSDK = stripeSDK;
    }

    @Override
    public String pay(double amount, PaymentStrategy strategy) {
        return stripeSDK.stripeCharge(amount, strategy.getMethod(), strategy.getDetails());
    }

    @Override
    public boolean refund(String transactionId, double amount) {
        // Note: Stripe calls the transaction ID a "chargeId" internally — adapter hides that
        return stripeSDK.stripeRefund(transactionId, amount);
    }
}
