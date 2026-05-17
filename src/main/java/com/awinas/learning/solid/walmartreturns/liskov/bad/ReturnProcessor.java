package com.awinas.learning.solid.walmartreturns.liskov.bad;

/*
 * ============================================================================
 * LISKOV SUBSTITUTION PRINCIPLE (LSP) - BAD EXAMPLE
 * ============================================================================
 *
 * Definition:
 *   "Objects of a superclass should be replaceable with objects of a subclass
 *    WITHOUT breaking the application."
 *   — Barbara Liskov
 *
 * In simpler terms: If class B extends class A, then we should be able to use B
 * everywhere A is used WITHOUT unexpected behavior or exceptions.
 *
 * ============================================================================
 * WALMART RETURNS CONTEXT:
 * ============================================================================
 * Walmart has different return categories:
 *   - Regular Return       → Can be refunded and can generate a shipping label
 *   - In-Store Only Return → Can be refunded but CANNOT generate a shipping label
 *                            (customer must physically bring item to store)
 *   - Final Sale Item      → CANNOT be refunded at all (clearance items)
 *
 * ============================================================================
 * WHY IS THIS BAD?
 * ============================================================================
 *
 * ❌ InStoreOnlyReturn extends ReturnProcessor but throws UnsupportedOperationException
 *    for generateShippingLabel(). This BREAKS the contract of the parent class.
 *
 * ❌ FinalSaleReturn extends ReturnProcessor but throws exceptions for BOTH
 *    processRefund() AND generateShippingLabel(). It can't actually be "returned"!
 *
 * ❌ Any code that accepts a ReturnProcessor and calls generateShippingLabel()
 *    will CRASH at runtime if given an InStoreOnlyReturn or FinalSaleReturn.
 *
 * ❌ The subclasses CANNOT be substituted for the parent without breaking things.
 *    This violates LSP.
 *
 * ❌ Real-world impact: A generic "process all returns" loop will explode when
 *    it encounters an InStoreOnlyReturn in the list.
 * ============================================================================
 */
public class ReturnProcessor {

    protected String orderId;
    protected double refundAmount;

    public ReturnProcessor(String orderId, double refundAmount) {
        this.orderId = orderId;
        this.refundAmount = refundAmount;
    }

    public void processRefund() {
        System.out.println("💰 Refund of $" + refundAmount + " processed for order: " + orderId);
    }

    public void generateShippingLabel() {
        System.out.println("📦 Shipping label generated for return of order: " + orderId);
    }

    public String getStatus() {
        return "RETURN_INITIATED";
    }
}
