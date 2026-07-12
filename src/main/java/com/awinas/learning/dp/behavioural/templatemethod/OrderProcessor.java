package com.awinas.learning.dp.behavioural.templatemethod;

// Template Method Pattern — Abstract Class
// Defines the SKELETON of the order processing algorithm.
// Subclasses fill in the specific steps without changing the overall flow.
public abstract class OrderProcessor {

    // TEMPLATE METHOD — the algorithm skeleton. Final so subclasses cannot change the flow.
    public final void processOrder(String orderId, double basePrice) {
        System.out.println("\n--- Processing Order: " + orderId + " ---");
        validateOrder(orderId);
        double price = calculatePrice(basePrice);
        price = applyDiscount(price);           // hook — subclass can override
        processPayment(orderId, price);
        sendConfirmation(orderId, price);
        System.out.println("--- Order " + orderId + " Complete ---");
    }

    // CONCRETE step — same for all order types
    private void validateOrder(String orderId) {
        System.out.println("[Validate]  Order " + orderId + " validated successfully");
    }

    // ABSTRACT step — each order type calculates price differently
    protected abstract double calculatePrice(double basePrice);

    // HOOK — optional override. Default: no discount
    protected double applyDiscount(double price) {
        return price;
    }

    // ABSTRACT step — each order type processes payment differently
    protected abstract void processPayment(String orderId, double amount);

    // CONCRETE step — same for all order types
    private void sendConfirmation(String orderId, double amount) {
        System.out.println("[Confirm]   Confirmation sent for " + orderId + " | Final: Rs." + String.format("%.2f", amount));
    }
}
