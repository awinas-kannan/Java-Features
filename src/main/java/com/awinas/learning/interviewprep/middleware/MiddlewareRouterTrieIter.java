package com.awinas.learning.interviewprep.middleware;

import java.util.HashMap;
import java.util.Map;

class MiddlewareRouterTrieIter implements Router {

	private final Map<String, String> exactRoutes; // O(1) lookup for exact matches
	private final TrieNode wildcardRoot; // Trie for wildcard routes

	public MiddlewareRouterTrieIter() {
		this.exactRoutes = new HashMap<>();
		this.wildcardRoot = new TrieNode();
	}

	@Override
	public void addRoute(String path, String result) {
		if (path.contains("*")) {
			addWildcardRoute(path, result);
		} else {
			exactRoutes.put(path, result);
		}
	}

	private void addWildcardRoute(String path, String result) {
		String[] parts = path.split("/");
		TrieNode current = wildcardRoot;

		for (String part : parts) {
			current.children.putIfAbsent(part, new TrieNode());
			current = current.children.get(part);
		}
		current.result = result; // Mark endpoint
	}

	@Override
	public String callRoute(String path) {
		// First, check for an exact match in O(1) time
		if (exactRoutes.containsKey(path)) {
			return exactRoutes.get(path);
		}

		// If not found, search wildcard Trie iteratively
		String result = searchWildcardIterative(path);
		return result != null ? result : "No Routes Found";
	}

	private String searchWildcardIterative(String path) {
		String[] parts = path.split("/");
		TrieNode current = wildcardRoot;

		for (String part : parts) {
			if (current.children.containsKey(part)) {
				current = current.children.get(part);
			} else if (current.children.containsKey("*")) {
				current = current.children.get("*"); // Match wildcard
			} else {
				return null; // No match found
			}
		}
		return current.result;
	}

	public static void main(String[] args) {
		Router router = new MiddlewareRouterTrieIter();

		router.addRoute("/foo", "foo");
		router.addRoute("/bar/*/baz", "bar");
		router.addRoute("/foo/baz", "foo");
		router.addRoute("/foo/*", "bar");

		System.out.println(router.callRoute("/bar"));
		System.out.println(router.callRoute("/foo")); // Output: foo (exact match, O(1))
		System.out.println(router.callRoute("/foo/baz")); // Output: foo (exact match, O(1))
		System.out.println(router.callRoute("/foo/xyz")); // Output: bar (wildcard, O(m))
		System.out.println(router.callRoute("/bar/a/baz")); // Output: bar (wildcard, O(m))
		System.out.println(router.callRoute("/unknown")); // Output: null (no match)

	}
}