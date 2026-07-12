package com.awinas.learning.dp.behavioural.templatemethod;

public class TemplateMethodDemo {

    public static void main(String[] args) {

        // Same processOrder() call for all — different steps run depending on the subclass
        // The algorithm skeleton never changes

        OrderProcessor standard     = new StandardOrderProcessor();
        OrderProcessor premium      = new PremiumOrderProcessor();
        OrderProcessor international = new InternationalOrderProcessor();

        standard.processOrder("ORD-STD-001", 1000.0);
        premium.processOrder("ORD-PRM-002", 1000.0);
        international.processOrder("ORD-INT-003", 1000.0);
    }
}
