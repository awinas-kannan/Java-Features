package com.awinas.learning.solid.walmartreturns.openclosed.bad;

/*
 * ============================================================================
 * OPEN/CLOSED PRINCIPLE (OCP) - BAD EXAMPLE
 * ============================================================================
 *
 * Definition:
 *   "Software entities (classes, modules, functions) should be OPEN for extension
 *    but CLOSED for modification."
 *   — Bertrand Meyer
 *
 * Meaning: You should be able to add new behavior WITHOUT modifying existing code.
 *
 * ============================================================================
 * WALMART RETURNS CONTEXT:
 * ============================================================================
 * Walmart supports multiple return types, each with different refund rules:
 *   - IN_STORE return    → Full refund to original payment
 *   - ONLINE return      → Refund after item received at warehouse
 *   - CURBSIDE return    → Full refund, item picked up at curbside
 *   - MARKETPLACE return → Refund handled by 3rd party seller
 *
 * ============================================================================
 * WHY IS THIS BAD?
 * ============================================================================
 *
 * ❌ Every time Walmart adds a new return channel (e.g., "LOCKER_RETURN",
 *    "DRONE_PICKUP"), we MUST modify this class by adding another if-else branch.
 *
 * ❌ Modifying existing code risks introducing bugs in already-working return types.
 *
 * ❌ This class grows unboundedly — imagine 15 return types with complex logic each.
 *
 * ❌ Violates OCP: The class is NOT closed for modification.
 * ============================================================================
 */
public class ReturnRefundCalculator {

    public double calculateRefund(String returnType, double itemPrice, int daysSincePurchase) {

        if (returnType.equals("IN_STORE")) {
            if (daysSincePurchase <= 30) {
                return itemPrice;
            } else {
                return itemPrice * 0.85;
            }

        } else if (returnType.equals("ONLINE")) {
            if (daysSincePurchase <= 30) {
                return itemPrice - 5.99; // shipping deduction
            } else {
                return (itemPrice - 5.99) * 0.85;
            }

        } else if (returnType.equals("CURBSIDE")) {
            return itemPrice; // always full refund for curbside

        } else if (returnType.equals("MARKETPLACE")) {
            return itemPrice * 0.80; // marketplace seller absorbs 20%

        } else {
            throw new IllegalArgumentException("Unknown return type: " + returnType);
        }

        // ❌ If we add "LOCKER_RETURN", we MUST modify this method.
        // ❌ If we add "DRONE_PICKUP", we MUST modify this method again.
        // ❌ This will keep growing and become unmaintainable.
    }

    public static void main(String[] args) {
        ReturnRefundCalculator calculator = new ReturnRefundCalculator();

        System.out.println("In-Store (15 days): $" + calculator.calculateRefund("IN_STORE", 100.0, 15));
        System.out.println("Online (45 days):   $" + calculator.calculateRefund("ONLINE", 100.0, 45));
        System.out.println("Curbside:           $" + calculator.calculateRefund("CURBSIDE", 100.0, 10));
        System.out.println("Marketplace:        $" + calculator.calculateRefund("MARKETPLACE", 100.0, 5));
    }
}
