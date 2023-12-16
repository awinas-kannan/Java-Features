package com.awinas.learning.HashCodeAndEquals;

public class HashCode {

	public static void main(String[] args) {
		/*
		 * 366712642 1829164700 2018699554 1311053135 118352462
		 */
		Employee emp1 = new Employee();
		Employee emp2 = new Employee(1);
		Employee emp3 = new Employee(1, "awi", "23");
		Employee emp4 = new Employee();
		Employee emp5 = new Employee();

		System.out.println(emp1.hashCode());
		System.out.println(emp2.hashCode());
		System.out.println(emp3.hashCode());
		System.out.println(emp4.hashCode());
		System.out.println("Hash Code before employee id");
		System.out.println(emp5.hashCode());
		emp5.setEmpId(1);
		System.out.println("Hash Code After employee id");
		//same with out equals method //Different with equals method
		System.out.println(emp5.hashCode()); 
		

		System.out.println(emp1.equals(emp4)); //True when equals method is over riden ..Because both Employee id = null
		System.out.println(emp1.equals(emp2));
		System.out.println(emp2.equals(emp3));
		System.out.println(emp3.equals(emp4));
		System.out.println(emp3.equals(emp5));


	}

}
