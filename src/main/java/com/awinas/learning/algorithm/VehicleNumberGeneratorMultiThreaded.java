package com.awinas.learning.algorithm;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VehicleNumberGeneratorMultiThreaded {

    // Worker class to generate number plates for a given range of first two digits
    static class NumberPlateWorker implements Runnable {
        private int startRange;
        private int endRange;
        private List<String> numberPlates;

        public NumberPlateWorker(int startRange, int endRange, List<String> numberPlates) {
            this.startRange = startRange;
            this.endRange = endRange;
            this.numberPlates = numberPlates;
        }

        @Override
        public void run() {
            // Loop for the given range of first two digits
            for (int firstTwoDigits = startRange; firstTwoDigits <= endRange; firstTwoDigits++) {
                // Loop for the next four digits (0000-9999)
                for (int fourDigits = 0; fourDigits <= 9999; fourDigits++) {
                    // Loop for single alphabet (A-Z)
                    for (char alphabet1 = 'A'; alphabet1 <= 'Z'; alphabet1++) {
                        // Add plates with single alphabet
                        synchronized (numberPlates) {
                            numberPlates.add(String.format("%02d BH %04d %c", firstTwoDigits, fourDigits, alphabet1));
                        }

                        // Loop for second alphabet (A-Z)
                        for (char alphabet2 = 'A'; alphabet2 <= 'Z'; alphabet2++) {
                            // Add plates with two alphabets
                            synchronized (numberPlates) {
                                numberPlates.add(String.format("%02d BH %04d %c%c", firstTwoDigits, fourDigits, alphabet1, alphabet2));
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // A list to store all generated number plates
        List<String> numberPlates = new ArrayList<>();

        // Define the number of threads
        int numberOfThreads = 10;

        // Create an ExecutorService to manage threads
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        // Divide the workload: each thread handles a portion of the first two digits (00-99)
        int rangePerThread = 100 / numberOfThreads;
        for (int i = 0; i < numberOfThreads; i++) {
            int startRange = i * rangePerThread;
            int endRange = (i + 1) * rangePerThread - 1;
            if (i == numberOfThreads - 1) {
                endRange = 99; // Ensure the last thread handles the remaining range
            }
            executor.execute(new NumberPlateWorker(startRange, endRange, numberPlates));
        }

        // Shut down the executor and wait for all threads to finish
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);  // Adjust timeout as needed

        System.out.println("Total possible number plates: " + numberPlates.size());

        // Optional: Printing few samples
        for (int i = 0; i < 10 && i < numberPlates.size(); i++) {
            System.out.println(numberPlates.get(i));
        }
    }
}
