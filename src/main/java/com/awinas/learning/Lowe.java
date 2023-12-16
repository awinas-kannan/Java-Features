package com.awinas.learning;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Lowe {

	public static void main(String[] args) {
		Employeees e1 = new Employeees("SSE", new Integer(2000));
		Employeees e2 = new Employeees("SE", new Integer(1000));
		Employeees e3 = new Employeees("SSE", new Integer(3000));
		Employeees e4 = new Employeees("SE", new Integer(2000));

		List<Employeees> elist = Arrays.asList(e1, e2, e3, e4);
		Map<String, Integer> totalSal = new HashMap<>();

//		elist.stream().forEach((e) -> {
//			if (totalSal.get(e.getDeigs()) != null) {
//				Integer sal = totalSal.get(e.getDeigs());
//				Integer salNew = sal.add(e.getSalary());
//				totalSal.put(e.getDeigs(), salNew);
//			} else {
//				totalSal.put(e.getDeigs(), e.getSalary());
//			}
//
//		});
//
//		System.out.println(totalSal);

		elist.stream()
		.collect(Collectors.groupingBy((e) -> e.getDeigs(), Collectors.summingInt(e -> e.getSalary())))
		.forEach((e, x) -> System.out.println(e + " " + x));

	}

}

class Employeees {
	private String deigs;
	private Integer salary;

	public Employeees(String deigs, Integer salary) {
		super();
		this.deigs = deigs;
		this.salary = salary;
	}

	public String getDeigs() {
		return deigs;
	}

	public void setDeigs(String deigs) {
		this.deigs = deigs;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

}
