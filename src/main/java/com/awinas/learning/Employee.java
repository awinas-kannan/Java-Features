package com.awinas.learning;

import java.io.Serializable;

public class Employee implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	transient Integer empId;
	String empName;
	String empAge;
	static String comp = "sella";
	final String pos = "dev";

	public Employee() {
		super();

	}

	public Employee(Integer empId) {
		super();
		this.empId = empId;
	}

	public Employee(Integer empId, String empName, String empAge) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.empAge = empAge;
	}

	public static String getComp() {
		return comp;
	}

	public static void setComp(String comp) {
		Employee.comp = comp;
	}

	public String getPos() {
		return pos;
	}

	/**
	 * @return the empId
	 */
	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empId == null) ? 0 : empId.hashCode());
		return result;
	}

	/*
	 * @Override public boolean equals(Object obj) { if (this.empId == ((Employee)
	 * obj).getEmpId()) return true; return false; }
	 */

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", empAge=" + empAge + "]";
	}

	public String getEmpAge() {
		return empAge;
	}

	public void setEmpAge(String empAge) {
		this.empAge = empAge;
	}

}