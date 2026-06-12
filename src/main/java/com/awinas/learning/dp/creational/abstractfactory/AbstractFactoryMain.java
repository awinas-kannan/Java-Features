package com.awinas.learning.dp.creational.abstractfactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AbstractFactoryMain {

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Boolean wannaCheckOtherBank = true;
		while (wannaCheckOtherBank) {
			System.out.print("Enter the name of Bank from where you want to take loan amount:HDFC/ICICI/SBI ");
			String bankName = br.readLine();

			System.out.print("\n");
			System.out.print(
					"Enter the type of loan e.g. home loan or business loan or education loan :Business/Education/Home ");

			String loanName = br.readLine();
			AbstractFactory bankFactory = FactoryCreator.getFactory("Bank");
			Bank b = bankFactory.getBank(bankName);

			System.out.print("\n");
			System.out.print("Enter the loan amount you want to take: ");

			double loanAmount = Double.parseDouble(br.readLine());
			System.out.print("\n");
			System.out.print("Enter the number of years to pay your entire loan amount: ");
			int years = Integer.parseInt(br.readLine());

			System.out.print("\n");

			AbstractFactory loanFactory = FactoryCreator.getFactory("Loan");
			Loan l = loanFactory.getLoan(loanName);
			l.calculateLoanPayment(loanAmount, l.getInterestRate(bankName), years);

			System.out.print("\n");
			System.out.print("Wana check another one yes -1 No - 0");
			wannaCheckOtherBank = Integer.parseInt(br.readLine()) == 1 ? true : false;

		}
		
		System.out.println("Thank you !!!!");
	}

}
