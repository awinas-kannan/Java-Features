package com.awinas.learning.Java8;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.awinas.learning.Java8.streams.MyList;

//https://www.baeldung.com/java-optional
public class OptionalInJava8 extends MyList {

	public static void main(String[] args) {
		Optional<List<Integer>> a = Optional.of(list);
		System.out.println(a.isPresent());

		// Null pointer exception
		// Optional<List<Integer>> a1 = null;
		// System.out.println(a1.isPresent());

		// Optional.ofNullable -> To denote that , the input may have null values
		List<Integer> zz = null;
		Optional<List<Integer>> ofNullable = Optional.ofNullable(zz);
		System.out.println(ofNullable.isPresent());

		// orElse()
		// if null , create new list
		List<Integer> orElse = null;
		List<Integer> orElseOpt = Optional.ofNullable(orElse).orElse(new ArrayList<Integer>());
		System.out.println(orElseOpt.size());

		List<Integer> orElseOptWithNotNull = Optional.ofNullable(list).orElse(new ArrayList<Integer>());
		System.out.println(orElseOptWithNotNull.size());

		System.out.println("******** orElse vs orElseGet ***********");
		// orElse vs orElseGet
		// Orelse is called even if the value is present
		// orElseGet is not called if the value is present

		String text = "Text present";

		System.out.println("Using orElseGet:");
		String defaultText = Optional.ofNullable(text).orElseGet(OptionalInJava8::getMyDefault);

		System.out.println("resutl : " + defaultText);

		System.out.println("Using orElse:");
		defaultText = Optional.ofNullable(text).orElse(getMyDefault());
		System.out.println("result : " + defaultText);

		System.out.println("******** Filter in optional ***********");

		Integer year = 2016;
		Optional<Integer> yearOptional = Optional.of(year);
		boolean is2016 = yearOptional.filter(y -> y == 2016).isPresent();
		System.out.println("is2016 " + is2016);
		boolean is2017 = yearOptional.filter(y -> y == 2017).isPresent();
		System.out.println("is2016 " + is2017);
	}

	public static String getMyDefault() {
		System.out.println("Getting Default Value");
		return "Default Value";
	}
}
