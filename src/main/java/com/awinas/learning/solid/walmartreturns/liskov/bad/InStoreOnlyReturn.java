package com.awinas.learning.solid.walmartreturns.liskov.bad;

/**
 * ❌ VIOLATES LSP: This subclass breaks the parent's contract by throwing
 * an exception for generateShippingLabel(). Code expecting a ReturnProcessor
 * will crash if it calls generateShippingLabel() on this object.
 */
public class InStoreOnlyReturn extends ReturnProcessor {

    public InStoreOnlyReturn(String orderId, double refundAmount) {
        super(orderId, refundAmount);
    }

    @Override
    public void processRefund() {
        System.out.println("💰 In-store refund of $" + refundAmount + " for order: " + orderId);
    }

    @Override
    public void generateShippingLabel() {
        // ❌ VIOLATION! Parent promises this works, but subclass throws exception.
        throw new UnsupportedOperationException(
                "In-store returns do not support shipping labels! Customer must return in person.");
    }
}
