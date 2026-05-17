package com.awinas.learning.solid.walmartreturns.liskov.bad;

import java.util.List;

/**
 * Demonstrates how LSP violation causes runtime crashes.
 *
 * Any code that generically processes a list of ReturnProcessor objects
 * will EXPLODE when it encounters InStoreOnlyReturn or FinalSaleReturn.
 */
public class BadMain {

    public static void processAllReturns(List<ReturnProcessor> returns) {
        for (ReturnProcessor returnProcessor : returns) {
            returnProcessor.processRefund();
            returnProcessor.generateShippingLabel(); // 💥 CRASHES for InStoreOnlyReturn!
            System.out.println("Status: " + returnProcessor.getStatus());
            System.out.println("---");
        }
    }

    public static void main(String[] args) {
        List<ReturnProcessor> returns = List.of(
                new ReturnProcessor("WMT-001", 49.99),
                new InStoreOnlyReturn("WMT-002", 29.99),  // 💥 Will crash here!
                new FinalSaleReturn("WMT-003", 9.99)      // 💥 Would crash here too
        );

        try {
            processAllReturns(returns);
        } catch (UnsupportedOperationException e) {
            System.out.println("\n💥 RUNTIME ERROR: " + e.getMessage());
            System.out.println("⚠️  This happened because LSP is violated — subclasses " +
                    "cannot be safely substituted for the parent class!");
        }
    }
}
