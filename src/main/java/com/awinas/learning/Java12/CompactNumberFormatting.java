package com.awinas.learning.Java12;

import java.text.NumberFormat;
import java.util.Locale;

public class CompactNumberFormatting {

	public static void main(String[] args) {
		NumberFormat shortFormatter = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
		NumberFormat longFormatter = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.LONG);
		longFormatter.setMaximumFractionDigits(3);
		System.out.println(shortFormatter.format(25561L));
		System.out.println(longFormatter.format(2579001.111d));
	}
}
