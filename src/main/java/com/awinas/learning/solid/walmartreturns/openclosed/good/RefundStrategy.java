package com.awinas.learning.solid.walmartreturns.openclosed.good;

/*
 * ============================================================================
 * OPEN/CLOSED PRINCIPLE (OCP) - GOOD EXAMPLE
 * ============================================================================
 *
 * Definition:
 *   "Software entities should be OPEN for extension but CLOSED for modification."
 *
 * ============================================================================
 * GOOD DESIGN — Using Strategy Pattern (Polymorphism):
 * ============================================================================
 *
 * We define a RefundStrategy interface, and each return type implements its own
 * refund calculation logic. When a new return type is added (e.g., LOCKER_RETURN),
 * we simply create a new class — we NEVER touch existing code.
 *
 * ============================================================================
 * WHY IS THIS GOOD?
 * ============================================================================
 *
 * ✅ OPEN for Extension:
 *    Adding "LockerReturnRefund" or "DronePickupRefund" requires ZERO changes
 *    to existing classes. Just create a new implementation of RefundStrategy.
 *
 * ✅ CLOSED for Modification:
 *    ReturnRefundProcessor never changes when new return types are added.
 *    Existing implementations (InStoreRefund, OnlineRefund, etc.) remain untouched.
 *
 * ✅ Each strategy is independently testable and deployable.
 *
 * ✅ Follows the "Plugin Architecture" — new behavior plugs in without surgery.
 * ============================================================================
 */
public interface RefundStrategy {

    double calculateRefund(double itemPrice, int daysSincePurchase);

    String getReturnType();
}
