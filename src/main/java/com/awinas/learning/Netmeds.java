package com.awinas.learning;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Netmeds {

	public static void main(String[] args) throws ParseException {

		String a = new String();

		int x = 0;
		x += 1;
		System.out.println(x);

		System.out.println(Math.round(1.4));
		String n = null;
		String b = "aw";
		// System.out.println(n.equals(""));

		float f = 1.1F;
		String strDate = "06-06-1991 19:20:000";
		Date date = new SimpleDateFormat("dd-MM-yyyy hh:mm:sss").parse(strDate);
		System.out.println(strDate + "\t" + date);

		boolean bool = false;
		System.out.println(bool);
	}

	// Which of the below is invalid identifier with the main method?
//	a) public
//	b) private
//	c) final
//	d) None of the above

}
