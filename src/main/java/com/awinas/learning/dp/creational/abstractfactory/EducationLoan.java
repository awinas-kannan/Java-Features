package com.awinas.learning.dp.creational.abstractfactory;

class EducationLoan extends Loan {
	
	public double getInterestRate(String bank) {
		if (bank.equals("HDFC")) {
			return 5;
		} else if (bank.equals("ICICI")) {
			return 6;
		} else if (bank.equals("SBI")) {
			return 7;
		}
		// default
		return 8;
	}
}