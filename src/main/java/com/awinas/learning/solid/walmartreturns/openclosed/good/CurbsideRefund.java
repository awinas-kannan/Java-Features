package com.awinas.learning.solid.walmartreturns.openclosed.good;

public class CurbsideRefund implements RefundStrategy {

    @Override
    public double calculateRefund(double itemPrice, int daysSincePurchase) {
        return itemPrice; // always full refund for curbside returns
    }

    @Override
    public String getReturnType() {
        return "CURBSIDE";
    }
}
