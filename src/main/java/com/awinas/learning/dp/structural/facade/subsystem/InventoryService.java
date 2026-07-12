package com.awinas.learning.dp.structural.facade.subsystem;

// Subsystem — checks and reserves product stock
public class InventoryService {

    public boolean checkStock(String productId, int quantity) {
        System.out.println("  [InventoryService] Checking stock for product: " + productId);
        // Simulating stock available
        System.out.println("  [InventoryService] Stock available: " + quantity + " unit(s)");
        return true;
    }

    public void reserveStock(String productId, int quantity) {
        System.out.println("  [InventoryService] Stock reserved: " + quantity + " unit(s) for " + productId);
    }

    public void releaseStock(String productId, int quantity) {
        System.out.println("  [InventoryService] Stock released back for " + productId + " (order cancelled)");
    }
}
