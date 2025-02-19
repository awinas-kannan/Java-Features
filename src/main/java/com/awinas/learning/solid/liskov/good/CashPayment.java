package com.awinas.learning.solid.liskov.good;
class CashPayment extends Payment implements CashDiscount {
    @Override
    void processPayment(double amount) {
        System.out.println("Processing cash payment of $" + amount);
    }

    @Override
    public void applyCashDiscount() {
        System.out.println("Applying cash discount...");
    }
}