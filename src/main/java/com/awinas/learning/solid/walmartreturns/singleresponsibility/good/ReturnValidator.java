package com.awinas.learning.solid.walmartreturns.singleresponsibility.good;

/**
 * SOLE RESPONSIBILITY: Validate whether a return request is eligible.
 *
 * This class ONLY changes if the return eligibility rules change
 * (e.g., return window extended from 90 to 120 days, new item-condition checks added).
 */
public class ReturnValidator {

    private static final int MAX_RETURN_WINDOW_DAYS = 90;

    public boolean isEligible(ReturnRequest request) {
        if (request.getDaysSincePurchase() > MAX_RETURN_WINDOW_DAYS) {
            System.out.println("❌ Return rejected: Beyond " + MAX_RETURN_WINDOW_DAYS + "-day return window.");
            return false;
        }
        if (request.getItemPrice() <= 0) {
            System.out.println("❌ Return rejected: Invalid item price.");
            return false;
        }
        System.out.println("✅ Return eligible for order: " + request.getOrderId());
        return true;
    }
}
