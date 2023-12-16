package com.awinas.learning.operator;

public class ShiftOperators {
	/*
	 * signed operators preserves the sign of number. 0-> represents positive, 1->represent
	 * negative number while shifting , the trailing posotion will be 0 if positive
	 * number while shifting , the trailing posotion will be 1 if negative number
	 * 
	 * for unsigned operator, the trailing positon will always be zero
	 * 
	 */
	public static void main(String[] args) {
		Integer x, y;
		x = 10;
		y = -10;
		System.out.println("x:   " + Integer.toBinaryString(x));
		System.out.println("y:   " + "" + Integer.toBinaryString(y));
		System.out.println();
		/*
		 * System.out.println(Integer.toBinaryString(x<<1));
		 * System.out.println(Integer.toBinaryString(y<<1));
		 */
		System.out.println("x>>1 " + Integer.toBinaryString(x >> 1) + " decimal "
				+ Integer.parseInt(Integer.toBinaryString(x >> 1), 2));
		System.out.println("y>>4 " + Integer.toBinaryString(y >> 4) + " decimal "
				+ Long.parseLong(Integer.toBinaryString(y >> 4), 2));

		System.out.println("x>>>4 " + Integer.toBinaryString(x >>> 1) + " decimal "
				+ Long.parseLong(Integer.toBinaryString(x >> 1), 2));

		System.out.println("y>>>4 " + Integer.toBinaryString(y >>> 4) + " decimal "
				+ Long.parseLong(Integer.toBinaryString(y >>> 4), 2));

	}
}
