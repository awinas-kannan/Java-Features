package com.awinas.learning.datastructure.tries;

public class MainClass {

	public static void main(String[] args) {

		Trie trie = new Trie();
		String[] keys = { "the", "a", "there", "answer", "any", "by", "bye", "their" };

		String output[] = { "Not present in trie", "Present in trie" };

		for (int i = 0; i < keys.length; i++)
			trie.insert(keys[i]);

		System.out.println(trie.toString());

		if (trie.search("the") == Boolean.TRUE)
			System.out.println("the --- " + output[1]);
		else
			System.out.println("the --- " + output[0]);

		if (trie.search("bye") == Boolean.TRUE)
			System.out.println("bye --- " + output[1]);
		else
			System.out.println("bye --- " + output[0]);

		if (trie.search("these") == Boolean.TRUE)
			System.out.println("these --- " + output[1]);
		else
			System.out.println("these --- " + output[0]);

		if (trie.search("their") == Boolean.TRUE)
			System.out.println("their --- " + output[1]);
		else
			System.out.println("their --- " + output[0]);

		if (trie.search("thaw") == Boolean.TRUE)
			System.out.println("thaw --- " + output[1]);
		else
			System.out.println("thaw --- " + output[0]);

		System.out.println("************");
		System.out.println(trie.search("thei"));
		
	}

}
