package com.awinas.learning.designpatterns.paymentapp.adapter;

import com.awinas.learning.designpatterns.paymentapp.sdk.StripeSDK;

/**
 * ADAPTER PATTERN - Stripe Refund Adapter
 *
 * Translates:
 *   RefundService.refund(transactionId, amount)
 *     -> StripeSDK.stripeRefund(chargeId, amount)
 *
 * Note: Stripe calls the transaction ID a "chargeId" - the adapter hides that detail.
 */
public class StripeRefundAdapter implements RefundService {

    private final StripeSDK stripeSDK;

    public StripeRefundAdapter(StripeSDK stripeSDK) {
        this.stripeSDK = stripeSDK;
    }

    @Override
    public boolean refund(String transactionId, double amount) {
        // Adapt: Stripe calls its transaction ID a "chargeId" - we just pass it through
        return stripeSDK.stripeRefund(transactionId, amount);
    }
}
