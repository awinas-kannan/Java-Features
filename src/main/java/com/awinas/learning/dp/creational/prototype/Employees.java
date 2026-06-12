package com.awinas.learning.dp.creational.prototype;

import java.util.ArrayList;
import java.util.List;

public class Employees implements Cloneable {

	private List<String> empList;

	public Employees() {
		empList = new ArrayList<String>();
	}

	public Employees(List<String> list) {
		this.empList = list;
	}

	public List<String>  loadData() {
		// read all employees from database and put into the list
		empList.add("Awinas");
		empList.add("Priya");
		empList.add("Jey");
		empList.add("Khanna");
		return empList;
	}

	public List<String> getEmpList() {
		return empList;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		List<String> temp = new ArrayList<String>();
		for (String s : this.getEmpList()) {
			temp.add(s);
		}
		return new Employees(temp);
	}
	
}