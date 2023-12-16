package com.awinas.learning.HashCodeAndEquals;

import java.io.Serializable;

public class Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Integer empId;
	String empName;
	String empAge;
	static String comp = "sella";
	final String pos = "Developer";

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

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpAge() {
		return empAge;
	}

	public void setEmpAge(String empAge) {
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

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", empAge=" + empAge + ", pos=" + pos + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empId == null) ? 0 : empId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Employee))
			return false;
		Employee other = (Employee) obj;
		if (empId == null) {
			if (other.empId != null)
				return false;
		} else if (!empId.equals(other.empId))
			return false;
		return true;
	}

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		//System.out.println(getClass()); //class com.awinas.learning.HashCodeAndEquals.Employee
//		//System.out.println(obj.getClass().getSimpleName()); //Employee
//		if (getClass() != obj.getClass())
//			return false;
//		Employee other = (Employee) obj;
//		if (empId == null) {
//			if (other.empId != null)
//				return false;
//		} else if (!empId.equals(other.empId))
//			return false;
//		return true;
//	}

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj) {
//			return true;
//		}
//		if (obj == null) {
//			return false;
//		}
//		if (getClass() != obj.getClass()) {
//			return false;
//		}
//		Employee other = (Employee) obj;
//		if (empId == null) {
//			if (other.empId != null) {
//				return false;
//			}
//		} else if (!empId.equals(other.empId)) {
//			return false;
//		}
//		return true;
//	}



}