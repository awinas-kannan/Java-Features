package com.awinas.learning.datastructure.tries;

import java.util.HashMap;
import java.util.Map;

public class TrieV2PhoneAndDic {
	private TrieNode root;

	// Constructor
	public TrieV2PhoneAndDic() {
		root = new TrieNode();
	}

	// Insert a word (or phone number) into the trie
	public void insert(String word) {
		TrieNode current = root;
		for (char c : word.toCharArray()) {
			current.children.putIfAbsent(c, new TrieNode());
			current = current.children.get(c);
		}
		current.isEndOfWord = true;
	}

	// Search for a word or phone number in the trie
	public boolean search(String word) {
		TrieNode current = root;
		for (char c : word.toCharArray()) {
			TrieNode node = current.children.get(c);
			if (node == null) {
				return false; // Word or phone number not found
			}
			current = node;
		}
		return current.isEndOfWord;
	}

	// Check if the trie contains any words that start with the given prefix
	public boolean startsWith(String prefix) {
		TrieNode current = root;
		for (char c : prefix.toCharArray()) {
			TrieNode node = current.children.get(c);
			if (node == null) {
				return false;
			}
			current = node;
		}
		return true; // Found the prefix
	}

	class TrieNode {
		Map<Character, TrieNode> children;
		boolean isEndOfWord;

		public TrieNode() {
			children = new HashMap<>();
			isEndOfWord = false;
		}
	}
}
