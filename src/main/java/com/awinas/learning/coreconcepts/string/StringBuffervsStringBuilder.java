package com.awinas.learning.coreconcepts.string;

import java.util.GregorianCalendar;

//https://www.journaldev.com/538/string-vs-stringbuffer-vs-stringbuilder
//https://www.digitalocean.com/community/tutorials/string-vs-stringbuffer-vs-stringbuilder


public class StringBuffervsStringBuilder {

	public static void main(String[] args) {
		System.gc();

		System.out.println("*******String Buffer******10000000*****");
		long start = new GregorianCalendar().getTimeInMillis();
		long startMemory = Runtime.getRuntime().freeMemory();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < 10000000; i++) {
			stringBuffer.append(":").append(i);
		}
		long end = new GregorianCalendar().getTimeInMillis();
		long endMemory = Runtime.getRuntime().freeMemory();
		System.out.println("Time Taken:" + (end - start));
		System.out.println("Memory used:" + (startMemory - endMemory));
		System.out.println(stringBuffer.length());
		System.out.println("*******String Builder******10000000*****");
		start = new GregorianCalendar().getTimeInMillis();
		startMemory = Runtime.getRuntime().freeMemory();
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < 10000000; i++) {
			stringBuilder.append(":").append(i);
		}
		end = new GregorianCalendar().getTimeInMillis();
		endMemory = Runtime.getRuntime().freeMemory();
		System.out.println("Time Taken:" + (end - start));
		System.out.println("Memory used:" + (startMemory - endMemory));
		System.out.println(stringBuilder.length());
	}

}
