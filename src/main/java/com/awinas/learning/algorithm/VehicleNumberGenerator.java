package com.awinas.learning.algorithm;
import java.util.ArrayList;
import java.util.List;

public class VehicleNumberGenerator {
	/*
### Time Complexity:

The program involves three nested loops, and let's break them down:

1. First two digits (00-99): There are 100 possibilities (from 0 to 99), which results in ( O(100) ).
2. Next four digits (0000-9999): There are 10,000 possibilities (from 0000 to 9999), resulting in ( O(10,000) ).
3. Last one or two alphabets (A-Z):
   - For one alphabet: 26 possibilities.
   - For two alphabets: 26 possibilities for the first letter and 26 possibilities for the second, giving ( 26 + 26 times 26 = 26 + 676 = 702 ) possibilities.
   - This results in ( O(702) ).

The total number of iterations is the product of these possibilities:


O(100 times 10,000 times 702) = O(702,000,000)


Thus, the time complexity is ( O(702,000,000) ), or simply ( O(N) ) where ( N ) is the number of possible vehicle number plate combinations.

### Space Complexity:

1. Space for storing the list of number plates: Each generated number plate string takes constant space, and the total number of strings is ( 702,000,000 ). If each string is approximately 12 characters long (since the format is fixed, "01 BH 0001 AA" = 12 characters including spaces), each string takes approximately 12 bytes of memory.
   
   Hence, the space required is:
   
   
   O(702,000,000 times 12) = O(8.424 times 10^9) , text{bytes} = O(8.4 , text{GB})
   
   
   So, the space complexity for storing the plates is approximately ( O(N) ), where ( N ) is the number of generated number plates.

2. Space for intermediate variables and loops: These take constant space, so their contribution is ( O(1) ).

Thus, the overall space complexity is ( O(N) ).
	

	 1 KB - 1000 Bytes
	 1 MB - 1000 KB
	 1 GM - 1000 MB
	 So 8424000000 /1000/1000/1000 = 8.4 GB
	 
	 */
    // Method to generate the sequences
    public static List<String> generateNumberPlates() {
        List<String> numberPlates = new ArrayList<>();

        // Loop for first two digits (00-99)
        for (int firstTwoDigits = 0; firstTwoDigits <= 99; firstTwoDigits++) {
            // Loop for the next four digits (0000-9999)
            for (int fourDigits = 0; fourDigits <= 9999; fourDigits++) {
                // Loop for single alphabet (A-Z)
                for (char alphabet1 = 'A'; alphabet1 <= 'Z'; alphabet1++) {
                    // Add plates with single alphabet
//                    numberPlates.add(String.format("%02d BH %04d %c", firstTwoDigits, fourDigits, alphabet1));
                    System.out.println(String.format("%02d BH %04d %c", firstTwoDigits, fourDigits, alphabet1));
                    // Loop for second alphabet (A-Z)
                    for (char alphabet2 = 'A'; alphabet2 <= 'Z'; alphabet2++) {
                        // Add plates with two alphabets
//                        numberPlates.add(String.format("%02d BH %04d %c%c", firstTwoDigits, fourDigits, alphabet1, alphabet2));
                        System.out.println(String.format("%02d BH %04d %c%c", firstTwoDigits, fourDigits, alphabet1, alphabet2));
                    }
                }
            }
        }
        
        return numberPlates;
    }

    // Main method to run the program
    public static void main(String[] args) {
        List<String> plates = generateNumberPlates();
        System.out.println("Total possible number plates: " + plates.size());

        // Optional: Printing few samples
        for (int i = 0; i < 10; i++) {
            System.out.println(plates.get(i));
        }
    }
}


