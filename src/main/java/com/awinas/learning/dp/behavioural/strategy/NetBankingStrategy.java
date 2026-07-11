package com.awinas.learning.dp.behavioural.strategy;

public class NetBankingStrategy implements PaymentStrategy {

    private final String bankName;

    public NetBankingStrategy(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public void pay(double amount) {
        String txnId = "NB-TXN-" + System.currentTimeMillis();
        System.out.println("  [NetBankingStrategy] Redirecting to bank portal");
        System.out.println("  Bank    : " + bankName);
        System.out.println("  Amount  : Rs." + amount);
        System.out.println("  Txn ID  : " + txnId);
        System.out.println("  Status  : Payment SUCCESS");
    }

    @Override
    public String getMethod() {
        return "NETBANKING";
    }

    @Override
    public String getDetails() {
        return "Bank: " + bankName;
    }
}
