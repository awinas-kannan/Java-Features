package com.awinas.learning.dp.creational.singletondp.v2;

/**
 * Demo: Both services use the SAME InventoryManager instance.
 * Any stock change from one service is visible to the other.
 */
public class SingletonDemo {

    public static void main(String[] args) {
        InventoryManager warehouseService = InventoryManager.getInstance();
        InventoryManager orderService = InventoryManager.getInstance();

        System.out.println("Are both references same? " + (warehouseService == orderService));

        warehouseService.addStock("Samsung Galaxy S25", 100);
        warehouseService.addStock("iPhone 16", 50);

        orderService.removeStock("Samsung Galaxy S25", 3);
        orderService.removeStock("iPhone 16", 1);

        System.out.println("Final inventory count: " + warehouseService.getTotalProducts());
    }
}
