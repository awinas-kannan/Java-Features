package com.awinas.learning.dp.behavioural.strategy;

public class CardStrategy implements PaymentStrategy {

    private final String cardNumber;
    private final String expiry;

    public CardStrategy(String cardNumber, String expiry) {
        this.cardNumber = cardNumber;
        this.expiry = expiry;
    }

    @Override
    public void pay(double amount) {
        String last4 = cardNumber.substring(cardNumber.length() - 4);
        String authCode = "AUTH-" + (int) (Math.random() * 900000 + 100000);
        System.out.println("  [CardStrategy] Authorising card payment");
        System.out.println("  Card    : ****-****-****-" + last4);
        System.out.println("  Expiry  : " + expiry);
        System.out.println("  Amount  : Rs." + amount);
        System.out.println("  Auth    : " + authCode);
        System.out.println("  Status  : Payment SUCCESS");
    }

    @Override
    public String getMethod() {
        return "CARD";
    }

    @Override
    public String getDetails() {
        String last4 = cardNumber.substring(cardNumber.length() - 4);
        return "Card: ****-****-****-" + last4 + " | Expiry: " + expiry;
    }
}
