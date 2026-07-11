package com.awinas.learning.designpatterns.paymentapp.abstractfactory;

import com.awinas.learning.designpatterns.paymentapp.adapter.PaymentService;
import com.awinas.learning.designpatterns.paymentapp.adapter.RazorpayAdapter;
import com.awinas.learning.designpatterns.paymentapp.adapter.RefundService;
import com.awinas.learning.designpatterns.paymentapp.sdk.RazorpaySDK;

/**
 * ABSTRACT FACTORY PATTERN - Razorpay Concrete Factory
 *
 * Creates one RazorpayAdapter instance and returns it as both
 * PaymentService and RefundService.
 *
 * Both methods return the same adapter — which is correct because:
 *   - RazorpayAdapter implements both interfaces
 *   - Both share the same RazorpaySDK instance (consistent config/credentials)
 */
public class RazorpayFactory implements PaymentProviderFactory {

    private final RazorpayAdapter razorpayAdapter = new RazorpayAdapter(new RazorpaySDK());

    @Override
    public PaymentService createPaymentService() {
        return razorpayAdapter;
    }

    @Override
    public RefundService createRefundService() {
        return razorpayAdapter;
    }
}
