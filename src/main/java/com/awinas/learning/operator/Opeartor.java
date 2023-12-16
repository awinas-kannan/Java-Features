package com.awinas.learning.operator;

public class Opeartor {

	public static void main(String[] args) {

		System.out.println("Arithmetic Operators");

		int sum = 10 + 20;
		int difference = 50 - 20;
		long area = 20l * 30l;
		int percentage = 20 / 100;

		System.out
				.println("sum " + sum + "\ndifference " + difference + "\narea " + area + "\npercentage " + percentage);

		System.out.println(30l * 1);
		
		
		System.out.println("\nString concatenation ");
		// String concatenation //reference type + primitive type
		//null can be concatenated with string
		int num = 26;
		String str1 = " - I'm awinas - ";
		String str2 = num + str1;
		System.out.println(str2);
		System.out.println(str1 + null);
		
		
	}

}
