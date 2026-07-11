package com.awinas.learning.dp.behavioural.observer;

// Sends email on EVERY status change
public class EmailService implements OrderObserver {

    @Override
    public void update(String orderId, OrderStatus status) {
        switch (status) {
            case PLACED:
                System.out.println("  [EmailService] Order confirmation email sent for " + orderId);
                break;
            case CONFIRMED:
                System.out.println("  [EmailService] Order confirmed email sent for " + orderId);
                break;
            case SHIPPED:
                System.out.println("  [EmailService] Shipping email with tracking link sent for " + orderId);
                break;
            case DELIVERED:
                System.out.println("  [EmailService] Delivery confirmation email sent for " + orderId);
                break;
            case CANCELLED:
                System.out.println("  [EmailService] Cancellation email with refund info sent for " + orderId);
                break;
        }
    }
}
