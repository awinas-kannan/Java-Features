package com.awinas.learning.dp.creational.builder;

/**
 * Demo: Building complex Order objects step-by-step.
 * Each order can have different combinations of optional fields.
 */
public class BuilderDemo {

    public static void main(String[] args) {
        System.out.println("=== Order 1: Using builder with args ===");
        Order simpleOrder = Order.builder("ORD-1001", "Awinas")
                .addItem("Samsung Galaxy S25 - ₹79,999")
                .shippingAddress("123, MG Road, Bangalore - 560001")
                .paymentMethod("UPI")
                .build();
        System.out.println(simpleOrder);

        System.out.println("\n=== Order 2: Using empty builder ===");
        Order emptyBuilderOrder = Order.builder()
                .orderId("ORD-1002")
                .customerName("Ravi Kumar")
                .addItem("Titan Watch - ₹4,999")
                .addItem("Gift Card - ₹500")
                .shippingAddress("456, Anna Nagar, Chennai - 600040")
                .paymentMethod("CREDIT_CARD")
                .giftWrap(true)
                .expressDelivery(true)
                .specialInstructions("Please include a birthday card saying 'Happy Birthday Priya!'")
                .build();
        System.out.println(emptyBuilderOrder);

        System.out.println("\n=== Order 3: Bulk order with coupon ===");
        Order bulkOrder = Order.builder()
                .orderId("ORD-1003")
                .customerName("Meena Stores")
                .addItem("Rice 25kg x 10 - ₹12,000")
                .addItem("Oil 5L x 20 - ₹6,000")
                .addItem("Sugar 5kg x 15 - ₹4,500")
                .shippingAddress("789, Wholesale Market, Coimbatore - 641001")
                .paymentMethod("WALLET")
                .couponCode("BULK20")
                .specialInstructions("Deliver before 6 AM - back gate entry only")
                .build();
        System.out.println(bulkOrder);
    }
}
