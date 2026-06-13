package com.awinas.learning.dp.creational.singletondp.v2;

/**
 * Singleton Pattern - Retail Example
 *
 * A single InventoryManager instance that all services talk to.
 * Uses double-checked locking for thread safety.
 */
public class InventoryManager {

    private static volatile InventoryManager instance;

    private int totalProducts;

    private InventoryManager() {
        this.totalProducts = 0;
        System.out.println("InventoryManager initialized.");
    }

    public static InventoryManager getInstance() {
        if (instance == null) {
            synchronized (InventoryManager.class) {
                if (instance == null) {
                    instance = new InventoryManager();
                }
            }
        }
        return instance;
    }

    public void addStock(String productName, int quantity) {
        totalProducts += quantity;
        System.out.println("Added " + quantity + " units of '" + productName + "'. Total products in inventory: " + totalProducts);
    }

    public void removeStock(String productName, int quantity) {
        if (quantity > totalProducts) {
            System.out.println("Insufficient stock for '" + productName + "'!");
            return;
        }
        totalProducts -= quantity;
        System.out.println("Removed " + quantity + " units of '" + productName + "'. Total products in inventory: " + totalProducts);
    }

    public int getTotalProducts() {
        return totalProducts;
    }
}
