package com.awinas.learning.solid.walmartreturns.liskov.good;

/**
 * Curbside returns — customer brings item to curbside, gets refund.
 * Supports refund but NOT shipping. Perfectly substitutable for any Refundable.
 */
public class CurbsideReturn implements Refundable {

    private final String orderId;
    private final double refundAmount;

    public CurbsideReturn(String orderId, double refundAmount) {
        this.orderId = orderId;
        this.refundAmount = refundAmount;
    }

    @Override
    public void processRefund() {
        System.out.println("💰 Curbside refund of $" + refundAmount +
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
