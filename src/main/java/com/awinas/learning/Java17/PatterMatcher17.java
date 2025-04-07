package com.awinas.learning.Java17;

public class PatterMatcher17 {

	public static void main(String[] args) {

//		Before Java 17
		String s = "Foo";

		if (s == null) {
			System.out.println("oops!");
		}

		switch (s) {
		case "Foo", "Bar" -> System.out.println("Great");
		default -> System.out.println("Ok");
		}

//		switch (s) {
//		case null -> System.out.println("Oops");
//		case "Foo", "Bar" -> System.out.println("Great");
//		default -> System.out.println("Ok");
//		}

//		Before Java 16
		Object o = "AK";
		if (o instanceof String) {
			String s1 = (String) o;
			String.format("String %s", s1);
		} else if (o instanceof Integer) {
			Integer i = (Integer) o;
			String.format("int %d", i);
		} else if (o instanceof Double) {
			Double d = (Double) o;
			String.format("double %f", d);
		}

		// In Java 16
		if (o instanceof String s1) {
			String.format("String %s", s1);
		} else if (o instanceof Integer i) {
			String.format("int %d", i);
		} else if (o instanceof Double d) {
			String.format("double %f", d);
		}

		// Java 17
//		System.out.println(formatterJava17("Java 17"));
//		System.out.println(formatterJava17(17));
	}

//	static String formatterJava17(Object o) {
//        return switch (o) {
//            case Integer i -> String.format("int %d", i);
//            case Long l    -> String.format("long %d", l);
//            case Double d  -> String.format("double %f", d);
//            case String s  -> String.format("String %s", s);
//            default        -> o.toString();
//        };
//    }

}
