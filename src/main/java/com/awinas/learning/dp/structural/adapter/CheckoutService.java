package com.awinas.learning.dp.structural.adapter;

// CheckoutService depends ONLY on PaymentService interface.
// It never imports RazorpaySDK, StripeSDK, or PaypalSDK.
// This is Dependency Inversion Principle in action.
public class CheckoutService {

    private final PaymentService paymentService;

    public CheckoutService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void placeOrder(String orderId, double amount, String method) {
        System.out.println("[CheckoutService] Placing order: " + orderId);
        String txnId = paymentService.pay(amount, method);
        System.out.println("[CheckoutService] Order placed. TxnId: " + txnId);
    }

    public void cancelOrder(String orderId, String txnId, double amount) {
        System.out.println("[CheckoutService] Cancelling order: " + orderId);
        boolean success = paymentService.refund(txnId, amount);
        System.out.println("[CheckoutService] Refund status: " + success);
    }
}
