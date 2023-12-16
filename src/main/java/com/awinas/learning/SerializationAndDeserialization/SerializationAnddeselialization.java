package com.awinas.learning.SerializationAndDeserialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//Transient value wont be availabe after serialization 
public class SerializationAnddeselialization {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Employee input = new Employee(1, "awinas", "22");

		// serialization
		FileOutputStream fos = new FileOutputStream("abc.json");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(input);
		Employee.setComp("stpl");
		System.out.println("Employee before serialization ");
		System.out.println(input);
		// de-serialization
		FileInputStream fis = new FileInputStream("abc.json");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Employee output = (Employee) ois.readObject();

		/// Employee Id is after deserialization is null beacuse its transient

		System.out.println("Employee After serialization ");
		System.out.println("i = " + output);
		System.out.println("i = " + output.getPos());
		System.out.println("i = " + Employee.getComp());

		// de-serialization another object
		FileInputStream fis1 = new FileInputStream("abc.json");
		ObjectInputStream ois1 = new ObjectInputStream(fis1);

		com.awinas.learning.SerializationAndDeserialization.Employee output1 = (Employee) ois1.readObject();
		// com.awinas.learning.Employee output1 = (com.awinas.learning.Employee)
		// ois1.readObject();

		System.out.println("i = " + output1);
		System.out.println("i = " + output1.getPos());

	}
}