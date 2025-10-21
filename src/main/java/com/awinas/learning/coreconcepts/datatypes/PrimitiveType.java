package com.awinas.learning.coreconcepts.datatypes;

//https://howtodoinjava.com/java/basics/primitive-data-types-in-java/

//byte -8 bit (1 byte)  // -128 to 127 (-27 to 27 � 1).
//short -16 bit (2 byte)  // -32768 to 32767 (or -2^15 to 2^15 � 1).
//int - 32 bit ( 4 byte)  // -2,147,483,648 to 2,147,483,647 (-2^31 to 2^31� 1).
//long -64 bit ( 8 byte)  // -2^63 to 2^63 � 1.

//Small data type to large datatype --- No type match
//Large data type to small datatype --- type match  -- Add explicit casting // data loss
public class PrimitiveType {

	public static void main(String[] args) {

		System.out.println("Byte Min value");
		byte bytemax = Byte.MAX_VALUE;
		byte bytemin = Byte.MIN_VALUE;
		System.out.println(bytemax);
		System.out.println(bytemin);

		System.out.println("Short Min value");
		short shortmax = Short.MAX_VALUE;
		short shortmin = Short.MIN_VALUE;
		System.out.println(shortmax);
		System.out.println(shortmin);

		System.out.println("Integer Min value");
		int max = Integer.MAX_VALUE; // Assigns maximum int value to max
		int min = Integer.MIN_VALUE;
		System.out.println(max);
		System.out.println(min);

		System.out.println("Long Min value");
		long longmax = Long.MAX_VALUE;
		long longmin = Long.MIN_VALUE;
		System.out.println(longmax);
		System.out.println(longmin);

		System.out.println("Char Date Type");
		char c1 = 'A';
		char c2 = 'L';
		char c3 = '5';
		char c4 = '/';
		System.out.println(c1);
		System.out.println(c2);
		System.out.println(c3);
		System.out.println(c4);

		System.out.println("Float Date Type");

		System.out.println(Float.MIN_VALUE);
		System.out.println(Float.MAX_VALUE);
		System.out.println(Float.MIN_NORMAL);
		System.out.println(Float.POSITIVE_INFINITY);
		System.out.println(Float.NEGATIVE_INFINITY);
		System.out.println(Float.NaN);

		System.out.println("Double Date Type");

		System.out.println(Double.MIN_VALUE);
		System.out.println(Double.MAX_VALUE);
		System.out.println(Double.MIN_NORMAL);
		System.out.println(Double.POSITIVE_INFINITY);
		System.out.println(Double.NEGATIVE_INFINITY);
		System.out.println(Double.NaN);

	}

}
