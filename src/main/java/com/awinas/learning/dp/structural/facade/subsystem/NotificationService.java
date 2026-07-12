package com.awinas.learning.dp.structural.facade.subsystem;

// Subsystem — sends email and SMS notifications
public class NotificationService {

    public void sendOrderConfirmation(String orderId, String email, String trackingId) {
        System.out.println("  [NotificationService] Email sent to " + email);
        System.out.println("  [NotificationService] Order: " + orderId + " | Track: " + trackingId);
    }

    public void sendCancellationNotice(String orderId, String email) {
        System.out.println("  [NotificationService] Cancellation email sent to " + email + " for order " + orderId);
    }
}
