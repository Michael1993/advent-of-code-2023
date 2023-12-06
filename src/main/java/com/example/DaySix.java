package com.example;

import java.util.List;
import java.util.stream.IntStream;

public class DaySix {

    public static void main(String[] args) {
        String input = input();

        System.out.println(DaySix.solve(input));
        System.out.println(DaySix.solveComplex(input));
    }

    public static long solveComplex(String input) {
        Race race = parseAsSingleRace(input);

        return race.timesToWin();
    }

    public static long solve(String input) {
        List<Race> races = parseRaces(input);
        return races.stream()
                .mapToLong(Race::timesToWin)
                .reduce(1, Math::multiplyExact);
    }

    public static List<Race> parseRaces(String input) {
        List<String> list = input.lines().toList();
        String[] times = list.get(0).substring("Time:".length()).trim().split("\\s+");
        String[] distances = list.get(1).substring("Distance:".length()).trim().split("\\s+");
        return IntStream.range(0, times.length)
                .mapToObj(i -> new Race(Long.parseLong(times[i]), Long.parseLong(distances[i])))
                .toList();
    }

    public static Race parseAsSingleRace(String input) {
        List<String> list = input.lines().toList();
        String time = list.get(0).substring("Time:".length()).replaceAll("\\s+", "");
        String distance = list.get(1).substring("Distance:".length()).replaceAll("\\s+", "");
        return new Race(Long.parseLong(time), Long.parseLong(distance));
    }

    public record Race(long duration, long distance) {
        public long minimumTimeToSurpassDistance() {
            return Math.round(Math.floor((duration - Math.sqrt((duration * duration - 4 * distance))) / 2)) + 1;
        }

        public long maximumTimeToSurpassDistance() {
            return Math.round(Math.ceil((duration + Math.sqrt((duration * duration - 4 * distance))) / 2)) - 1;
        }

        public long timesToWin() {
            return maximumTimeToSurpassDistance() - minimumTimeToSurpassDistance() + 1;
        }
    }

    static String input() {
        return """
                Time:        62     73     75     65
                Distance:   644   1023   1240   1023""";
    }
}
