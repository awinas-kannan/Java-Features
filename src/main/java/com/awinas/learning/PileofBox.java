package com.awinas.learning;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PileofBox {
  
	
	public static long pilesOfBoxes(List<Integer> boxesInPiles) {
		long no = 0l;
		boolean isAllOne = false;
		while (!isAllOne) {
			Set<Integer> setI = new TreeSet<>();
			setI.addAll(boxesInPiles);
			int sizeSet = setI.size();
			int x = 0;
			int secondMin = 0;
			int maxval = 0;
			for (Integer i : setI) {
				if (x == sizeSet - 1) {
					maxval = i;
				}
				if (x == sizeSet - 2) {
					secondMin = i;
				}
				x++;
			}
			int maxIdx = 0;
			for (int i = 0; i < boxesInPiles.size(); i++) {
				if (boxesInPiles.get(i) == maxval) {
					maxIdx = i;
					break;
				}
			}
			if (maxval > secondMin) {
				int rem = maxval - secondMin;
				boxesInPiles.remove(maxIdx);
				boxesInPiles.add(maxIdx, maxval - rem);
				no++;
			}
			if (isAllEqal(boxesInPiles)) {
				isAllOne = true;
			}
		}
		return no;

	}

	private static boolean isAllEqal(List<Integer> boxesInPiles) {
		Set<Integer> setI = new TreeSet<>(boxesInPiles);
		return setI.size() == 1;
	}

}
//Alex is given n piles of boxes of equal or unequal heights. 
//In one step, Alex can remove any number of boxes from the pile which has the maximum height 
//and try to make it equal to the one which is just lower than the maximum height of the stack.
//Determine the minimum number of steps required to make all of the piles equal in height.

//7 6 5  4 3 2 1

//6 6 5 4 3 2 1

//6 5 5 4 3 2 1

//5 5 5 4 3 2 1

// O(nlog(n)) +  o(n) + o(n)

