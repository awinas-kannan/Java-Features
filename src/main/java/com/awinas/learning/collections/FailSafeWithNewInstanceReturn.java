package com.awinas.learning.collections;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

//Cloned object are returned 
//so no concurrent modification exception thrown

//Check internal implementation of iterator();

class FailSafeWithNewInstanceReturn {
	public static void main(String args[]) {
		CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>(new Integer[] { 1, 3, 5, 8 });
		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			Integer no = (Integer) itr.next();
			System.out.println(no);
			if (no == 8)
				// This will not print,
				// hence it has created separate copy
				list.add(14);
		}

		// 14 WILL NOT BE PRINTED AS , ITERATOR RETURNNS NEW INSTANCE
		// AND WE HAVE MADE MODIFICATION IN ORIGINAL LIST
	}
}