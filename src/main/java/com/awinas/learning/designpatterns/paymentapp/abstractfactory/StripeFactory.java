package com.awinas.learning.designpatterns.paymentapp.abstractfactory;

import com.awinas.learning.designpatterns.paymentapp.adapter.PaymentService;
import com.awinas.learning.designpatterns.paymentapp.adapter.RefundService;
import com.awinas.learning.designpatterns.paymentapp.adapter.StripePaymentAdapter;
import com.awinas.learning.designpatterns.paymentapp.adapter.StripeRefundAdapter;
import com.awinas.learning.designpatterns.paymentapp.sdk.StripeSDK;

/**
 * ABSTRACT FACTORY PATTERN - Stripe Concrete Factory
 *
 * Creates the Stripe ecosystem:
 *   - StripePaymentAdapter (Adapter wrapping StripeSDK)
 *   - StripeRefundAdapter  (Adapter wrapping StripeSDK)
 *
 * Both share the same StripeSDK instance.
 */
public class StripeFactory implements PaymentProviderFactory {

    private final StripeSDK stripeSDK = new StripeSDK();

    @Override
    public PaymentService createPaymentService() {
        return new StripePaymentAdapter(stripeSDK);
    }

    @Override
    public RefundService createRefundService() {
        return new StripeRefundAdapter(stripeSDK);
    }
}
