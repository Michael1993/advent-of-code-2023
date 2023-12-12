package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DayNinePartTwo {
    public static long solve(String input) {
        return input.lines()
                .mapToLong(DayNinePartTwo::getNext)
                .sum();
    }

    public static long getNext(String input) {
        List<Long> numbers = Arrays.stream(input.split("\\s+")).map(Long::parseLong).toList();
        return getNext(numbers);
    }

    private static long getNext(List<Long> numbers) {
        if (numbers.stream().allMatch(n -> n == 0)) {
            return 0;
        }
        return numbers.getFirst() - getNext(differences(numbers));
    }

    private static List<Long> differences(List<Long> numbers) {
        List<Long> differences = new ArrayList<>();
        for (int i = 0; i < numbers.size() - 1; i++) {
            differences.add(numbers.get(i+1) - numbers.get(i));
        }
        return differences;
    }
}
