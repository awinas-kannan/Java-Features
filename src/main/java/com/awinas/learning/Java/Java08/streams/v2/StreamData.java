package com.awinas.learning.Java.Java08.streams.v2;

import java.util.Arrays;
import java.util.List;

public class StreamData {

    public static final List<String> names = Arrays.asList(
            "Awinas", "Kannan", "Rahul", "Sneha", "Amit",
            "Priya", "Rahul", "Awinas", "Deepa", "Kannan"
    );

    public static final List<Integer> numbers = Arrays.asList(
            5, 3, 8, 1, 9, 2, 7, 4, 6, 10, 3, 8, 1, 5
    );

    public static final List<String> fruits = Arrays.asList(
            "Apple", "Banana", "Cherry", "Apple", "Date",
            "Elderberry", "Fig", "Banana", "Grape", "Cherry"
    );

    public static final List<Double> prices = Arrays.asList(
            99.99, 45.50, 120.00, 33.75, 89.90,
            150.25, 60.00, 25.10, 200.00, 75.50
    );

    public static final List<String> sentences = Arrays.asList(
            "Java is awesome",
            "Streams are powerful",
            "Lambda makes code concise",
            "Functional programming rocks",
            "Collections framework is rich"
    );

    public static final List<List<Integer>> nestedNumbers = Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(4, 5, 6),
            Arrays.asList(7, 8, 9)
    );

    public static final List<Integer> duplicates = Arrays.asList(
            1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10
    );
}
