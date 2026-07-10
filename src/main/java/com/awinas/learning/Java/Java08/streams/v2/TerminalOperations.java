package com.awinas.learning.Java.Java08.streams.v2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Stream Terminal Operations - triggers the pipeline, produces a result or side-effect.
 * Stream is consumed after a terminal operation and cannot be reused.
 * Each operation is separated into its own method for clarity.
 */
public class TerminalOperations {

    public static void main(String[] args) {
        forEachOperation();
        forEachOrderedOperation();
        collectToListSetMap();
        collectJoining();
        collectGroupingBy();
        collectPartitioningBy();
        collectSummarizing();
        reduceOperation();
        countOperation();
        minOperation();
        maxOperation();
        findFirstOperation();
        findAnyOperation();
        matchOperations();
        toArrayOperation();
    }

    // forEach(Consumer) - performs an action on each element, returns void
    private static void forEachOperation() {
        System.out.println("========== FOREACH ==========");

        StreamData.fruits.stream()
                .forEach(f -> System.out.println("  " + f));
        System.out.println();
    }

    // forEachOrdered(Consumer) - guarantees encounter order even in parallel streams
    private static void forEachOrderedOperation() {
        System.out.println("========== FOREACHORDERED ==========");

        // forEach in parallel - order NOT guaranteed
        System.out.print("forEach (parallel):        ");
        StreamData.numbers.stream().limit(5).parallel()
                .forEach(n -> System.out.print(n + " "));
        System.out.println();

        // forEachOrdered in parallel - order IS guaranteed
        System.out.print("forEachOrdered (parallel): ");
        StreamData.numbers.stream().limit(5).parallel()
                .forEachOrdered(n -> System.out.print(n + " "));
        System.out.println("\n");
    }

    // collect(Collectors.toList/toSet/toMap) - accumulates elements into a container
    private static void collectToListSetMap() {
        System.out.println("========== COLLECT - toList / toSet / toMap ==========");

        // toList - preserves order, allows duplicates
        List<String> list = StreamData.names.stream()
                .filter(n -> n.length() > 4)
                .collect(Collectors.toList());
        System.out.println("toList: " + list);

        // toSet - removes duplicates, no order guarantee
        Set<String> set = StreamData.names.stream()
                .collect(Collectors.toSet());
        System.out.println("toSet: " + set);

        // toMap - key mapper + value mapper
        Map<String, Integer> map = StreamData.fruits.stream()
                .distinct()
                .collect(Collectors.toMap(f -> f, String::length));
        System.out.println("toMap: " + map);
        System.out.println();
    }

    // collect(Collectors.joining) - concatenates elements into a single String
    private static void collectJoining() {
        System.out.println("========== COLLECT - joining ==========");

        // joining with delimiter only
        String joinedSimple = StreamData.names.stream()
                .distinct()
                .collect(Collectors.joining(", "));
        System.out.println("joining (delimiter): " + joinedSimple);

        // joining with delimiter + prefix + suffix
        String joinedFull = StreamData.names.stream()
                .filter(n -> n.startsWith("A"))
                .collect(Collectors.joining("::", "[", "]"));
        System.out.println("joining (delimiter + prefix + suffix): " + joinedFull);
        System.out.println();
    }

    // collect(Collectors.groupingBy) - groups elements by a classifier function
    private static void collectGroupingBy() {
        System.out.println("========== COLLECT - groupingBy ==========");

        // with Function anonymous class
        Function<String, Integer> byLength = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        };
        Map<Integer, List<String>> grouped1 = StreamData.names.stream()
                .collect(Collectors.groupingBy(byLength));
        System.out.println("Function impl: " + grouped1);

        // with method reference
        Map<Integer, List<String>> grouped2 = StreamData.names.stream()
                .collect(Collectors.groupingBy(String::length));
        System.out.println("Method ref: " + grouped2);

        // composite key (group by multiple values)
        Map<Object, List<String>> grouped3 = StreamData.names.stream()
                .collect(Collectors.groupingBy(name -> new ArrayList<>(Arrays.asList(name.length(), name.charAt(0)))));
        System.out.println("Composite key: " + grouped3);

        // with downstream collector toSet (removes duplicates in each group)
        Map<Integer, Set<String>> grouped4 = StreamData.names.stream()
                .collect(Collectors.groupingBy(String::length, Collectors.toSet()));
        System.out.println("Downstream toSet: " + grouped4);

        // with custom map factory + downstream
        Map<Integer, Set<String>> grouped5 = StreamData.names.stream()
                .collect(Collectors.groupingBy(String::length, HashMap::new, Collectors.toSet()));
        System.out.println("HashMap factory + toSet: " + grouped5);

        // with counting downstream
        Map<Integer, Long> grouped6 = StreamData.names.stream()
                .collect(Collectors.groupingBy(String::length, Collectors.counting()));
        System.out.println("Downstream counting: " + grouped6);
        System.out.println();
    }

    // collect(Collectors.partitioningBy) - splits into exactly two groups: true and false
    private static void collectPartitioningBy() {
        System.out.println("========== COLLECT - partitioningBy ==========");

        Map<Boolean, List<Integer>> partitioned = StreamData.numbers.stream()
                .collect(Collectors.partitioningBy(n -> n > 5));
        System.out.println("partitioningBy (>5): " + partitioned);
        System.out.println();
    }

    // collect(Collectors.summarizing) - produces statistics (count, sum, min, avg, max) in one pass
    private static void collectSummarizing() {
        System.out.println("========== COLLECT - summarizing ==========");

        DoubleSummaryStatistics stats = StreamData.prices.stream()
                .collect(Collectors.summarizingDouble(Double::doubleValue));
        System.out.println("summarizingDouble: " + stats);
        System.out.println();
    }

    // reduce() - combines all elements into a single result using accumulator
    private static void reduceOperation() {
        System.out.println("========== REDUCE ==========");

        // 1-arg: no identity, returns Optional
        Optional<String> concatenated = StreamData.names.stream()
                .reduce((a, b) -> a + "--" + b);
        concatenated.ifPresent(r -> System.out.println("1-arg (no identity): " + r));

        // 2-arg: with identity, returns T directly
        int sumWithIdentity = StreamData.numbers.stream()
                .reduce(0, Integer::sum);
        System.out.println("2-arg (identity=0, sum): " + sumWithIdentity);

        // 2-arg: identity value affects the result
        int sumStartingAt10 = StreamData.numbers.stream()
                .reduce(10, Integer::sum);
        System.out.println("2-arg (identity=10, sum): " + sumStartingAt10);

        // 2-arg: with BinaryOperator anonymous class
        BinaryOperator<String> accumulator = new BinaryOperator<String>() {
            @Override
            public String apply(String result, String element) {
                return result + " " + element;
            }
        };
        String reduced = StreamData.names.stream()
                .reduce("Names:", accumulator);
        System.out.println("2-arg (BinaryOperator impl): " + reduced);

        // 2-arg: string concatenation with lambda
        String concatLambda = StreamData.names.stream()
                .reduce("Result:", (concat, e) -> concat + " " + e);
        System.out.println("2-arg (String concat lambda): " + concatLambda);

        // 2-arg: product calculation
        int product = StreamData.numbers.stream()
                .limit(5)
                .reduce(1, (a, b) -> a * b);
        System.out.println("2-arg (product): " + product);

        // 3-arg: identity + BiFunction accumulator + BinaryOperator combiner
        // Used when result type differs from stream element type
        BiFunction<Integer, String, Integer> lengthAccumulator = new BiFunction<Integer, String, Integer>() {
            @Override
            public Integer apply(Integer total, String name) {
                return total + name.length();
            }
        };
        BinaryOperator<Integer> combiner = new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer a, Integer b) {
                return a + b;
            }
        };
        int totalLength = StreamData.names.stream()
                .reduce(0, lengthAccumulator, combiner);
        System.out.println("3-arg (BiFunction impl): " + totalLength);

        // 3-arg: with lambda
        int totalLengthLambda = StreamData.names.stream()
                .reduce(0, (total, name) -> total + name.length(), Integer::sum);
        System.out.println("3-arg (lambda): " + totalLengthLambda);

        // parallelStream reduce - combiner merges partial results from threads
        int parallelSum = StreamData.numbers.parallelStream()
                .reduce(0, Integer::sum);
        System.out.println("parallelStream reduce: " + parallelSum);
        System.out.println();
    }

    // count() - returns total number of elements as long
    private static void countOperation() {
        System.out.println("========== COUNT ==========");

        long totalCount = StreamData.names.stream().count();
        System.out.println("count (all): " + totalCount);

        long filteredCount = StreamData.names.stream()
                .filter(n -> n.startsWith("A"))
                .count();
        System.out.println("count (filtered): " + filteredCount);
        System.out.println();
    }

    // min(Comparator) - finds the smallest element, returns Optional
    private static void minOperation() {
        System.out.println("========== MIN ==========");

        Optional<Integer> min = StreamData.numbers.stream()
                .min((a, b) -> a.compareTo(b));
        System.out.println("min (Integer): " + min.orElse(0));

        Optional<String> minName = StreamData.names.stream()
                .min(String::compareTo);
        System.out.println("min (String): " + minName.orElse(""));
        System.out.println();
    }

    // max(Comparator) - finds the largest element, returns Optional
    private static void maxOperation() {
        System.out.println("========== MAX ==========");

        Optional<Integer> max = StreamData.numbers.stream()
                .max((a, b) -> a.compareTo(b));
        System.out.println("max (Integer): " + max.orElse(0));

        Optional<String> maxName = StreamData.names.stream()
                .max(String::compareTo);
        System.out.println("max (String): " + maxName.orElse(""));
        System.out.println();
    }

    // findFirst() - returns the first element as Optional, deterministic
    private static void findFirstOperation() {
        System.out.println("========== FINDFIRST ==========");

        Optional<String> first = StreamData.names.stream()
                .filter(n -> n.startsWith("R"))
                .findFirst();
        System.out.println("findFirst: " + first.orElse("not found"));

        // when no element matches
        Optional<String> noMatch = StreamData.names.stream()
                .filter(n -> n.startsWith("Z"))
                .findFirst();
        System.out.println("findFirst (no match): " + noMatch.orElse("not found"));
        System.out.println();
    }

    // findAny() - returns any element as Optional, non-deterministic in parallel
    private static void findAnyOperation() {
        System.out.println("========== FINDANY ==========");

        Optional<String> any = StreamData.names.parallelStream()
                .filter(n -> n.startsWith("K"))
                .findAny();
        System.out.println("findAny (parallel): " + any.orElse("not found"));
        System.out.println();
    }

    // anyMatch / allMatch / noneMatch - short-circuiting boolean checks
    private static void matchOperations() {
        System.out.println("========== ANYMATCH / ALLMATCH / NONEMATCH ==========");

        // anyMatch - true if at least one element matches
        boolean anyMatch = StreamData.numbers.stream().anyMatch(n -> n > 9);
        System.out.println("anyMatch (>9): " + anyMatch);

        // allMatch - true only if ALL elements match
        boolean allMatchTrue = StreamData.numbers.stream().allMatch(n -> n > 0);
        System.out.println("allMatch (>0): " + allMatchTrue);

        boolean allMatchFalse = StreamData.numbers.stream().allMatch(n -> n > 5);
        System.out.println("allMatch (>5): " + allMatchFalse);

        // noneMatch - true if NO elements match
        boolean noneMatch = StreamData.numbers.stream().noneMatch(n -> n > 100);
        System.out.println("noneMatch (>100): " + noneMatch);
        System.out.println();
    }

    // toArray() - converts stream into an array
    private static void toArrayOperation() {
        System.out.println("========== TOARRAY ==========");

        // returns Object[]
        Object[] array = StreamData.fruits.stream()
                .distinct()
                .toArray();
        System.out.println("toArray (Object[]): " + Arrays.toString(array));

        // with generator - returns typed array
        String[] typedArray = StreamData.fruits.stream()
                .distinct()
                .toArray(String[]::new);
        System.out.println("toArray (String[]): " + Arrays.toString(typedArray));
        System.out.println();
    }
}
