package com.example;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DayFour {

    public static void main(String[] args) {
        System.out.println(DayFour.solve("puzzleInput()"));
        System.out.println(DayFour.solveComplex("puzzleInput()"));
    }

    public static int solve(String input) {
        return input.lines()
                .map(DayFour::lineToScratchcard)
                .mapToInt(Scratchcard::value)
                .sum();
    }

    public static int solveComplex(String input) {
        List<Scratchcard> list = input.lines()
                .map(DayFour::lineToScratchcard)
                .toList();
        return IntStream.range(0, list.size())
                .map(i -> summation(list.subList(i, list.size())))
                .sum();
    }

    private static int summation(List<Scratchcard> list) {
        if (list.isEmpty())
            return 0;
        int sum = 1;
        for (int i = 1; i < list.get(0).numberOfWinners() + 1 && i < list.size(); i++) {
            sum += summation(list.subList(i, list.size()));
        }
        return sum;
    }

    private static Scratchcard lineToScratchcard(String line) {
        var scratchcardValues = line.split(":")[1].split("\\|");
        if (scratchcardValues.length != 2)
            throw new IllegalStateException();
        return new Scratchcard(asList(scratchcardValues[0]), asList(scratchcardValues[1]));
    }

    private static List<Integer> asList(String scratchcardValue) {
        return Stream.of(scratchcardValue.trim().split(" +"))
                .map(Integer::parseInt)
                .toList();
    }

    record Scratchcard(List<Integer> winners, List<Integer> numbers) {
        int value() {
            long numberOfWinners = numbers.stream().filter(winners::contains).count();
            if (numberOfWinners == 0)
                return 0;
            return 1 << (numberOfWinners - 1);
        }

        long numberOfWinners() {
            return numbers.stream().filter(winners::contains).count();
        }
    }

}
