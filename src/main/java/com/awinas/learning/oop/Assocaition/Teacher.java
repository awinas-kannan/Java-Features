package com.awinas.learning.oop.Assocaition;

import java.util.List;

//--basically a dependency between the objects
//--Teacher dependent on student and vice versa

//A Teacher obj will have list of Student

//if the student leaves the school ( deleted ) , we dont need to delete teacher
//it still exist

//It represents a relationship between two or more objects 
//where all objects have their own life cycle and there is no owner.
//The name of an association specifies the nature of the relationship between objects.

public class Teacher {
	private String name;
	private List<Student> students;
	// getter and setter methods
}
