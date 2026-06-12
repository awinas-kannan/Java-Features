package com.awinas.learning.dp.structural.decorator.v2;

/*
 * Product.java
 * TShirt.java
 * 
 * ProductDecorator.java
 * 
 * DiscountCoupon.java
 * ExpressDelivery.java
 * ProductDecorator.java
 * 
 */
public class RetailApp {
	public static void main(String[] args) {
        Product basicTShirt = new TShirt();

        Product customTShirt = new GiftWrap(
                                    new ExpressDelivery(
                                        new DiscountCoupon(basicTShirt)
                                    )
                                );

        System.out.println("Product: " + customTShirt.getDescription());
        System.out.println("Total Price: ₹" + customTShirt.getPrice());
    }
}
