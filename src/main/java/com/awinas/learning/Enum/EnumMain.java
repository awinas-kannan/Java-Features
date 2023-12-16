package com.awinas.learning.Enum;

public class EnumMain {

	public static void main(String[] args) {

		System.out.println("*************************");
		System.out.println(UserGroup.ICICI_EMPLOYEE.getName());
		System.out.println(UserGroup.ICICI_CUSTOMER.getName());
		System.out.println("*************************");
		System.out.println(UserGroup.resolveGroupsByName("HDFC"));
		UserGroup ug = UserGroup.resolveGroupsByName("HDFC");
		System.out.println(" " + ug.name);
		System.out.println(" " + ug.getBankname());
		System.out.println(" " + ug.getName());
		System.out.println(" " + ug.getChannels());
		System.out.println("*************************");

		System.out.println("*************************");
		// System.out.println(UserGroup.resolveGroupsByName("HDFC_AWI"));
		System.out.println("*********Order in Enum ***********");
		System.out.println(UserGroup.ICICI_CUSTOMER.ordinal());
		System.out.println("*********Value of in Enum ***********");
		UserGroup ug1 = UserGroup.valueOf("ICICI_CUSTOMER");
		System.out.println(ug1.getName());

		System.out.println("*********Comaprision ***********");
		// True
		// because enums are singlton and comparable by default
		UserGroup ug2 = UserGroup.valueOf("ICICI_CUSTOMER");
		UserGroup ug3 = UserGroup.valueOf("ICICI_CUSTOMER");
		System.out.println(ug2 == ug3);
		System.out.println(ug2.equals(ug3));

	}

}
