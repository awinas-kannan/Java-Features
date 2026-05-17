package com.awinas.learning.solid.walmartreturns.singleresponsibility.good;

/*
 * ============================================================================
 * SINGLE RESPONSIBILITY PRINCIPLE (SRP) - GOOD EXAMPLE
 * ============================================================================
 *
 * Definition:
 *   "A class should have only ONE reason to change."
 *
 * ============================================================================
 * GOOD DESIGN — Each class has exactly ONE responsibility:
 * ============================================================================
 *
 *   ReturnRequest         → Data holder (represents the return request entity)
 *   ReturnValidator       → Validates eligibility rules
 *   RefundProcessor       → Calculates and processes refund
 *   NotificationService   → Sends customer notifications
 *   ReturnAuditLogger     → Logs for audit/analytics
 *   ReturnOrchestrator    → Coordinates the workflow (thin orchestration layer)
 *
 * ============================================================================
 * WHY IS THIS GOOD?
 * ============================================================================
 *
 * ✅ Single Reason to Change:
 *    - If refund rules change → only RefundProcessor changes.
 *    - If notification channel changes → only NotificationService changes.
 *    - If validation rules change → only ReturnValidator changes.
 *    - If audit format changes → only ReturnAuditLogger changes.
 *
 * ✅ Easy to Unit Test:
 *    - Each class can be tested independently with mocks.
 *
 * ✅ Team-friendly:
 *    - Different developers can work on different classes in parallel.
 *
 * ✅ Low coupling, high cohesion.
 * ============================================================================
 */
public class ReturnRequest {

    private final String orderId;
    private final String customerId;
    private final String itemName;
    private final double itemPrice;
    private final int daysSincePurchase;

    public ReturnRequest(String orderId, String customerId, String itemName,
                         double itemPrice, int daysSincePurchase) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.daysSincePurchase = daysSincePurchase;
    }

    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public String getItemName() { return itemName; }
    public double getItemPrice() { return itemPrice; }
    public int getDaysSincePurchase() { return daysSincePurchase; }
}
