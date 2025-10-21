package com.awinas.learning.coreconcepts.garbagecollection;

//https://www.geeksforgeeks.org/garbage-collection-java/

//Steps
//Set references to null(i.e X = Y = null;)
//Call, System.gc();
//Call, System.runFinalization();
public class GcFinalization2 {

	public static void main(String[] args) {

		Employee E = new Employee("GFG1", 56);
		Employee F = new Employee("GFG2", 45);
		Employee G = new Employee("GFG3", 25);
		E.show();
		F.show();
		G.show();
		E.showNextId();
		F.showNextId();
		G.showNextId();

		{

			// It is sub block to keep
			// all those interns.
			Employee X = new Employee("GFG4", 23);
			Employee Y = new Employee("GFG5", 21);
			X.show();
			Y.show();
			X.showNextId();
			Y.showNextId();

			// Adding gc to make next int 4
			// decrement handled in finalize

			X = Y = null;
			System.gc();

			// Without this printing wrong results
			System.runFinalization();
		}

		// without system.gc() , this will print 6
		// After countering this brace, X and Y
		// will be removed.Therefore,
		// now it should show nextId as 4.
		System.out.println("*************** Next  Id **************");
		E.showNextId();
	}

}
