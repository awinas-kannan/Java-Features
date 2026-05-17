package com.awinas.learning.solid.walmartreturns.dependencyinversion.good;

public interface RefundCalculator {
    double calculate(double itemPrice, int daysSincePurchase);
}
