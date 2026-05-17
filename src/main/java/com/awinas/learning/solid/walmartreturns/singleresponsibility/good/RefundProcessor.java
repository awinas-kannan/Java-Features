package com.awinas.learning.solid.walmartreturns.singleresponsibility.good;

/**
 * SOLE RESPONSIBILITY: Calculate and process the refund amount.
 *
 * This class ONLY changes if refund business rules change
 * (e.g., restocking fee percentage changes, new refund tiers introduced).
 */
public class RefundProcessor {

    private static final int FULL_REFUND_WINDOW_DAYS = 30;
    private static final double RESTOCKING_FEE_PERCENT = 0.15;

    public void processRefund(ReturnRequest request) {
        if (request.getDaysSincePurchase() <= FULL_REFUND_WINDOW_DAYS) {
            System.out.println("💰 Full refund of $" + request.getItemPrice() +
                    " to original payment method.");
        } else {
            double storeCredit = request.getItemPrice() * (1 - RESTOCKING_FEE_PERCENT);
            System.out.printf("💰 Store credit of $%.2f issued (%.0f%% restocking fee applied).%n",
                    storeCredit, RESTOCKING_FEE_PERCENT * 100);
        }
    }
}
