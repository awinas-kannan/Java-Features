package com.awinas.learning.interviewprep.middleware;

import java.util.HashMap;
import java.util.Map;

class MiddlewareRouter implements Router {
	private final Map<String, String> routes;

	public MiddlewareRouter() {
		this.routes = new HashMap<>();
	}

	@Override
	public void addRoute(String path, String result) {
		routes.put(path, result);
	}

	@Override
	public String callRoute(String path) {
		return routes.getOrDefault(path, "Route not found");
	}

	public static void main(String[] args) {
		Router router = new MiddlewareRouter();

		router.addRoute("/bar", "result");
		System.out.println(router.callRoute("/bar")); // Output: result

		router.addRoute("/foo/bar", "result2");
		System.out.println(router.callRoute("/foo/bar")); // Output: result2

		System.out.println(router.callRoute("/unknown")); // Output: Route not found
	}
}
