package com.awinas.learning.datastructure.tries;

import java.util.ArrayList;
import java.util.List;

public class Trie {

	TrieNode root = new TrieNode();

	public void insert(String str) {

		TrieNode currentNode = root;
		int index;
		for (int i = 0; i < str.length(); i++) {
			index = str.charAt(i) - 'a';
			if (currentNode.children[index] == null) {
				currentNode.children[index] = new TrieNode();
				//currentNode.children[index].characer = str.charAt(i);
				currentNode.characer = str.charAt(i);
			}
			currentNode = currentNode.children[index];

		}
		currentNode.isEndOfWord = true;
	}

	public Boolean search(String str) {

		List<String> suggestion = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		TrieNode currentNode = root;
		int index;
		for (int i = 0; i < str.length(); i++) {
			index = str.charAt(i) - 'a';
			if (currentNode.children[index] == null) {
				return false;
			}
			sb.append(str.charAt(i));
			suggestion.add(sb.toString());
			currentNode = currentNode.children[index];

		}
		/*
		 * String msg = (currentNode.isEndOfWord == Boolean.TRUE) ? sb.toString() :
		 * "not present"; System.out.println(msg);
		 */

		suggestion.forEach(System.out::println);
		return currentNode != null && currentNode.isEndOfWord;
	}

	@Override
	public String toString() {
		return "Trie [root=" + root + "]";
	}

}
