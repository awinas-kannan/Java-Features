package com.awinas.learning.solid.walmartreturns.openclosed.good;

public class MarketplaceRefund implements RefundStrategy {

    private static final double MARKETPLACE_DEDUCTION = 0.20;

    @Override
    public double calculateRefund(double itemPrice, int daysSincePurchase) {
        return itemPrice * (1 - MARKETPLACE_DEDUCTION);
    }

    @Override
    public String getReturnType() {
        return "MARKETPLACE";
    }
}
