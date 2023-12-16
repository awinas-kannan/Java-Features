package com.awinas.learning.interview.nielsen;

//What is the probability of an integer from 1 to 60,000 not having the digit 6
class Bank {
	// all the cash (ATM) machines share a single bank account
	int TRANSACTIONS_PER_MACHINE = 5;
	private int balance = 0;

	private void deposit() {
		balance = balance + 1;
	}

	private void withdraw() {
		balance = balance - 1;
	}

//	// Customers use the cash machines to do transactions like this:
//	deposit(); // put a dollar in
//
//	withdraw(); // take it back out

//	private void cashMachine() {
//		for (int i = 0; i < TRANSACTIONS_PER_MACHINE; ++i) {
//			deposit();
//			withdraw();
//		}
//
//	}
}