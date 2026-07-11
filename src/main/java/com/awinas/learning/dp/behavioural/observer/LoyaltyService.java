package com.awinas.learning.dp.behavioural.observer;

// Adds reward points only on DELIVERED — points are earned when order is complete
public class LoyaltyService implements OrderObserver {

    private static final int POINTS_PER_ORDER = 50;

    @Override
    public void update(String orderId, OrderStatus status) {
        if (status == OrderStatus.DELIVERED) {
            System.out.println("  [LoyaltyService] " + POINTS_PER_ORDER
                    + " reward points added for order " + orderId);
        }
        // No action for any other status
    }
}
