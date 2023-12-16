package com.awinas.learning.interview.nielsen;

import java.util.ArrayList;
import java.util.List;

public class Question3 {

	static List<String> list = new ArrayList<String>();

	public static void main(String[] args) {

		list.add("candidateName=awinas&profile=java");
		String key = "profile";
		// new Question3().getValueBasedOnkey(key);

		String rowVal = "candidateName=awinas&profile=java";
		System.out.println(new Question3().getValue(rowVal, key));
	}

	private void getValueBasedOnkey(String key) {
		for (String s : list) {
			if (s.contains(key)) {
				String[] ar = s.split("&");
				for (String a : ar) {
					if (a.contains(key)) {
						System.out.println(a.split("=")[1]);
					}
				}
			}
		}
	}

	private String getValue(String rowVal, String key) {
		if (rowVal != null && key != null && rowVal.contains(key)) {
			String[] ar = rowVal.split("&");
			for (String a : ar) {
				if (a.contains(key)) {
					return a.split("=")[1];
				}
			}
		}

		return null;
	}

}
