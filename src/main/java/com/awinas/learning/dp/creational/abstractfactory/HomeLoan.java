package com.awinas.learning.dp.creational.abstractfactory;

class HomeLoan extends Loan {
	public double getInterestRate(String bank) {
		
		if (bank.equals("HDFC")) {
			return 9;
		} else if (bank.equals("ICICI")) {
			return 10;
		} else if (bank.equals("SBI")) {
			return 11;
		}
		// default
		return 12;
	}
}