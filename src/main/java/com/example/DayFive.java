package com.example;

import java.util.*;

public class DayFive {

    public static void main(String[] args) {
        String seeds = "seedRanges()";

        System.out.println(DayFive.solveComplex(seeds, List.of(
                "seedToSoil()",
                "soilToFertilizer()",
                "fertilizerToWater()",
                "waterToLight()",
                "lightToTemperature()",
                "temperatureToHumidity()",
                "humidityToLocation()")));
    }

    public static OptionalLong solve(String seeds, List<String> maps) {
        return Arrays.stream(seeds.split(" "))
                .mapToLong(Long::parseLong)
                .map(seed -> {
                    for (String map : maps) {
                        seed = convert(seed, map);
                    }
                    return seed;
                })
                .min();

    }

    public static OptionalLong solveComplex(String seedRanges, List<String> maps) {
        List<SeedRange> seedRangeList = mapToSeedRanges(seedRanges);
        for (String map : maps) {
            List<Range> ranges = mapToRanges(map);
            seedRangeList = seedRangeList.stream()
                    .flatMap(seedRange -> seedRange.convert(ranges).stream())
                    .toList();
        }
        return seedRangeList.stream().mapToLong(sr -> sr.start).min();
    }

    public static long convert(long number, String map) {
        List<Range> ranges = mapToRanges(map);
        if (ranges.stream().filter(range -> range.isInRange(number))
                .count() > 1) {
            throw new IllegalStateException("Expected exactly ONE range!");
        }
        OptionalLong fromMap = ranges.stream()
                .filter(range -> range.isInRange(number))
                .mapToLong(range -> range.getInRange(number))
                .findFirst();
        return fromMap.orElse(number);
    }

    public static List<SeedRange> mapToSeedRanges(String map) {
        return map.lines()
                .map(DayFive::lineToSeedRange)
                .toList();
    }

    private static SeedRange lineToSeedRange(String line) {
        var parts = line.split(" ");
        if (parts.length != 2)
            throw new IllegalStateException();
        return new SeedRange(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }

    public static List<Range> mapToRanges(String map) {
        return map.lines()
                .map(DayFive::lineToRange)
                .toList();
    }

    private static Range lineToRange(String line) {
        String[] parts = line.split(" ");
        if (parts.length != 3)
            throw new IllegalStateException();
        return new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1]), Long.parseLong(parts[2]));
    }

    public record Range(long destinationStart, long sourceStart, long length) {

        public boolean isInRange(long value) {
            return value >= sourceStart && value < sourceStart + length;
        }

        public long getInRange(long value) {
            return value - sourceStart + destinationStart;
        }

    }

    public record SeedRange(long start, long length) {

        public List<SeedRange> convert(List<Range> ranges) {
            List<SeedRange> result = new ArrayList<>();
            SeedRange current = this;
            for (var rangeOptional = overlapping(current, ranges); rangeOptional.isPresent(); rangeOptional = overlapping(current, ranges)) {
                var range = rangeOptional.get();
                if (current.start >= range.sourceStart) {
                    long available = range.sourceStart + range.length - current.start;
                    if (available > current.length) {
                        result.add(new SeedRange(current.start + range.destinationStart - range.sourceStart, current.length));
                        current = new SeedRange(0, 0);
                    } else {
                        result.add(new SeedRange(current.start + range.destinationStart - range.sourceStart, available));
                        current = new SeedRange(current.start + available, current.length - available);
                    }
                } else {
                    long l = range.sourceStart - current.start;
                    result.add(new SeedRange(current.start, l));
                    current = new SeedRange(range.sourceStart, current.length - l);
                }
            }
            if (current.length != 0) {
                result.add(current);
            }
            return result;
        }

    }


    public static Optional<Range> overlapping(SeedRange seedRange, List<Range> ranges) {
        if (seedRange.length == 0)
            return Optional.empty();
        return ranges.stream()
                .filter(range -> (range.sourceStart >= seedRange.start && range.sourceStart < seedRange.start + seedRange.length) ||
                        (seedRange.start >= range.sourceStart && seedRange.start < range.sourceStart + range.length))
                .min(Comparator.comparingLong(sr -> sr.sourceStart));
    }

}
