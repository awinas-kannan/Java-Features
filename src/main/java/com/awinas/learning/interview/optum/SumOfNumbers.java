package com.awinas.learning.interview.optum;
import java.util.Arrays;

public class SumOfNumbers {
	public static void main(String[] args) {

		Integer[] num = new Integer[] { 34, 20, 67, 90, 9 };
		int length = num.length;
		int key = 121;

		int[] threeNum = new int[3];
		boolean isSumAvailable = false;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				for (int k = 0; k < length; k++) {
					if (num[i] + num[j] + num[k] == key) {
						isSumAvailable = true;
						threeNum[0] = num[i];
						threeNum[1] = num[j];
						threeNum[2] = num[k];
						break;
					}
				}
			}
		}

		if (isSumAvailable)
			System.out.println(Arrays.toString(threeNum));
		else
			System.out.println("No such combination");

	}
}
//67 20 34

//Integer[] num = new Integer[] { -1,  9 ,20 ,34 ,67, 90  };


//Select c_status, sum(c_amt)
//from claims
//group by c_status

//select max 
//(select city_id , sum(c_amt)   from claims c, claimslink cl
//where c.cid = cl.cid
//group by cl.cityid,c.status )x  


// 9 20 34 
// 9 20 67 
// 9 20 90 

//20 34 

//-1 90 - 89 (121 - 89 ) 34 67  