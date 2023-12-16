package com.awinas.learning;

public class PrimitiveSetters {

	public static void main(String[] args) {

		PrimSet set = new PrimSet();
//		set.setIntVal(null);
//		set.setLongVal(null);
//		
//		PrimSet setCons = new PrimSet(null, null);
	}

}

class PrimSet {

	int intVal;
	long longVal;

	public PrimSet() {

	}

	public PrimSet(int intVal, long longVal) {
		super();
		this.intVal = intVal;
		this.longVal = longVal;
	}

	public int getIntVal() {
		return intVal;
	}

	public void setIntVal(int intVal) {
		this.intVal = intVal;
	}

	public long getLongVal() {
		return longVal;
	}

	public void setLongVal(long longVal) {
		this.longVal = longVal;
	}

}