package com.awinas.learning.MutableAndImmutable;

public class MutableEmployee {

	String name;
	String age;
	String tech;

	public MutableEmployee(String name, String age, String tech) {
		super();
		this.name = name;
		this.age = age;
		this.tech = tech;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getTech() {
		return tech;
	}

	public void setTech(String tech) {
		this.tech = tech;
	}

	@Override
	public String toString() {
		return "MutableEmployee [name=" + name + ", age=" + age + ", tech=" + tech + "]";
	}

}
