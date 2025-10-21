package com.awinas.learning.coreconcepts.cloning;

//Different memory address for original & cloned object
public class TestCloning {

	public static void main(String[] args) throws CloneNotSupportedException {
		Department dept = new Department(1, "Human Resource");
		Employee original = new Employee(1, "Admin", dept);

		// Lets create a clone of original object
		Employee cloned = (Employee) original.clone();

		cloned.setEmployeeName("Awinas Kannan");
		//original.setEmployeeName("Vignesh");
		System.out.println(cloned);
		System.out.println(original);
		// Let verify using employee id, if cloning actually workded
		System.out.println(cloned.getEmpoyeeId());

		// Verify JDK's rules

		// Must be true and objects must have different memory addresses
		System.out.println(original != cloned);

		// As we are returning same class; so it should be true
		System.out.println(original.getClass() == cloned.getClass());

		// Default equals method checks for references so it should be false. If we want
		// to make it true,
		// then we need to override equals method in Employee class.
		System.out.println(original.equals(cloned));

		System.out.println("***************Shallow copy ********************");
		// First checking by chaning DEPT

		// Let change the department name in cloned object and we will verify in
		// original object

		// Both department values in original & cloned own the same instance
		// so chnage once value will change other
		// Because of this one we have to use clone in department obj also
		// and we have to call the clone and set it in new object

		// But if we change the whole dept , only dept in that employee obj will change

		System.out.println("*********************Changing  dept name alone***************");
		cloned.getDepartment().setName("Finance");

		System.out.println(original.getDepartment().getName());
		System.out.println(cloned.getDepartment().getName());

		System.out.println("*********************Changing whole dept***************");
		Department dept2 = new Department(2, "crediti");
		cloned.setDepartment(dept2);
		System.out.println(cloned);
		System.out.println(original);

		// Add clone method in department
		// modify clone in employee
		// check testdeepcopy.java

	}
}
