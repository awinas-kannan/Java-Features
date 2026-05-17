package com.awinas.learning.solid.walmartreturns.liskov.good;

/*
 * ============================================================================
 * LISKOV SUBSTITUTION PRINCIPLE (LSP) - GOOD EXAMPLE
 * ============================================================================
 *
 * Definition:
 *   "Objects of a superclass should be replaceable with objects of a subclass
 *    WITHOUT breaking the application."
 *
 * ============================================================================
 * GOOD DESIGN — Proper Hierarchy with Correct Abstractions:
 * ============================================================================
 *
 * Instead of forcing all return types into one class hierarchy, we split
 * capabilities into proper interfaces:
 *
 *   Refundable           → Anything that can be refunded
 *   Shippable            → Anything that supports shipping labels
 *   OnlineReturn         → Implements BOTH (refundable + shippable)
 *   InStoreReturn        → Implements ONLY Refundable (no shipping needed)
 *   NonReturnableItem    → Implements NEITHER (separate concept entirely)
 *
 * ============================================================================
 * WHY IS THIS GOOD?
 * ============================================================================
 *
 * ✅ No subclass throws UnsupportedOperationException — every class fully
 *    honors the contracts of the interfaces it implements.
 *
 * ✅ Code that accepts Refundable can safely call processRefund() on ANY
 *    implementation — InStoreReturn, OnlineReturn, CurbsideReturn, etc.
 *
 * ✅ Code that accepts Shippable can safely call generateShippingLabel() on
 *    ANY implementation — only classes that genuinely support shipping implement it.
 *
 * ✅ Substitution is SAFE: Any Refundable can replace another Refundable.
 *    Any Shippable can replace another Shippable. No surprises.
 *
 * ✅ NonReturnableItem is a completely separate concept — not forced into
 *    the return hierarchy where it doesn't belong.
 * ============================================================================
 */
public interface Refundable {

    void processRefund();

    double getRefundAmount();

    String getOrderId();
}
