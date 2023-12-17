package com.awinas.learning.Java11;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FilesStringMethod {
	public static void main(String[] args) throws IOException {
		/*
		 * Files.readString Files.writeString
		 */

		Path filePath = Paths.get("../Java-Features/src/main/java/com/awinas/learning/Java11/fromFile.txt");
		Path filePathTo = Paths.get("../Java-Features/src/main/java/com/awinas/learning/Java11/ToFile.txt");
		String fileContent = Files.readString(filePath, StandardCharsets.UTF_8);
		System.out.println(fileContent);

		String[] rows = fileContent.split("\n");
		for (String row : rows) {
			System.out.println("Name: " + row);
			String lineContent = "Name: " + row + "\n";
			Files.writeString(filePathTo, lineContent, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
		}

	}
}
