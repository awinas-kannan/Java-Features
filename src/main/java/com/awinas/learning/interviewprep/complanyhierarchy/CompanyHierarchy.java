package com.awinas.learning.interviewprep.complanyhierarchy;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/*
 * 
 * https://leetcode.com/discuss/interview-experience/6399521/Atlassian-P50-or-Jan-2025
 * 
 */
class CompanyHierarchy {
	private Map<String, Set<String>> graph; // Adjacency list representation
	private Set<String> departments; // Stores department IDs
	private Set<String> employees; // Stores employee IDs

	public CompanyHierarchy() {
		this.graph = new HashMap<>();
		this.departments = new HashSet<>();
		this.employees = new HashSet<>();
	}

	// ✅ Add a new employee explicitly
	public void addEmployee(String empId) {
		if (!employees.contains(empId)) {
			graph.putIfAbsent(empId, new HashSet<>());
			employees.add(empId);
		}
	}

	// ✅ Add a new department explicitly
	public void addDepartment(String deptId) {
		if (!departments.contains(deptId)) {
			graph.putIfAbsent(deptId, new HashSet<>());
			departments.add(deptId);
		}
	}

	// ✅ Assign an employee to a department (employee must exist)
	public void assignEmployeeToDepartment(String empId, String deptId) {
		if (!employees.contains(empId)) {
			throw new IllegalArgumentException("Employee does not exist: " + empId);
		}
		if (!departments.contains(deptId)) {
			throw new IllegalArgumentException("Department does not exist: " + deptId);
		}

		graph.get(empId).add(deptId);
		graph.get(deptId).add(empId);
	}

	// ✅ Establish reporting relationships between employees (both must exist)
	public void addReporting(String managerId, String employeeId) {
		if (!employees.contains(managerId) || !employees.contains(employeeId)) {
			throw new IllegalArgumentException("One or both employees do not exist: " + managerId + ", " + employeeId);
		}

		graph.get(managerId).add(employeeId);
		graph.get(employeeId).add(managerId);
	}

	// ✅ Find the closest department for a list of employees using BFS
	public String findClosestDepartment(List<String> employeeIds) {
		Queue<String> queue = new LinkedList<>();
		Set<String> visited = new HashSet<>();

		// Add all employees to the queue and mark them as visited
		for (String empId : employeeIds) {
			if (!employees.contains(empId)) {
				throw new IllegalArgumentException("Employee does not exist: " + empId);
			}
			queue.offer(empId);
			visited.add(empId);
		}

		// BFS to find the nearest department
		while (!queue.isEmpty()) {
			String current = queue.poll();

			// ✅ If we reach a department, return it immediately
			if (departments.contains(current)) {
				return current;
			}

			// Explore neighbors
			for (String neighbor : graph.getOrDefault(current, Collections.emptySet())) {
				if (!visited.contains(neighbor)) {
					queue.offer(neighbor);
					visited.add(neighbor);
				}
			}
		}

		return "No department found"; // If no department is reachable
	}

	// ✅ Print the graph for debugging
	public void printGraph() {
		for (Map.Entry<String, Set<String>> entry : graph.entrySet()) {
			System.out.println(entry.getKey() + " -> " + entry.getValue());
		}
	}

	public static void main(String[] args) {
		CompanyHierarchy hierarchy = new CompanyHierarchy();

		// ✅ Add employees explicitly
		hierarchy.addEmployee("emp_1");
		hierarchy.addEmployee("emp_2");
		hierarchy.addEmployee("emp_3");
		hierarchy.addEmployee("emp_4");
		hierarchy.addEmployee("emp_5");

		// ✅ Add departments explicitly
		hierarchy.addDepartment("dept_10");
		hierarchy.addDepartment("dept_20");

		// ✅ Assign employees to departments
		hierarchy.assignEmployeeToDepartment("emp_2", "dept_10");
		hierarchy.assignEmployeeToDepartment("emp_4", "dept_20");

		// ✅ Establish reporting structure
		hierarchy.addReporting("emp_1", "emp_2");
		hierarchy.addReporting("emp_1", "emp_3");
		hierarchy.addReporting("emp_3", "emp_4");
		hierarchy.addReporting("emp_3", "emp_5");

		// ✅ Print graph for visualization
		System.out.println("Company Hierarchy Graph:");
		hierarchy.printGraph();

		// ✅ Find the closest department for employees ["emp_5", "emp_1"]
		System.out.println("\nClosest Department for emp_5 and emp_1: "
				+ hierarchy.findClosestDepartment(Arrays.asList("emp_5", "emp_1"))); // Expected: dept_10
	}
}