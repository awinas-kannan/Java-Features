package com.awinas.learning.dp.structural.proxy;

// Real Subject - does the actual payment work
// It only focuses on payment processing, nothing else
public class RealPaymentGateway implements PaymentGateway {

    @Override
    public String pay(String userId, double amount) {
        String txnId = "TXN-" + System.currentTimeMillis();
        System.out.println("  [RealPaymentGateway] Payment processed"
                + " | User: " + userId
                + " | Amount: Rs." + amount
                + " | TxnId: " + txnId);
        return txnId;
    }

    @Override
    public boolean refund(String transactionId, double amount) {
        System.out.println("  [RealPaymentGateway] Refund processed"
                + " | TxnId: " + transactionId
                + " | Amount: Rs." + amount);
        return true;
    }
}
