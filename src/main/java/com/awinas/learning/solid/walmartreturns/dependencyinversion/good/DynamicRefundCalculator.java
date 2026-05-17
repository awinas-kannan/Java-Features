package com.awinas.learning.solid.walmartreturns.dependencyinversion.good;

/**
 * ✅ Dynamic refund calculation based on return window.
 * Can be swapped in without modifying ReturnService.
 */
public class DynamicRefundCalculator implements RefundCalculator {

    @Override
    public double calculate(double itemPrice, int daysSincePurchase) {
        if (daysSincePurchase <= 15) {
            return itemPrice; // full refund within 15 days
        } else if (daysSincePurchase <= 30) {
            return itemPrice * 0.95; // 5% fee after 15 days
        } else if (daysSincePurchase <= 60) {
            return itemPrice * 0.85; // 15% fee after 30 days
        } else {
            return itemPrice * 0.70; // 30% fee after 60 days
        }
    }
}
