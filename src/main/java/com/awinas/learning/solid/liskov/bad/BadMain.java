package com.awinas.learning.solid.liskov.bad;

/*
 * 
 * 
 *

Why does this violate LSP?
❌ UPIPayment & CreditCardPayment don’t support cash discounts:

If we pass a CreditCardPayment object to a function expecting Payment, it will have an unnecessary applyCashDiscount() method.
Calling applyCashDiscount() on a UPI or Credit Card payment makes no sense!

 */
public class BadMain {

	public static void main(String[] args) {
		Payment crediCard = new CreditCardPayment();
		Payment cashPayment = new CashPayment();

		/*
		 * Credit card doesn't require cash discount.... Only cash payment has that
		 * feature...
		 * cashPayment.applyCashDiscount();
		 * Move it to Interface
		 */
		crediCard.applyCashDiscount();
		
		

	}

}
