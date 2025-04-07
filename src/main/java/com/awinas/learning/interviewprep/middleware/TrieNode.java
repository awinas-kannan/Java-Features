package com.awinas.learning.interviewprep.middleware;

import java.util.HashMap;
import java.util.Map;

class TrieNode {
	Map<String, TrieNode> children;
	String result;
	boolean isEnd;

	public TrieNode() {
		this.children = new HashMap<>();
		this.result = null;
		this.isEnd = false;
	}
}