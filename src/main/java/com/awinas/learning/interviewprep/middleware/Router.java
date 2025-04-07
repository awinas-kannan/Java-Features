package com.awinas.learning.interviewprep.middleware;

interface Router {
	void addRoute(String path, String result);

	String callRoute(String path);
}