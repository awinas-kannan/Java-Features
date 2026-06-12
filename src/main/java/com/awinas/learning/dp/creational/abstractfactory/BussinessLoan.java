package com.awinas.learning.dp.creational.abstractfactory;

class BussinessLoan extends Loan {

	public double getInterestRate(String bank) {
		if (bank.equals("HDFC")) {
			return 9.5;
		} else if (bank.equals("ICICI")) {
			return 11.5;
		} else if (bank.equals("SBI")) {
			return 10.5;
		}
		// default
		return 12;
	}

}