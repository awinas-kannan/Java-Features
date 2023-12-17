package com.awinas.learning.Java12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileMismatch {

	public static void main(String[] args) throws IOException {
		/*
		 * mismatch() method compares two file paths and return a long value. The long
		 * indicates the position of the first mismatched byte in the content of the two
		 * files. The return value will be '–1' if the files are “equal.”
		 */
		Path filePathFrom = Paths.get("../Java-Features/src/main/java/com/awinas/learning/Java12/fromFile.txt");
		Path filePathTo = Paths.get("../Java-Features/src/main/java/com/awinas/learning/Java12/ToFile.txt");
		long diff = Files.mismatch(filePathFrom, filePathTo);
		System.out.println("diff " + diff);
		
		diff = Files.mismatch(filePathFrom, filePathFrom);
		System.out.println("diff " + diff);
	}

}
