package com.awinas.learning.Java9;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientJava9 {
	public static void main(String[] args) throws URISyntaxException {
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI("//howtodoinjava.com/")).GET().build();
//		HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandler.asString());
//		System.out.println(httpResponse.body());
//		if (httpResponse.isDone()) {
//			System.out.println(httpResponse.get().statusCode());
//			System.out.println(httpResponse.get().body());
//		} else {
//			httpResponse.cancel(true);
//		}
	}
}
