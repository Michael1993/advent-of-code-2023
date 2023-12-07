package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static java.util.Map.entry;

public class DayTwo {

    private static final Map<Color, Integer> COLORS = Map.of(
            Color.RED, 12,
            Color.GREEN, 13,
            Color.BLUE, 14
    );

    public static void main(String[] args) {
        System.out.println(DayTwo.solveComplex("input()"));
    }

    public static int solve(String input) {
        return input.lines()
                .map(DayTwo::gameAsListOfRounds)
                .filter(DayTwo::isPossibleGame)
                .mapToInt(Entry::getKey)
                .sum();
    }

    public static int solveComplex(String input) {
        return input.lines()
                .map(DayTwo::gameAsListOfRounds)
                .map(Entry::getValue)
                .map(DayTwo::colorMinimumPossible)
                .map(Map::values)
                .mapToInt(c -> c.stream().mapToInt(Integer::intValue).reduce(1, (a, b) -> a * b))
                .sum();
    }

    private static Entry<Integer, List<List<Entry<Color, Integer>>>> gameAsListOfRounds(String game) {
        String[] split = game.split(":");
        // yes, technically could be numbered in a for loop, which would be cleaner
        int id = Integer.parseInt(split[0].replaceAll("[^0-9]", ""));
        var listOfRounds = Arrays.stream(split[1].trim().split(";"))
                .map(DayTwo::roundAsListOfBatches)
                .toList();
        return entry(id, listOfRounds);
    }

    private static List<Entry<Color, Integer>> roundAsListOfBatches(String round) {
        return Arrays.stream(round.split(","))
                .map(DayTwo::batchAsEntry)
                .toList();
    }

    private static Entry<Color, Integer> batchAsEntry(String batch) {
        var color = Color.valueOf(batch.replaceAll("[^a-z]", "").toUpperCase());
        var amount = Integer.parseInt(batch.replaceAll("[^0-9]", ""));
        return entry(color, amount);
    }

    private static boolean isPossibleGame(Entry<Integer, List<List<Entry<Color, Integer>>>> game) {
        for (List<Entry<Color, Integer>> rounds : game.getValue()) {
            for (Entry<Color, Integer> batch : rounds) {
                int available = COLORS.get(batch.getKey());
                int drawn = batch.getValue();
                if (drawn > available)
                    return false;
            }
        }
        return true;
    }

    private static Map<Color, Integer> colorMinimumPossible(List<List<Map.Entry<Color, Integer>>> rounds) {
        var redMinimum = roundMaximum(rounds, Color.RED);
        var greenMinimum = roundMaximum(rounds, Color.GREEN);
        var blueMinimum = roundMaximum(rounds, Color.BLUE);
        return Map.of(
                Color.RED, redMinimum,
                Color.GREEN, greenMinimum,
                Color.BLUE, blueMinimum
        );
    }

    private static int roundMaximum(List<List<Map.Entry<Color, Integer>>> rounds, Color color) {
        return rounds.stream()
                .flatMap(round -> round.stream().filter(batch -> batch.getKey().equals(color)))
                .mapToInt(Entry::getValue)
                .max().orElse(0);
    }

    public enum Color {
        RED,
        BLUE,
        GREEN
    }

}
