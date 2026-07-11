package com.awinas.learning.dp.behavioural.observer;

// Reacts to CONFIRMED (pick and pack) and CANCELLED (restock items)
public class WarehouseService implements OrderObserver {

    @Override
    public void update(String orderId, OrderStatus status) {
        switch (status) {
            case CONFIRMED:
                System.out.println("  [WarehouseService] Pick and pack initiated for order " + orderId);
                break;
            case SHIPPED:
                System.out.println("  [WarehouseService] Order " + orderId + " handed to courier. Inventory updated.");
                break;
            case CANCELLED:
                System.out.println("  [WarehouseService] Order " + orderId + " cancelled. Items restocked.");
                break;
            default:
                break;
        }
    }
}
