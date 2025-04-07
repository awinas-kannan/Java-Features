package com.awinas.learning.interviewprep.middleware;

class MiddlewareRouterTrie implements Router {
	
	private final TrieNode root;

	public MiddlewareRouterTrie() {
		this.root = new TrieNode();
	}

	@Override
	public void addRoute(String path, String result) {
		String[] parts = path.split("/");
		TrieNode current = root;

		for (String part : parts) {
			current.children.putIfAbsent(part, new TrieNode());
			current = current.children.get(part);
		}
		current.result = result; // Mark the end of a valid route.
	}

	@Override
	public String callRoute(String path) {
		String[] parts = path.split("/");
		return search(root, parts, 0);
	}

	private String search(TrieNode node, String[] parts, int index) {
		if (node == null)
			return null;
		if (index == parts.length)
			return node.result; // Return result if at an endpoint.

		// Exact match case
		if (node.children.containsKey(parts[index])) {
			String exactMatch = search(node.children.get(parts[index]), parts, index + 1);
			if (exactMatch != null)
				return exactMatch;
		}

		// Wildcard match case
		if (node.children.containsKey("*")) {
			String wildcardMatch = search(node.children.get("*"), parts, index + 1);
			if (wildcardMatch != null)
				return wildcardMatch;
		}

		return "No Routes Found"; // No match found
	}

	public static void main(String[] args) {
		Router router = new MiddlewareRouterTrie();

//		router.addRoute("/bar", "result");
		router.addRoute("/foo", "foo");
		router.addRoute("/bar/*/baz", "bar");
		router.addRoute("/foo/baz", "foo");
		router.addRoute("/foo/*", "bar");

		System.out.println(router.callRoute("/bar"));
		System.out.println(router.callRoute("/bar/a/baz")); // Output: bar
		System.out.println(router.callRoute("/foo")); // Output: foo (exact match)
		System.out.println(router.callRoute("/foo/xyz")); // Output: bar (wildcard match)
		System.out.println(router.callRoute("/foo/baz")); // Output: foo (exact match)
		System.out.println(router.callRoute("/bar/x/baz")); // Output: bar (wildcard match)
		System.out.println(router.callRoute("/unknown")); // Output: null (no match)
	}
}
