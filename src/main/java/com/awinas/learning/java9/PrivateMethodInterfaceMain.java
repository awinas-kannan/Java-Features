package com.awinas.learning.Java9;

public class PrivateMethodInterfaceMain implements PrivateMethodInterface {

	@Override
	public void abstractMetod() {
		System.out.println("abstractMetod");
	}

	public static void main(String[] args) {
		PrivateMethodInterface pmi = new PrivateMethodInterfaceMain();
		pmi.abstractMetod();
		pmi.defMethod1();
		pmi.defMethod2();
		PrivateMethodInterface.staticMethod();
		System.out.println(PrivateMethodInterface.pi);

		int sumOfEvens = pmi.addEvenNumbers(1, 2, 3, 4, 5, 6, 7, 8, 9);
		System.out.println(sumOfEvens);

		int sumOfOdds = pmi.addOddNumbers(1, 2, 3, 4, 5, 6, 7, 8, 9);
		System.out.println(sumOfOdds);

	}

}
