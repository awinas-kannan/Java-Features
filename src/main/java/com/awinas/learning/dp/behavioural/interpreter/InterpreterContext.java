package com.awinas.learning.dp.behavioural.interpreter;

public class InterpreterContext {

	public String getBinaryFormat(int i) {
		return Integer.toBinaryString(i);
	}

	public String getHexadecimalFormat(int i) {
		return Integer.toHexString(i);
	}
}