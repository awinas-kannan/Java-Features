package com.awinas.learning.Java14;

import java.util.Map;

public class EmpRecordMain {

	public static void main(String[] args) {
		var map = Map.of("Street name", "Street", "Dist", "Mdu", "State", "TN", "Country", "IND");
		EmpRecord empRecord1 = new EmpRecord(10, "Awi", 10000, map);
		EmpRecord empRecord2 = new EmpRecord(10, "Awi", 10000, null);

		// toString()
		System.out.println(empRecord1);
		System.out.println(empRecord1.getAddressCount());
		// accessing fields
		System.out.println("Name: " + empRecord1.name());
		System.out.println("ID: " + empRecord1.id());

		// equals()
		System.out.println(empRecord1.equals(empRecord2));

		// hashCode()
		System.out.println(empRecord1 == empRecord2);
		
		//is records
		System.out.println(empRecord1.getClass().isRecord());

		//getRecordComponents()
		
		System.out.println(empRecord1.getClass().getRecordComponents());
		
		/*
		 * Exception in thread "main" java.lang.IllegalArgumentException: employee id
		 * can't be negative at
		 * com.awinas.learning.Java14.EmpRecord.<init>(EmpRecord.java:9) at
		 * com.awinas.learning.Java14.EmpRecordMain.main(EmpRecordMain.java:8)
		 * 
		 */
		
		
		EmpRecord empRecord3 = new EmpRecord(-1, "Awi", -1, null);
		

	}

}
