package com.awinas.learning.coreconcepts.cloning;

public class Employee implements Cloneable {

	private int empoyeeId;
	private String employeeName;
	private Department department;

	public Employee(int id, String name, Department dept) {
		this.empoyeeId = id;
		this.employeeName = name;
		this.department = dept;
	}

	public int getEmpoyeeId() {
		return empoyeeId;
	}

	public void setEmpoyeeId(int empoyeeId) {
		this.empoyeeId = empoyeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return "Employee [empoyeeId=" + empoyeeId + ", employeeName=" + employeeName + ", department=" + department
				+ "]";
	}

	// Modified clone() method in Employee class
//	@Override
//	protected Object clone() throws CloneNotSupportedException {
//		Employee cloned = (Employee) super.clone();
//		// department clone will give the new department obj ..
//		// (Diff memory address)
//		System.out.println(cloned.getDepartment().hashCode());
//		System.out.println(cloned.getDepartment().clone().hashCode());
//		cloned.setDepartment((Department) cloned.getDepartment().clone());
//		return cloned;
//	}

}