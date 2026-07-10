package com.awinas.learning.Java08.streams.v2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream Intermediate Operations - returns a new Stream, lazy execution.
 * Each operation is separated into its own method for clarity.
 */
public class IntermediateOperations {

    public static void main(String[] args) {
        filterOperation();
        mapOperation();
        flatMapOperation();
        distinctOperation();
        sortedOperation();
        peekOperation();
        limitOperation();
        skipOperation();
        mapToIntOperation();
        mapToDoubleOperation();
        mapToLongOperation();
        takeWhileOperation();
        dropWhileOperation();
    }

    // filter(Predicate<T>) - keeps only elements that satisfy the given condition
    private static void filterOperation() {
        System.out.println("========== FILTER ==========");

        // with anonymous Predicate implementation
        Predicate<String> startsWithA = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.startsWith("A");
            }
        };
        List<String> filteredImpl = StreamData.names.stream()
                .filter(startsWithA)
                .collect(Collectors.toList());
        System.out.println("Predicate impl: " + filteredImpl);

        // with lambda
        List<String> filteredLambda = StreamData.names.stream()
                .filter(name -> name.startsWith("A"))
                .collect(Collectors.toList());
        System.out.println("Lambda: " + filteredLambda);

        // negate() - reverses the predicate condition
        List<String> filteredNegate = StreamData.names.stream()
                .filter(startsWithA.negate())
                .collect(Collectors.toList());
        System.out.println("Negate: " + filteredNegate);

        // filter + distinct - removes duplicates after filtering
        List<String> filteredDistinct = StreamData.names.stream()
                .filter(name -> name.startsWith("A"))
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Filter + Distinct: " + filteredDistinct);
        System.out.println();
    }

    // map(Function<T, R>) - transforms each element from type T to type R
    private static void mapOperation() {
        System.out.println("========== MAP ==========");

        // with anonymous Function implementation
        Function<String, String> toUpperMapper = new Function<String, String>() {
            @Override
            public String apply(String s) {
                return s.toUpperCase();
            }
        };
        List<String> mappedImpl = StreamData.names.stream()
                .map(toUpperMapper)
                .collect(Collectors.toList());
        System.out.println("Function impl: " + mappedImpl);

        // with method reference
        List<String> mappedRef = StreamData.names.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("Method ref: " + mappedRef);

        // null-safe map + split each string into characters
        StreamData.names.stream()
                .filter(name -> name != null)
                .map(name -> Arrays.asList(name.split("")))
                .forEach(charList -> System.out.println("  Split to chars: " + charList));

        // type transformation - String to Integer
        List<Integer> lengths = StreamData.names.stream()
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println("String -> Integer (length): " + lengths);
        System.out.println();
    }

    // flatMap(Function<T, Stream<R>>) - flattens nested structures into a single stream
    private static void flatMapOperation() {
        System.out.println("========== FLATMAP ==========");

        // with anonymous Function implementation
        Function<List<Integer>, Stream<Integer>> flatMapper = new Function<List<Integer>, Stream<Integer>>() {
            @Override
            public Stream<Integer> apply(List<Integer> list) {
                return list.stream();
            }
        };
        List<Integer> flatMappedImpl = StreamData.nestedNumbers.stream()
                .flatMap(flatMapper)
                .collect(Collectors.toList());
        System.out.println("Function impl: " + flatMappedImpl);

        // with method reference
        List<Integer> flatMappedRef = StreamData.nestedNumbers.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        System.out.println("Method ref: " + flatMappedRef);

        // flatMap + filter - flatten then filter even numbers
        List<Integer> flatFiltered = StreamData.nestedNumbers.stream()
                .flatMap(List::stream)
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("FlatMap + filter even: " + flatFiltered);

        // split sentences into individual words
        List<String> words = StreamData.sentences.stream()
                .flatMap(sentence -> Stream.of(sentence.split(" ")))
                .collect(Collectors.toList());
        System.out.println("Split sentences to words: " + words);
        System.out.println();
    }

    // distinct() - removes duplicate elements using equals() and hashCode()
    private static void distinctOperation() {
        System.out.println("========== DISTINCT ==========");

        List<Integer> distinctNumbers = StreamData.duplicates.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Distinct numbers: " + distinctNumbers);

        List<String> distinctNames = StreamData.names.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Distinct names: " + distinctNames);
        System.out.println();
    }

    // sorted() - natural order | sorted(Comparator) - custom logic
    private static void sortedOperation() {
        System.out.println("========== SORTED ==========");

        // ascending (natural order)
        List<String> sortedAsc = StreamData.names.stream()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("Ascending: " + sortedAsc);

        // descending with anonymous Comparator
        Comparator<String> descComparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return -(o1.compareTo(o2));
            }
        };
        List<String> sortedDescImpl = StreamData.names.stream()
                .sorted(descComparator)
                .collect(Collectors.toList());
        System.out.println("Descending (Comparator impl): " + sortedDescImpl);

        // descending with lambda
        List<String> sortedDescLambda = StreamData.names.stream()
                .sorted((o1, o2) -> -(o1.compareTo(o2)))
                .collect(Collectors.toList());
        System.out.println("Descending (lambda): " + sortedDescLambda);

        // sorted by property (string length)
        List<String> sortedByLength = StreamData.names.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
        System.out.println("By length: " + sortedByLength);
        System.out.println();
    }

    // peek(Consumer<T>) - performs side-effect action without modifying the stream (for debugging)
    private static void peekOperation() {
        System.out.println("========== PEEK ==========");

        // with anonymous Consumer implementation
        Consumer<String> peekAction = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("  peeking: " + s);
            }
        };
        String joined = StreamData.fruits.stream()
                .peek(peekAction)
                .collect(Collectors.joining(", "));
        System.out.println("Consumer impl result: " + joined);

        // debugging pipeline - peek before and after filter
        List<String> peekedFiltered = StreamData.fruits.stream()
                .peek(f -> System.out.println("  before filter: " + f))
                .filter(f -> f.length() > 5)
                .peek(f -> System.out.println("  after filter: " + f))
                .collect(Collectors.toList());
        System.out.println("Peek + filter result: " + peekedFiltered);
        System.out.println();
    }

    // limit(long n) - truncates stream to at most first N elements
    private static void limitOperation() {
        System.out.println("========== LIMIT ==========");

        List<Integer> limited = StreamData.numbers.stream()
                .limit(5)
                .collect(Collectors.toList());
        System.out.println("limit(5): " + limited);
        System.out.println();
    }

    // skip(long n) - discards first N elements, returns the rest
    private static void skipOperation() {
        System.out.println("========== SKIP ==========");

        List<Integer> skipped = StreamData.numbers.stream()
                .skip(10)
                .collect(Collectors.toList());
        System.out.println("skip(10): " + skipped);

        // skip + limit = pagination pattern (page 2, size 4)
        List<Integer> page = StreamData.numbers.stream()
                .skip(3)
                .limit(4)
                .collect(Collectors.toList());
        System.out.println("skip(3) + limit(4) [pagination]: " + page);
        System.out.println();
    }

    // mapToInt(ToIntFunction) - converts to IntStream (primitive), avoids autoboxing
    private static void mapToIntOperation() {
        System.out.println("========== MAPTOINT ==========");

        // sum
        int sum = StreamData.numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println("sum: " + sum);

        // min
        OptionalInt minVal = StreamData.numbers.stream()
                .mapToInt(Integer::intValue)
                .min();
        System.out.println("min: " + minVal.orElse(0));

        // max
        OptionalInt maxVal = StreamData.numbers.stream()
                .mapToInt(Integer::intValue)
                .max();
        System.out.println("max: " + maxVal.orElse(0));

        // average
        OptionalDouble average = StreamData.numbers.stream()
                .mapToInt(Integer::intValue)
                .average();
        System.out.println("average: " + average.orElse(0.0));

        // summaryStatistics - count, sum, min, avg, max in one pass
        IntSummaryStatistics stats = StreamData.numbers.stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics();
        System.out.println("summaryStatistics: " + stats);
        System.out.println();
    }

    // mapToDouble(ToDoubleFunction) - converts to DoubleStream for decimal precision
    private static void mapToDoubleOperation() {
        System.out.println("========== MAPTODOUBLE ==========");

        double avg = StreamData.prices.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
        System.out.println("average: " + avg);
        System.out.println();
    }

    // mapToLong(ToLongFunction) - converts to LongStream for large numbers
    private static void mapToLongOperation() {
        System.out.println("========== MAPTOLONG ==========");

        long longSum = StreamData.numbers.stream()
                .mapToLong(Integer::longValue)
                .sum();
        System.out.println("sum: " + longSum);
        System.out.println();
    }

    // takeWhile(Predicate) [Java 9+] - takes elements while true, stops at first false
    private static void takeWhileOperation() {
        System.out.println("========== TAKEWHILE (Java 9+) ==========");

        List<Integer> taken = StreamData.numbers.stream()
                .takeWhile(n -> n < 8)
                .collect(Collectors.toList());
        System.out.println("takeWhile (< 8): " + taken);
        System.out.println();
    }

    // dropWhile(Predicate) [Java 9+] - drops elements while true, keeps everything after first false
    private static void dropWhileOperation() {
        System.out.println("========== DROPWHILE (Java 9+) ==========");

        List<Integer> dropped = StreamData.numbers.stream()
                .dropWhile(n -> n < 8)
                .collect(Collectors.toList());
        System.out.println("dropWhile (< 8): " + dropped);
        System.out.println();
    }
}
