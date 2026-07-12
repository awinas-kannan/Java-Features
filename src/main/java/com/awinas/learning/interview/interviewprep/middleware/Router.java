package com.awinas.learning.interview.interviewprep.middleware;

interface Router {
	void addRoute(String path, String result);

	String callRoute(String path);
}