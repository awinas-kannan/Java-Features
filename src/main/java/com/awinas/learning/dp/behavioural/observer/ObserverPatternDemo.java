package com.awinas.learning.dp.behavioural.observer;

public class ObserverPatternDemo {

    public static void main(String[] args) {

        // Create order (Subject)
        Order order = new Order("ORD-2001");

        // Register observers — any service interested in order events subscribes here
        order.addObserver(new EmailService());
        order.addObserver(new SMSService());
        order.addObserver(new WarehouseService());
        order.addObserver(new LoyaltyService());

        // Simulate order lifecycle
        // Each status change automatically notifies ALL registered observers
        order.setStatus(OrderStatus.PLACED);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setStatus(OrderStatus.SHIPPED);
        order.setStatus(OrderStatus.DELIVERED);

        // ---- Cancelled order (separate demo) ----
        System.out.println("\n========================================");
        System.out.println("Cancelled Order Demo");
        System.out.println("========================================");

        Order cancelledOrder = new Order("ORD-2002");
        cancelledOrder.addObserver(new EmailService());
        cancelledOrder.addObserver(new SMSService());
        cancelledOrder.addObserver(new WarehouseService());
        cancelledOrder.addObserver(new LoyaltyService());

        cancelledOrder.setStatus(OrderStatus.PLACED);
        cancelledOrder.setStatus(OrderStatus.CONFIRMED);
        cancelledOrder.setStatus(OrderStatus.CANCELLED);

        // ---- Dynamic: remove SMS observer mid-flow ----
        System.out.println("\n========================================");
        System.out.println("Remove SMS Observer Mid-Flow");
        System.out.println("========================================");

        SMSService sms = new SMSService();
        Order order3 = new Order("ORD-2003");
        order3.addObserver(new EmailService());
        order3.addObserver(sms);

        order3.setStatus(OrderStatus.PLACED);

        order3.removeObserver(sms);   // customer opted out of SMS

        order3.setStatus(OrderStatus.SHIPPED);   // email fires, SMS does not
    }
}
