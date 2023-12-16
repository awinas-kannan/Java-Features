package com.awinas.learning;

public class RemoveDuplicate {

	public static void main(String[] args) {
		String s = "zabbacad";

		boolean isConsicutiveAvailable = true;

		while (isConsicutiveAvailable) {
			isConsicutiveAvailable = false;
			int lenght = s.length();
			for (int i = 1; i < lenght; i++) {
				if (s.charAt(i) == s.charAt(i - 1)) {
					s = s.substring(0, i - 1) + s.substring(i + 1, lenght);
					isConsicutiveAvailable = true;
					break;
				}

			}
		}

		System.out.println(s);
	}

}
