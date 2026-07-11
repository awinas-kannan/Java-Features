package com.awinas.learning.dp.paymentapp.abstractfactory;

import com.awinas.learning.dp.paymentapp.adapter.PaymentService;
import com.awinas.learning.dp.paymentapp.adapter.RefundService;
import com.awinas.learning.dp.paymentapp.adapter.StripeAdapter;
import com.awinas.learning.dp.paymentapp.sdk.StripeSDK;

/**
 * ABSTRACT FACTORY PATTERN - Stripe Concrete Factory
 *
 * Creates one StripeAdapter instance and returns it as both
 * PaymentService and RefundService.
 *
 * Both methods return the same adapter — which is correct because:
 *   - StripeAdapter implements both interfaces
 *   - Both share the same StripeSDK instance (consistent config/credentials)
 */
public class StripeFactory implements PaymentProviderFactory {

    private final StripeAdapter stripeAdapter = new StripeAdapter(new StripeSDK());

    @Override
    public PaymentService createPaymentService() {
        return stripeAdapter;
    }

    @Override
    public RefundService createRefundService() {
        return stripeAdapter;
    }
}
