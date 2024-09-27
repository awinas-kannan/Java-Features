package com.awinas.learning.datastructure.tries;

public class TrieV2Main {

	public static void main(String[] args) {
		TrieV2PhoneAndDic trie = new TrieV2PhoneAndDic();

		// Insert words into the trie
		trie.insert("apple");
		trie.insert("app");
		trie.insert("banana");
		trie.insert("ball");

		// Insert phone numbers into the trie
		trie.insert("1234567890");
		trie.insert("9876543210");

		// Search for words in the trie
		System.out.println("Search for 'apple': " + trie.search("apple")); // true
		System.out.println("Search for 'app': " + trie.search("app")); // true
		System.out.println("Search for 'banana': " + trie.search("banana")); // true
		System.out.println("Search for 'ball': " + trie.search("ball")); // true
		System.out.println("Search for 'bat': " + trie.search("bat")); // false

		// Search for phone numbers in the trie
		System.out.println("Search for '1234567890': " + trie.search("1234567890")); // true
		System.out.println("Search for '9876543210': " + trie.search("9876543210")); // true
		System.out.println("Search for '1111111111': " + trie.search("1111111111")); // false

		// Check if the trie contains any words that start with a given prefix
		System.out.println("Starts with 'app': " + trie.startsWith("app")); // true
		System.out.println("Starts with 'ban': " + trie.startsWith("ban")); // true
		System.out.println("Starts with 'bal': " + trie.startsWith("bal")); // true
		System.out.println("Starts with 'bat': " + trie.startsWith("bat")); // false

	}

}
