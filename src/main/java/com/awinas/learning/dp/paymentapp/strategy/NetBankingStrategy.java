package com.awinas.learning.dp.paymentapp.strategy;

/**
 * STRATEGY PATTERN - Net Banking Payment Strategy
 *
 * Encapsulates net banking details.
 * Bank name is the key detail (e.g., "HDFC", "SBI", "ICICI").
 */
public class NetBankingStrategy implements PaymentStrategy {

    private final String bankName;
    private final String accountHolder;

    public NetBankingStrategy(String bankName, String accountHolder) {
        this.bankName = bankName;
        this.accountHolder = accountHolder;
    }

    @Override
    public String getMethod() {
        return "NETBANKING";
    }

    @Override
    public String getDetails() {
        return "Bank: " + bankName + " | Account: " + accountHolder;
    }
}
