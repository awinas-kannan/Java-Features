package com.awinas.learning.dp.creational.abstractfactory;

public class BlackFridayOfferCard implements OfferCard {
    @Override
    public void showOffer() {
        System.out.println("[Black Friday Offer] Flat 60% OFF on electronics + Free express shipping!");
    }
}
