package com.awinas.learning.designpatterns.paymentapp.strategy;

/**
 * STRATEGY PATTERN - Card Payment Strategy
 *
 * Encapsulates card details.
 * getDetails() masks the card number for security (shows only last 4 digits).
 */
public class CardStrategy implements PaymentStrategy {

    private final String cardNumber;
    private final String expiry;
    private final String cvv;

    public CardStrategy(String cardNumber, String expiry, String cvv) {
        this.cardNumber = cardNumber;
        this.expiry = expiry;
        this.cvv = cvv;
    }

    @Override
    public String getMethod() {
        return "CARD";
    }

    @Override
    public String getDetails() {
        // Never expose full card number - show only last 4 digits
        String masked = "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
        return "Card: " + masked + " | Expiry: " + expiry;
    }
}
