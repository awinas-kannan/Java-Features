package com.awinas.learning.Java14;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/*
 * Few important things to note about Records:
 * 
 * A record can neither extend a class nor it can be extended by another class.
 * It’s a final class. Records cannot be abstract Records cannot extend any
 * other class and cannot define instance fields inside the body. Instance
 * fields must be defined in the state description only Declared fields are
 * private and final The body of a record allows static fields and methods
 * 
 */

public record EmpRecord(int id, String name, long salary, Map<String, String> addresses) implements Serializable{

	/*
	 * JVM provides an all-arguments constructor by default that assigns its
	 * arguments to the corresponding fields. It is called the canonical
	 * constructor.
	 */

	/*
	 * Extending Records Constructor
	 * 
	 * The additional constructor defined inside the record is called a Compact
	 * constructor.
	 */
	
	public EmpRecord {

		Objects.requireNonNull(id);
		Objects.requireNonNull(name);
		if (id < 0)
			throw new IllegalArgumentException("employee id can't be negative");

		if (salary < 0)
			throw new IllegalArgumentException("employee salary can't be negative");
	}

	public EmpRecord() {
		this(1, "NA", 10000, null);
	}

	public EmpRecord(String name) {
		this(1, name, 1000, null);
	}

	/*
	 * Records Classes Can Have Methods
	 * 
	 * But, records are meant to be data carriers. We should avoid having utility
	 * methods in a record class. For example, the above method can be created in a
	 * utility class.
	 * 
	 * 
	 */

	public int getAddressCount() {
		if (this.addresses != null)
			return this.addresses().size();
		else
			return 0;
	}

}