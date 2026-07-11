package com.awinas.learning.dp.behavioural.strategy;

public class UpiStrategy implements PaymentStrategy {

    private final String upiId;

    public UpiStrategy(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public void pay(double amount) {
        String refId = "UPI-REF-" + System.currentTimeMillis();
        System.out.println("  [UpiStrategy] Sending UPI payment request");
        System.out.println("  UPI ID  : " + upiId);
        System.out.println("  Amount  : Rs." + amount);
        System.out.println("  Ref ID  : " + refId);
        System.out.println("  Status  : Payment SUCCESS");
    }
}
