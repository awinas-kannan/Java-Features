package com.awinas.learning;

import java.util.HashMap;
import java.util.Map;

//https://www.jesperdj.com/2016/07/19/dont-use-the-double-brace-initialization-trick/
public class DoubleBraceInitProblem {

	private final String text;

	public DoubleBraceInitProblem(String text) {
		this.text = text;
	}

	public int countWord(String word) {
		return word.length(); // Implementation omitted
	}

	public Map<String, Integer> countArticles() {
		// another class file is created .. check in bin folder
		return new HashMap<String, Integer>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("a", countWord("a"));
				put("an", countWord("an"));
				put("the", countWord("the"));
			}
		};
	}

	public static void main(String[] args) {
		DoubleBraceInitProblem dbi = new DoubleBraceInitProblem("ak");
		System.out.println(dbi.countArticles());
	}
}
