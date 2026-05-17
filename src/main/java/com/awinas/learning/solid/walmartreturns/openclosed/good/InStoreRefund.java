package com.awinas.learning.solid.walmartreturns.openclosed.good;

public class InStoreRefund implements RefundStrategy {

    private static final int FULL_REFUND_WINDOW = 30;
    private static final double RESTOCKING_FEE = 0.15;

    @Override
    public double calculateRefund(double itemPrice, int daysSincePurchase) {
        if (daysSincePurchase <= FULL_REFUND_WINDOW) {
            return itemPrice;
        }
        return itemPrice * (1 - RESTOCKING_FEE);
    }

    @Override
    public String getReturnType() {
        return "IN_STORE";
    }
}
