package com.awinas.learning.solid.walmartreturns.dependencyinversion.bad;

public class FixedRateRefundCalculator {

    public double calculate(double itemPrice) {
        return itemPrice * 0.90; // 10% restocking fee always
    }
}
