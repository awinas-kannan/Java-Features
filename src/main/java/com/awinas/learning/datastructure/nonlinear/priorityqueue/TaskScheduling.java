package com.awinas.learning.datastructure.nonlinear.priorityqueue;

import java.util.PriorityQueue;

//Remove implements Comparable<Task>
//Exception in thread "main" java.lang.ClassCastException: class com.awinas.learning.datastructure.nonlinear.priorityqueue.Task 
//cannot be cast to class java.lang.Comparable (com.awinas.learning.datastructure.nonlinear.priorityqueue.
//Task is in unnamed module of loader 'app'; java.lang.Comparable is in module java.base of loader 'bootstrap')
// 

public class TaskScheduling {
	public static void main(String[] args) {
		PriorityQueue<Task> taskQueue = new PriorityQueue<>();

		// Add tasks with different priorities
		taskQueue.add(new Task("Task 1", 2)); // priority 2 (lower priority)
		taskQueue.add(new Task("Task 2", 1)); // priority 1 (higher priority)
		taskQueue.add(new Task("Task 3", 3)); // priority 3 (lowest priority)

		// Process tasks based on their priority
		System.out.println("Processing tasks in order of priority:");
		while (!taskQueue.isEmpty()) {
			System.out.println(taskQueue.poll());
		}
	}
}

class Task implements Comparable<Task> {
	private String name;
	private int priority;

	public Task(String name, int priority) {
		this.name = name;
		this.priority = priority;
	}

	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Task other) {
		// Higher priority task (lower number) comes first
//		return Integer.compare(this.priority, other.priority);
		return this.priority > other.priority ? 1 : -1;
	}

	@Override
	public String toString() {
		return "Task{name='" + name + "', priority=" + priority + "}";
	}
}