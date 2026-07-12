package com.awinas.learning.dp.structural.facade;

import com.awinas.learning.dp.structural.facade.subsystem.InventoryService;
import com.awinas.learning.dp.structural.facade.subsystem.NotificationService;
import com.awinas.learning.dp.structural.facade.subsystem.PaymentService;
import com.awinas.learning.dp.structural.facade.subsystem.ShippingService;

// FACADE — one simple entry point for the entire order placement flow.
// Client calls placeOrder() and cancelOrder() only.
// Client never interacts with InventoryService, PaymentService, ShippingService, or NotificationService directly.
public class OrderFacade {

    private final InventoryService  inventoryService  = new InventoryService();
    private final PaymentService    paymentService    = new PaymentService();
    private final ShippingService   shippingService   = new ShippingService();
    private final NotificationService notificationService = new NotificationService();

    public boolean placeOrder(String orderId, String productId, int qty,
                              double amount, String paymentMethod,
                              String address, String email) {

        System.out.println("\n[OrderFacade] Placing order: " + orderId);

        // Step 1: Check and reserve stock
        if (!inventoryService.checkStock(productId, qty)) {
            System.out.println("[OrderFacade] Order FAILED — Out of stock");
            return false;
        }
        inventoryService.reserveStock(productId, qty);

        // Step 2: Process payment
        String txnId = paymentService.processPayment(orderId, amount, paymentMethod);

        // Step 3: Create shipment
        String trackingId = shippingService.createShipment(orderId, address);

        // Step 4: Send confirmation
        notificationService.sendOrderConfirmation(orderId, email, trackingId);

        System.out.println("[OrderFacade] Order " + orderId + " placed successfully!");
        return true;
    }

    public void cancelOrder(String orderId, String productId, int qty,
                            String txnId, double amount,
                            String trackingId, String email) {

        System.out.println("\n[OrderFacade] Cancelling order: " + orderId);

        notificationService.sendCancellationNotice(orderId, email);
        shippingService.cancelShipment(trackingId);
        paymentService.refundPayment(txnId, amount);
        inventoryService.releaseStock(productId, qty);

        System.out.println("[OrderFacade] Order " + orderId + " cancelled and refund initiated.");
    }
}
