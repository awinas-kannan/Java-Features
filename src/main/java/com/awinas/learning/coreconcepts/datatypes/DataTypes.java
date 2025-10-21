package com.awinas.learning.coreconcepts.datatypes;

//https://howtodoinjava.com/java/basics/data-types-in-java/

//Non static variable , methods cannot be used inside static method
public class DataTypes {

	char xxx;
	static byte byteVal;
	static short shortVal;
	static int intVal;
	static long longVal;
	static boolean boolVal;
	static float floatVal;
	static double doubleVal;
	static char charVal;

	public static void main(String[] args) {

		// Default data type values
		byte byteVal = 0;
		short shortVal = 0;
		int intVal = 0;
		long longVal = 0;
		boolean boolVal = false;
		float floatVal = 0;
		double doubleVal = 0;
		char charVal = 0;// 65;
		System.out.println("<<<init vs declare default value>>>");
		System.out.println("byteVal " + byteVal + " shortVal " + shortVal + " intVal " + intVal + " longVal " + longVal
				+ " boolVal " + boolVal + " floatVal " + floatVal + " doubleVal " + doubleVal + " charVal " + charVal);

		System.out.println("byteVal " + getByteVal() + " shortVal " + getShortVal() + " intVal " + getIntVal()
				+ " longVal " + getLongVal() + " boolVal " + isBoolVal() + " floatVal " + getFloatVal() + " doubleVal "
				+ getDoubleVal() + " charVal " + getcharVal());

		System.out.println("### Casiting ###");
		int number1 = 1000000;
		int number2 = 1234;

		// higher to lower cast , forces to add casting
		short sn1 = (short) number1;
		short sn2 = (short) number2;
		System.out.println("data loss " + sn1);
		System.out.println("NSo data loss " + sn2);

		// Auto Boxing // directly assigning primitive value to wrapper
		Integer counter = 20;
		Float PI = 3.14f;
		Byte wByte = 0;
		Short wShortVal = 0;
		Integer wIntVal = 0;
		Long wLongVal = 0l; // should add l for 0
		Boolean wBoolVal = false;
		Float wFloatVal = 0f;
		Double wDoubleVal = 0d;
		Character wCharVal = 0;

		System.out.println("Auto Boxing " + PI + " " + wByte + " " + wShortVal + " " + wLongVal + " " + wBoolVal + " "
				+ wCharVal + " " + wDoubleVal);

		//Unbox -- Wrapper valut to primitive value
		int unBox = counter;
		System.out.println("Un Boxing " + unBox);

		Long ll = 12345678l;
		System.out.println(ll.shortValue());
	}

	public static char getcharVal() {
		return charVal;
	}

	public char getXxx() {
		return xxx;
	}

	public static byte getByteVal() {
		return byteVal;
	}

	public static short getShortVal() {
		return shortVal;
	}

	public static int getIntVal() {
		return intVal;
	}

	public static long getLongVal() {
		return longVal;
	}

	public static boolean isBoolVal() {
		return boolVal;
	}

	public static float getFloatVal() {
		return floatVal;
	}

	public static double getDoubleVal() {
		return doubleVal;
	}

}
