package com.awinas.learning.oop.inheritance;

//Parent fields cant be over ridden
//Parent methods will be overridden

//Member fields are accessed from reference type class.
//Member methods are accessed from actual instance types.
public class InheritanceMain {

	public static void main(String[] args) {

		System.out.println("manager object with manager reference");
		Manager m1 = new Manager();
		// method
		System.out.println(m1.getWho());
		// field
		System.out.println(m1.id);

		// If getId() not overriden, then employee id will be displayed
		// even though we have id in manager
		/// {{Member methods are accessed from actual instance types.}}
		System.out.println(m1.getId());

		System.out.println("manager object with Employee reference");
		Employee e1 = new Manager();
		System.out.println(e1.getWho());
		// Eventhough we have id in manager. Employee id will be fetched
		// {{Member fields are accessed from reference type class.}}
		System.out.println(e1.id);
		System.out.println(e1.getId());

	}

}
