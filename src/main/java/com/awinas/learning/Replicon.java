package com.awinas.learning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class Result {

	/*
	 * Complete the 'getArticleTitles' function below.
	 *
	 * The function is expected to return a STRING_ARRAY. The function accepts
	 * STRING author as parameter.
	 * 
	 * URL for cut and paste:
	 * https://jsonmock.hackerrank.com/api/articles?author=<authorName>&page=<num>
	 *
	 */

	public static List<String> getArticleTitles(String author) {

		List<String> articles = new ArrayList<>();
		String strUrl = "https://jsonmock.hackerrank.com/api/articles?author=" + author;
		StringBuilder content = new StringBuilder();
		Response resp = getRespo(strUrl, content);

		for (Data dat : resp.getData()) {

			if (dat.getTitle() != null) {
				articles.add(dat.getTitle());
			} else if (dat.getStory_title() != null) {
				articles.add(dat.getStory_title());
			}

		}

		return articles;

	}

	private static Response getRespo(String strUrl, StringBuilder content) {
		Response resp = null;
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
				content.append(in.readLine());
			}
			System.out.println(content.toString());
			Gson gson = new GsonBuilder().serializeNulls().create();
			resp = gson.fromJson(content.toString(), Response.class);

		} catch (Exception e) {

		}
		return resp;
	}
}

public class Replicon {
	public static void main(String[] args) throws IOException {
//		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

//		String author = bufferedReader.readLine();

		List<String> result = Result.getArticleTitles("epaga");
		System.out.println(result);
//		bufferedWriter.write(result.stream().collect(joining("\n")) + "\n");
//
//		bufferedReader.close();
//		bufferedWriter.close();
	}
}

class Response {
	String page;
	String per_page;
	String total;
	String total_pages;
	List<Data> data;

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPer_page() {
		return per_page;
	}

	public void setPer_page(String per_page) {
		this.per_page = per_page;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTotal_pages() {
		return total_pages;
	}

	public void setTotal_pages(String total_pages) {
		this.total_pages = total_pages;
	}

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Response [page=" + page + ", per_page=" + per_page + ", total=" + total + ", total_pages=" + total_pages
				+ ", data=" + data + "]";
	}

}

class Data {
	String title;
	String story_title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStory_title() {
		return story_title;
	}

	public void setStory_title(String story_title) {
		this.story_title = story_title;
	}

	@Override
	public String toString() {
		return "Data [title=" + title + ", story_title=" + story_title + "]";
	}

}