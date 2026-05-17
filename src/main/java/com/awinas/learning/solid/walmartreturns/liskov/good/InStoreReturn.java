package com.awinas.learning.solid.walmartreturns.liskov.good;

/**
 * In-store returns support refund but NOT shipping — implements only Refundable.
 * No UnsupportedOperationException, no broken contract, no surprise exceptions.
 */
public class InStoreReturn implements Refundable {

    private final String orderId;
    private final double refundAmount;

    public InStoreReturn(String orderId, double refundAmount) {
        this.orderId = orderId;
        this.refundAmount = refundAmount;
    }

    @Override
    public void processRefund() {
        System.out.println("💰 In-store refund of $" + refundAmount +
                " processed for order: " + orderId);
    }

    @Override
    public double getRefundAmount() {
        return refundAmount;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }
}
