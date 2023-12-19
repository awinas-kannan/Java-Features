package com.awinas.learning.Java15;

public sealed class Account permits CurrentAccount,LoanAccount,SavingAccount {

	/*
	 * Here, we are permitted only 3 classes the permission to extend the Account
	 * class. No other class class can extend the Account class
	 */
}
