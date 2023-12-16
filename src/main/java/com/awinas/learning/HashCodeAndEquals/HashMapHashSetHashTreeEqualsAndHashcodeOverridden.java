package com.awinas.learning.HashCodeAndEquals;

import java.util.HashSet;
import java.util.Set;

//https://dzone.com/articles/working-with-hashcode-and-equals-in-java
/*
 * If two objects are equal, they MUST have the same hash code.
 * 
 *  If two objects
 * have the same hash code, it doesn't mean that they are equal. //EqualsOverridenInStringClass.java 
 * 
 * Overriding
 * equals() alone will make your business fail with hashing data structures
 * like: HashSet, HashMap, HashTable ... etc. 
 * 
 * Overriding hashcode() alone
 * doesn't force Java to ignore memory addresses when comparing two objects.
 */

//Set has only unique values
// HashSet internally uses hashmap algo //
//It uses hash code to check unique values
//If hashcode method is not over riden // then every employee obj will have diff hashcode
//If hashcode is overriden with emp id , then employee obj will have same hashcode for same emp id
//So only the unique Emp id will be added in set
public class HashMapHashSetHashTreeEqualsAndHashcodeOverridden {

	public static void main(String[] args) {

		Employee emp1 = new Employee(1, "raj", "25");
		Employee emp2 = new Employee(2, "manoj", "35");
		Employee emp3 = new Employee(3, "awinas", "23");
		Employee emp4 = new Employee(1, "raj", "25");
		Employee emp5 = new Employee(1, "raj", "27");
		System.err.println("emp1 " + emp1.hashCode());
		System.out.println("emp2 " + emp2.hashCode());
		System.out.println("emp3 " + emp3.hashCode());
		System.out.println("emp4 " + emp4.hashCode());
		System.out.println("emp5 " + emp5.hashCode());

		Set<Employee> hashSet = new HashSet<>();
		hashSet.add(emp1);
		hashSet.add(emp2);
		hashSet.add(emp3);
		//add debugger here and check
		hashSet.add(emp4);
		hashSet.add(emp5);

		System.out.println(hashSet);
		// O/p when hashcode & equals is over riden
		// Only three obj will be present in set
		// AS last two employee has same ID as first one

		// [Employee [empId=1, empName=raj, empAge=25, pos=Developer],
		// Employee [empId=2, empName=manoj, empAge=35, pos=Developer],
		// Employee [empId=3, empName=awinas, empAge=23, pos=Developer]]

		// O/p when equals alone is overridden .Hashcode method not overridden
		// ALL Objects will be added ...

		// [Employee [empId=3, empName=awinas, empAge=23, pos=Developer],
		// Employee [empId=1, empName=raj, empAge=27, pos=Developer],
		// Employee [empId=1, empName=raj, empAge=25, pos=Developer],
		// Employee [empId=2, empName=manoj, empAge=35, pos=Developer],
		// Employee [empId=1, empName=raj, empAge=25, pos=Developer]]

		HashSet<Employee> ihashset = new HashSet<>();
		System.out.println("empty -- " + ihashset.hashCode());

		ihashset.add(emp1);
		System.out.println("ihashset.hashCode() " + ihashset.hashCode());
		ihashset.add(emp2);
		System.out.println("ihashset.hashCode() " + ihashset.hashCode());

		System.out.println("hashSet equals ihashset--" + hashSet.equals(ihashset));
		System.out.println(ihashset.contains(emp3));
		ihashset.add(emp3);
		System.out.println("hashSet equals ihashset--" + hashSet.equals(ihashset));
		System.out.println(ihashset.contains(emp3));

	}
}
