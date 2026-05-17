package com.awinas.learning.solid.walmartreturns.singleresponsibility.bad;

/*
 * ============================================================================
 * SINGLE RESPONSIBILITY PRINCIPLE (SRP) - BAD EXAMPLE
 * ============================================================================
 *
 * Definition:
 *   "A class should have only ONE reason to change."
 *   — Robert C. Martin
 *
 * In other words, a class should have only ONE job or responsibility.
 *
 * ============================================================================
 * WALMART RETURNS CONTEXT:
 * ============================================================================
 * When a customer initiates a return at Walmart, multiple things happen:
 *   1. The return request is validated (eligibility window, item condition)
 *   2. The refund is processed (to original payment method or store credit)
 *   3. A notification is sent to the customer (email/SMS)
 *   4. The return is logged for analytics/reporting
 *
 * ============================================================================
 * WHY IS THIS BAD?
 * ============================================================================
 *
 * ❌ This single class handles ALL of the above responsibilities:
 *    - Return validation logic
 *    - Refund processing logic
 *    - Customer notification logic
 *    - Audit/logging logic
 *
 * ❌ PROBLEMS:
 *    1. If the refund calculation changes (e.g., partial refund for damaged items),
 *       we modify THIS class — risking breaking notification or validation logic.
 *    2. If we switch from email to push notifications, we modify THIS class —
 *       risking breaking refund or validation logic.
 *    3. Hard to unit test — you can't test validation without also dealing with
 *       refund, notification, and logging dependencies.
 *    4. Multiple developers can't work on different concerns independently.
 *
 * ❌ This class has FOUR reasons to change — violating SRP.
 * ============================================================================
 */
public class ReturnService {

    private String orderId;
    private String customerId;
    private String itemName;
    private double itemPrice;
    private int daysSincePurchase;

    public ReturnService(String orderId, String customerId, String itemName,
                         double itemPrice, int daysSincePurchase) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.daysSincePurchase = daysSincePurchase;
    }

    // Responsibility 1: Validate return eligibility
    public boolean validateReturn() {
        if (daysSincePurchase > 90) {
            System.out.println("❌ Return rejected: Beyond 90-day return window.");
            return false;
        }
        if (itemPrice <= 0) {
            System.out.println("❌ Return rejected: Invalid item price.");
            return false;
        }
        System.out.println("✅ Return eligible for order: " + orderId);
        return true;
    }

    // Responsibility 2: Process the refund
    public void processRefund() {
        if (daysSincePurchase <= 30) {
            System.out.println("💰 Full refund of $" + itemPrice + " to original payment method.");
        } else {
            double storeCredit = itemPrice * 0.85;
            System.out.println("💰 Store credit of $" + storeCredit + " issued (15% restocking fee applied).");
        }
    }

    // Responsibility 3: Send notification to customer
    public void sendNotification() {
        System.out.println("📧 Email sent to customer " + customerId +
                ": Your return for '" + itemName + "' has been processed.");
        System.out.println("📱 SMS sent to customer " + customerId +
                ": Refund initiated for order " + orderId);
    }

    // Responsibility 4: Log for audit/analytics
    public void logReturn() {
        System.out.println("📊 [AUDIT LOG] Return processed - Order: " + orderId +
                ", Customer: " + customerId + ", Item: " + itemName +
                ", Amount: $" + itemPrice + ", Days Since Purchase: " + daysSincePurchase);
    }

    // Orchestrates everything — this class does TOO much
    public void initiateReturn() {
        if (validateReturn()) {
            processRefund();
            sendNotification();
            logReturn();
        }
    }

    public static void main(String[] args) {
        ReturnService returnService = new ReturnService(
                "WMT-ORD-98765", "CUST-1234", "Samsung TV 55\"", 499.99, 25);
        returnService.initiateReturn();

        System.out.println("\n--- Attempting expired return ---\n");

        ReturnService expiredReturn = new ReturnService(
                "WMT-ORD-11111", "CUST-5678", "Blender", 79.99, 120);
        expiredReturn.initiateReturn();
    }
}
