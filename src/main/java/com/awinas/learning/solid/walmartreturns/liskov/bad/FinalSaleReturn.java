package com.awinas.learning.solid.walmartreturns.liskov.bad;

/**
 * ❌ VIOLATES LSP COMPLETELY: This subclass breaks BOTH parent contracts.
 * It cannot process refunds NOR generate shipping labels.
 * It should NOT be a subclass of ReturnProcessor at all.
 */
public class FinalSaleReturn extends ReturnProcessor {

    public FinalSaleReturn(String orderId, double refundAmount) {
        super(orderId, refundAmount);
    }

    @Override
    public void processRefund() {
        // ❌ VIOLATION! This is a final sale item — refunds are not allowed.
        throw new UnsupportedOperationException(
                "Final sale items cannot be refunded!");
    }

    @Override
    public void generateShippingLabel() {
        // ❌ VIOLATION! No shipping needed since no return is possible.
        throw new UnsupportedOperationException(
                "Final sale items cannot be returned!");
    }

    @Override
    public String getStatus() {
        return "NOT_RETURNABLE";
    }
}
