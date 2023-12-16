package com.awinas.learning.oop.encapsulation;

public class EncapsulatedEmployee implements Igetters, Isetters {

	private String id;
	private String name;
	private String age;
	private String dob;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAge() {
		// get age from dob
		age = "23";
		return age;
	}

	public String getDob() {

		return dob;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

}
