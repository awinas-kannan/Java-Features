package com.awinas.learning.coreconcepts.exceptionhandling;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CheckedEx {

	public static void main(String[] args) {

		try {
			// this is forcing you to handle FileNotFoundException
			FileReader f = new FileReader("Validator.java");
			// this is forcing you to handle IOException
			f.read();
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
