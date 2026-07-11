package com.awinas.learning.dp.behavioural.observer;

// Sends SMS only for SHIPPED and DELIVERED — customer doesn't need SMS for every event
public class SMSService implements OrderObserver {

    @Override
    public void update(String orderId, OrderStatus status) {
        switch (status) {
            case SHIPPED:
                System.out.println("  [SMSService] SMS sent: Your order " + orderId + " is on the way!");
                break;
            case DELIVERED:
                System.out.println("  [SMSService] SMS sent: Order " + orderId + " delivered. Rate your experience.");
                break;
            case CANCELLED:
                System.out.println("  [SMSService] SMS sent: Order " + orderId + " cancelled. Refund initiated.");
                break;
            default:
                // No SMS for PLACED or CONFIRMED
                break;
        }
    }
}
