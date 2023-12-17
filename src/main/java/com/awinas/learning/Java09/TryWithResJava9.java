package com.awinas.learning.Java09;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
 * https://howtodoinjava.com/java/try-with-resources/
 */
public class TryWithResJava9 {

	public static void main(String[] args) {
		String fromFile = "../Java-Features/src/main/java/com/awinas/learning/java9/fromFile.txt";
		String toFile = "../Java-Features/src/main/java/com/awinas/learning/java9/ToFile.txt";

		try {
			tryWithRes_Before_Java7(fromFile, toFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Copy file is done.");
	}

	void testARM_Before_Java9() throws IOException {
		BufferedReader reader1 = new BufferedReader(new FileReader("journaldev.txt"));
		try (BufferedReader reader2 = reader1) {
			System.out.println(reader2.readLine());
		}
	}

	/*
	 * Copy from one file to another
	 */
	public static void tryWithRes_Before_Java7(String from, String to) throws IOException {

		InputStream inStream = null;
		OutputStream outStream = null;

		try {

			inStream = new FileInputStream(new File(from));
			outStream = new FileOutputStream(new File(to));

			byte[] buffer = new byte[1024];

			// if this throws exception
			int length;
			while ((length = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
				outStream.flush();
			}

			System.out.println("<<tryWithRes_Before_Java7>> Copied");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("<<tryWithRes_Before_Java7>> Finally");
			// if this throws exception
			if (inStream != null)
				inStream.close();

			// this has a leak
			if (outStream != null)
				outStream.close();
		}

		/*
		 * However, this example might have a resource leak; if the while copy bytes
		 * process throws an exception, and the inStream.close() throws an exception,
		 * then the outStream has a resource leak and causes a performance issue. We can
		 * solve this by wrapping the close stream with another layer of try-catch, but
		 * it’s too lengthy and error-prone.
		 * 
		 */

	}

	public static void tryWithRes_Java7(String from, String to) {

		try (InputStream inStream = new FileInputStream(new File(from));
				OutputStream outStream = new FileOutputStream(new File(to))) {

			byte[] buffer = new byte[1024];

			int length;
			while ((length = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
				outStream.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Copy from one file to another
	 */
	public static void tryWithRes_Java9(String from, String to) throws FileNotFoundException {

		InputStream inStream = new FileInputStream(new File(from));
		OutputStream outStream = new FileOutputStream(new File(to));

		try (inStream; outStream) {

			byte[] buffer = new byte[1024];

			int length;
			while ((length = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
				outStream.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

//		outStream = new FileOutputStream(new File(to)); // change again, not effective final
	}

}
