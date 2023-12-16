package com.awinas.learning.oop.Assocaition;

import java.util.List;

//A student obj will have list of teacher

//if the teacher leaves the school ( deleted ) , we dont need to delete student
//it still exist
public class Student {

	private String name;

	private List<Teacher> teachers;

	// getter and setter methods
}