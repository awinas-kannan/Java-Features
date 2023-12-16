package com.awinas.learning.operator;

public class BitwiseOperators {
	/**
	 * @param args
	 */
	/* All the power of 2 have only single set bit */
	// 2 - 0010 // 4 - 0100 // 8 1000 // 16 - 10000
	// https://www.javatpoint.com/bitwise-operator-in-java
	// https://www.scientecheasy.com/2020/05/shift-operator-in-java.html/

	// https://florian.github.io/xor-trick/ ----- > check all application

	// https://www.programiz.com/java-programming/bitwise-operators ---> For bitwise
	// complement :: -(N + 1). The negative value of integer is obtained from 2's
	// complement of pos integer
	public static void main(String[] args) {

		System.out.println(Integer.toBinaryString(2));
		System.out.println(Integer.toBinaryString(-2));
		System.out.println(Integer.toBinaryString(5));
		System.out.println("5 << 3 : Number " + (5 << 3) + " Binary  " + Integer.toBinaryString(5 << 3));
		System.out.println(Integer.toBinaryString(-5));
		System.out.println(Integer.toBinaryString(-5 >> 3));
		System.out.println(Integer.toBinaryString(-5 << 3));
		/*
		 * BITWISE XOR --ODD ONE OUT
		 * 
		 * XOR has these two properties:
		 * 
		 * X ^ x = 0
		 * 
		 * 0 ^ Y = Y
		 * 
		 * X ^ X ^ Y = Y
		 * 
		 * a ^ ( b ^ c)= (a ^ b) ^ c --ASSOCIATIVE PROPERTY
		 */

		// Just imagine like sorting
		// The sorted value ll be -- a ,a ,b,b,c,c,e,e,g
		// Now check the propery... 'a' xor 'a' -> 0 .. then 0 xor 'b' is 'b'
		// again 'b' xor 'b' -> 0
		Character[] arr = { 'a', 'a', 'b', 'c', 'b', 'c', 'g', 'e', 'e' };
		Integer res = 0;
		for (Integer i = 0; i < arr.length; i++) {
			res ^= arr[i];
		}
		System.out.println("The odd occurring element is " + (char) res.intValue());

		System.out.println("************************************");

		/*
		 * SWAP a^b=15 b= a^ b ^ a --10 a= 10^15 --5
		 * 
		 */

		int a = 10;
		int b = 5;

		// a ^ b ^ a - > b (odd one )
		// a ^ b ^ b - > a (odd one )

		a = a ^ b; // Some value stored in a
		b = a ^ b; // Some value stored in a XOR b ( a already has b so a is retrieved in b ) (
					// just like a ^ b ^ b )
		a = a ^ b;
		System.out.println("a- " + a + " b-" + b);
		System.out.println("************************************");

		/*
		 * Upper case English alphabet to lower case A = 65 a = 97 || 97 - 65 = 32 which
		 * is space 32 - 0100000 65 - 1000001
		 */
		// Ascii value of Space & its binary form
		System.out.println(((int) ' ') + " - " + Integer.toBinaryString((int) ' '));
		System.out.println(((int) 'A') + " - " + Integer.toBinaryString((int) 'A'));
		Character c = 'A';
		System.out.println((char) (c | ' '));

		System.out.println("************************************");

		/*
		 * Divide by 2
		 */

		System.out.println();
		int xx = 1024;
		for (int m = 0; m < 10; m++)
			System.out.print((xx >>= 1) + " ");

		System.out.println();
		System.out.println("************************************");
		/*
		 * Multiplying by 2
		 */
		xx = 2;
		System.out.println();
		for (int m = 0; m < 10; m++)
			System.out.print((xx <<= 1) + " ");
		System.out.println();
		System.out.println("************************************");

		/*
		 * String to Upper case 32 +32 +32 = 96 So its its starting from 97 (a)
		 */
		System.out.println("String to Upper case");
		System.out.println(((int) '_') + " - " + Integer.toBinaryString((int) '_'));
		String awi = "kamal";
		char[] charArr = awi.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char ch : charArr) {
			sb.append((char) (ch & '_'));
		}
		System.out.println(sb.toString());

		System.out.println("************************************");

		/* Traditional way */

		System.out.println("a--" + (int) 'a');
		System.out.println("A--" + (int) 'A');
		String gok = "gokul";
		char[] charArr1 = gok.toCharArray();
		StringBuilder sb1 = new StringBuilder();
		for (char ch : charArr1) {
			sb1.append((char) (ch - ' '));
		}
		System.out.println(sb1.toString());

		System.out.println("************************************");
		int count = 0;
		int aw = 109;
		while (aw > 0) {
			aw &= (aw - 1);
			count++;
		}
		System.out.println("count" + count);

		System.out.println("**************Find one Missing number **********************");

		// Missing number
		Integer[] intArr = new Integer[] { 1, 2, 3, 4, 6, 7 };
		System.out.println("Array length " + intArr.length);
		int xorIntArr = 0;
		for (int i : intArr) {
			xorIntArr ^= i;
		}

		for (int i = 1; i <= intArr.length + 1; i++) {
			xorIntArr ^= i;
		}
		System.out.println("Missing Number is " + xorIntArr);

		System.out.println("**************Find two Missing number **********************");

		Integer[] intArr1 = new Integer[] { 3, 4 };
		// Integer[] intArr1 = new Integer[] { 1, 3, 5, 6};
		// Integer[] intArr1 = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 9, 10, 11 };
//		Integer[] intArr1 = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 19, 20, 21, 22,
//				23 };
		int n = intArr1.length + 2;
		System.out.println("Array length " + intArr1.length);
		int XOR = 0;
		for (int i : intArr1) {
			XOR ^= i;
		}

		for (int i = 1; i <= n; i++) {
			XOR ^= i;
		}
		System.out.println("Missing Number  combination is " + XOR + " Binary form " + Integer.toBinaryString(XOR));
		int set_bit_no = XOR & ~(XOR - 1);
		System.out.println("XOR VALUE  " + XOR + "(XOR - 1) " + (XOR - 1) + "~(XOR - 1) " + (~(XOR - 1)) + " Bin value "
				+ Integer.toBinaryString((~(XOR - 1))));
		// First set bit can be obtained by bitwise and of positive integer with their
		// negative value
		// 6 & -6
		System.out.println("set bit  " + Integer.toBinaryString(set_bit_no));
		// We are getting first set bit and gonna split the given array & (1 to n) nums
		// based on set bits at the same position
		// Because we know in XOR we get 1 if both numbers have opposite bit
		int x = 0, y = 0; // Initialize missing numbers
		for (int i = 0; i < n - 2; i++) {
			System.out.println((intArr1[i] & set_bit_no));
			if ((intArr1[i] & set_bit_no) == set_bit_no)

				/* XOR of first set in arr[] */
				x = x ^ intArr1[i];
			else
				/* XOR of second set in arr[] */
				y = y ^ intArr1[i];
		}
		System.out.println("After loop one - value of x & y  " + x + " - " + y);

		for (int i = 1; i <= n; i++) {
			System.out.println((i & set_bit_no));
			if ((i & set_bit_no) == set_bit_no)

				/*
				 * XOR of first set in arr[] and {1, 2, ...n }
				 */
				x = x ^ i;
			else
				/*
				 * XOR of second set in arr[] and {1, 2, ...n }
				 */
				y = y ^ i;
		}

		System.out.println("Two Missing Numbers are ");
		System.out.println(x + " " + y);
	}
}
