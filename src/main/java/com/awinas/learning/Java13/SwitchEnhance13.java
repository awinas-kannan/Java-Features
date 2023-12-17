package com.awinas.learning.Java13;

public class SwitchEnhance13 {

	public static void main(String[] args) {
		var me = 4;
		var result1 = getValueFromSwitch(me, "squareMe");
		System.out.println(result1);
		var result2 = getValueFromSwitch(me, "");
		System.out.println(result2);
	}

	private static int getValueFromSwitch(int me, String operation) {
		return switch (operation) {
		case "doubleMe" -> {
			yield me * 2;
		}
		case "squareMe" -> {
			yield me * me;
		}
		default -> me;
		};
	}

}
