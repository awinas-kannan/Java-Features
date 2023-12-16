package com.awinas.learning.algorithm;

import java.util.ArrayList;
import java.util.List;

public class PhoneBook {

	public static void main(String[] args) {
		TrieDs ds = new TrieDs();
		ds.search("awi");
	}
}

class TrieDs {

	Trie rootNode = new Trie();

	public List<String> search(String input) {

		Trie currentNode = rootNode;
		List<String> output = new ArrayList<>();
		System.out.println((int) 'a');
		for (int i = 0; i < input.length(); i++) {
			Character c = input.charAt(i);
			int ascii = c - 97;
			System.out.println(c);
			System.out.println(ascii);
			if (currentNode.nodes[ascii] != null) {
				currentNode = currentNode.nodes[ascii];
			}
		}

		return output;

	}
}

class Trie {

	Character value;
	Trie[] nodes = new Trie[26];

	Trie() {
		for (int i = 0; i < 26; i++) {
			nodes[i] = null;
		}
	}

}
