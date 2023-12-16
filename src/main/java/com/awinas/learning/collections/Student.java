package com.awinas.learning.collections;

public class Student implements Comparable<Student> {

	private int no;
	private int mark1;
	private int mark2;
	private int mark3;
	private int total;

	public Student(int no, int mark1, int mark2, int mark3) {
		super();
		this.no = no;
		this.mark1 = mark1;
		this.mark2 = mark2;
		this.mark3 = mark3;
		this.total = mark1 + mark2 + mark3;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getMark1() {
		return mark1;
	}

	public void setMark1(int mark1) {
		this.mark1 = mark1;
	}

	public int getMark2() {
		return mark2;
	}

	public void setMark2(int mark2) {
		this.mark2 = mark2;
	}

	public int getMark3() {
		return mark3;
	}

	public void setMark3(int mark3) {
		this.mark3 = mark3;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public int compareTo(Student o) {

		return this.total > o.total ? 1 : -1;
	}

}
