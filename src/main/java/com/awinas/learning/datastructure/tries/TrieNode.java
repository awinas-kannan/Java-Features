package com.awinas.learning.datastructure.tries;

import java.util.Arrays;

public class TrieNode {

	Character characer;
	TrieNode[] children = new TrieNode[26];
	Boolean isEndOfWord;

	public TrieNode() {

		for (int i = 0; i < 26; i++) {
			children[i] = null;
		}

		isEndOfWord = false;
	}

	@Override
	public String toString() {
		return "TrieNode [characer=" + characer + ", children=" + Arrays.toString(children) + ", isEndOfWord="
				+ isEndOfWord + "]";
	}
	
	

}
