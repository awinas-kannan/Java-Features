package com.awinas.learning.solid.walmartreturns.openclosed.good;

public class OnlineRefund implements RefundStrategy {

    private static final int FULL_REFUND_WINDOW = 30;
    private static final double SHIPPING_DEDUCTION = 5.99;
    private static final double RESTOCKING_FEE = 0.15;

    @Override
    public double calculateRefund(double itemPrice, int daysSincePurchase) {
        double baseRefund = itemPrice - SHIPPING_DEDUCTION;
        if (daysSincePurchase <= FULL_REFUND_WINDOW) {
            return baseRefund;
        }
        return baseRefund * (1 - RESTOCKING_FEE);
    }

    @Override
    public String getReturnType() {
        return "ONLINE";
    }
}
