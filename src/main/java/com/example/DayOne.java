package com.example;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.Map.entry;

public class DayOne {

    private static final Map<String, Integer> LITERALS = Map.ofEntries(
            entry("one", 1),
            entry("two", 2),
            entry("three", 3),
            entry("four", 4),
            entry("five", 5),
            entry("six", 6),
            entry("seven", 7),
            entry("eight", 8),
            entry("nine", 9),
            entry("zero", 0),
            entry("1", 1),
            entry("2", 2),
            entry("3", 3),
            entry("4", 4),
            entry("5", 5),
            entry("6", 6),
            entry("7", 7),
            entry("8", 8),
            entry("9", 9),
            entry("0", 0)
    );

    public static void main(String[] args) {
        var in = "onetwo12345";
        System.out.println(in.indexOf("two"));
        var input = "complexInput()";
        System.out.println(DayOne.solve(input));
    }

    public static int solve(String input) {
        return input.lines()
                .map(line -> new FirstAndLastNumber(findFirstNumber(line), findLastNumber(line)))
                .mapToInt(number -> number.first * 10 + number.last)
                .sum();
    }

    private static int findFirstNumber(String string) {
        int min = -1;
        int value = -1;
        for (Map.Entry<String, Integer> literal : LITERALS.entrySet()) {
            if (string.contains(literal.getKey())) {
                if (min == -1 || string.indexOf(literal.getKey()) < min) {
                    min = string.indexOf(literal.getKey());
                    value = literal.getValue();
                }
            }
        }
        if (value == -1) {
            throw new IllegalStateException();
        }
        return value;
    }

    private static int findLastNumber(String string) {
        int max = -1;
        int value = -1;
        for (Map.Entry<String, Integer> literal : LITERALS.entrySet()) {
            if (string.contains(literal.getKey())) {
                if (max == -1 || string.lastIndexOf(literal.getKey()) > max) {
                    max = string.lastIndexOf(literal.getKey());
                    value = literal.getValue();
                }
            }
        }
        if (value == -1) {
            throw new IllegalStateException();
        }
        return value;
    }

    record FirstAndLastNumber(int first, int last) {}
}
