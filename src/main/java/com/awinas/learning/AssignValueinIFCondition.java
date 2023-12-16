package com.awinas.learning;

public class AssignValueinIFCondition {

	public static void main(String[] args) {
		String str = "awinas";
		String str1 = null;
		if ((str1 = str) == null) {
			System.out.println();
		} else if (str1 == "awinas") {
			System.out.println("values assigend through if condition");
		}

	}

}
