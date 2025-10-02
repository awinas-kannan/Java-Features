package com.awinas.learning;

import java.util.HashMap;
import java.util.Map;

public class BooleanTest {

	public static void main(String[] args) {

		String nullBool = null;
		String nullBoolStr = "null";
		String falseBool = "false";
		String trueBool = "true";

//		System.out.println(Boolean.parseBoolean(nullBool));
//		System.out.println(Boolean.parseBoolean(nullBoolStr));
//		System.out.println(Boolean.parseBoolean(falseBool));
//		System.out.println(Boolean.parseBoolean(trueBool));
		Boolean isSellerEligibleForLMPStoreReturn = Boolean.TRUE;
		System.out.println(Boolean.TRUE.equals(isSellerEligibleForLMPStoreReturn));
		System.out.println(Boolean.TRUE.equals(null));

		Map<String, Map<String, Boolean>> testMapParent = new HashMap<>();
		Map<String, Boolean> testMapChild = new HashMap<>();
		testMapParent.put("seller", testMapChild);

		System.out.println(testMapParent.get("seller"));
		Map<String, Boolean> retrievedMap = testMapParent.get("seller");
		System.out.println(retrievedMap.get("Offerd"));
	}

}
