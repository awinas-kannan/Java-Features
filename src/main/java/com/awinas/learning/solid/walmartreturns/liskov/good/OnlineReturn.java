package com.awinas.learning.solid.walmartreturns.liskov.good;

/**
 * Online returns support BOTH refund AND shipping — implements both interfaces.
 * This class fully honors both contracts without any exceptions or hacks.
 */
public class OnlineReturn implements Refundable, Shippable {

    private final String orderId;
    private final double refundAmount;

    public OnlineReturn(String orderId, double refundAmount) {
        this.orderId = orderId;
        this.refundAmount = refundAmount;
    }

    @Override
    public void processRefund() {
        System.out.println("💰 Online refund of $" + refundAmount +
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

    @Override
    public void generateShippingLabel() {
        System.out.println("📦 Shipping label generated for online return: " + orderId);
    }

    @Override
    public String getTrackingInfo() {
        return "TRACK-" + orderId + "-ONLINE";
    }
}
