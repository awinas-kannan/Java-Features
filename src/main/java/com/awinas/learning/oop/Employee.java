package com.awinas.learning.oop;

import java.util.Date;

public class Employee {

	private String name;
	private Integer age;
	private Date dob;
	private Department dept;

	public Employee(String name, Integer age, Date dob, Department dept) {
		super();
		this.name = name;
		this.age = age;
		this.dob = dob;
		this.dept = dept;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", age=" + age + ", dob=" + dob + ", dept=" + dept + "]";
	}

}
