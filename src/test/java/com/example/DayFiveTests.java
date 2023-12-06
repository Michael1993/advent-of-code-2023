package com.example;

import com.example.DayFive.Range;
import com.example.DayFive.SeedRange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class DayFiveTests {

    @Test
    void outOfBounds() {
        long s1 = 576061773;
        long l1 = 314005237;
        long s2 = 890067010;
        long l2 = 231802530;
        long sr1 = 762696372;
        long sr2 = 160455077;

        System.out.println(s1 + l1);
        System.out.println(s2 + l2);
        System.out.println(sr1 + sr2);
    }

    @Test
    void complex() {
        // given
        String seeds = """
                79 14
                55 13""";

        // when
        OptionalLong solution = DayFive.solveComplex(seeds, List.of(
                seedToSoil(),
                soilToFertilizer(),
                fertilizerToWater(),
                waterToLight(),
                lightToTemperature(),
                temperatureToHumidity(),
                humidityToLocation()));

        // then
        assertThat(solution).isPresent().hasValue(46);
    }

    @Test
    void complexRangeSearch() {
        // given
        SeedRange seedRange = new SeedRange(34, 12);
        Range range = new Range(33,12, 100);

        // when
        Optional<Range> overlapping = DayFive.overlapping(seedRange, List.of(range));

        // then
        assertThat(overlapping).isPresent();
    }

    @Test
    void complexConvert() {
        // given
        SeedRange seedRange = new SeedRange(34, 12);
        Range range = new Range(5,12, 100);

        // when
        List<SeedRange> solution = seedRange.convert(List.of(range));

        // then
        assertThat(solution).hasSize(1).hasSameElementsAs(List.of(new SeedRange(27, 12)));
    }

    @Test
    void complexConvertBreaking() {
        // given
        SeedRange seedRange = new SeedRange(17, 12);
        Range range1 = new Range(5,12, 10); // 12 to 22 so 17 to 22
        Range range2 = new Range(122, 22, 10);

        // when
        List<SeedRange> solution = seedRange.convert(List.of(range1, range2));

        // then
        assertThat(solution).hasSize(2)
                .hasSameElementsAs(List.of(
                        new SeedRange(10, 5),
                        new SeedRange(122, 7)));
    }

    @Test
    void complexConvertBreakingOtherWay() {
        // given
        SeedRange seedRange = new SeedRange(12, 12);
        Range range = new Range(122,17, 10);

        // when
        List<SeedRange> solution = seedRange.convert(List.of(range));

        // then
        assertThat(solution).hasSize(2)
                .hasSameElementsAs(List.of(
                        new SeedRange(12, 5),
                        new SeedRange(122, 7)));
    }

    @Test
    void complexMore() {
        // given
        SeedRange seedRange = new SeedRange(21, 100);
        Range range = new Range(122,17, 10); // 17 to 27 so 21 to 27 is mapped (6)

        // when
        List<SeedRange> solution = seedRange.convert(List.of(range));

        // then
        assertThat(solution).hasSize(2)
                .hasSameElementsAs(List.of(
                        new SeedRange(126, 6),
                        new SeedRange(27, 94)));
    }

    @Test
    void complexLess() {
        // given
        SeedRange seedRange = new SeedRange(12, 1);
        Range range = new Range(122,17, 10);

        // when
        List<SeedRange> solution = seedRange.convert(List.of(range));

        // then
        assertThat(solution).hasSize(1)
                .hasSameElementsAs(List.of(
                        new SeedRange(12, 1)));
    }

    @Test
    void complexLessAndMore() {
        // given
        SeedRange seedRange = new SeedRange(12, 100);
        Range range = new Range(122,17, 10);

        // when
        List<SeedRange> solution = seedRange.convert(List.of(range));

        // then
        assertThat(solution).hasSize(3)
                .hasSameElementsAs(List.of(
                        new SeedRange(12, 5),
                        new SeedRange(122, 10),
                        new SeedRange(27, 85)));
    }

    @Test
    void basic() {
        // given
        String seeds = "79 14 55 13";

        // when
        OptionalLong solution = DayFive.solve(seeds, List.of(
                seedToSoil(),
                soilToFertilizer(),
                fertilizerToWater(),
                waterToLight(),
                lightToTemperature(),
                temperatureToHumidity(),
                humidityToLocation()
        ));

        // then
        assertThat(solution).isPresent().hasValue(35);
    }

    @Test
    void rangeSearch() {
        // given
        var seed = 77L;
        var ranges = DayFive.mapToRanges(testInput());

        // when
        boolean result = ranges.stream()
                .anyMatch(range -> range.isInRange(seed));

        // then
        assertThat(result).isTrue();
    }

    @Test
    void mapping() {
        // given
        var seed = 77L;
        var ranges = DayFive.mapToRanges(testInput());

        // when
        Optional<Long> inRange = ranges.stream()
                .filter(range -> range.isInRange(seed))
                .findAny().map(range -> range.getInRange(seed));


        // then
        assertThat(inRange).isPresent().hasValue(45L);
    }

    @ParameterizedTest
    @ValueSource(strings = {"100", "12", "44"})
    void notInRange(String input) {
        // given
        var seed = Long.parseLong(input);
        var ranges = DayFive.mapToRanges(testInput());

        // when
        Optional<?> inRange = ranges.stream()
                .filter(range -> range.isInRange(seed))
                .findAny();


        // then
        assertThat(inRange).isEmpty();
    }

    @Test
    void middle() {
        // given
        var seed = 82L;
        var ranges = DayFive.mapToRanges(testInput());

        // when
        Optional<Long> inRange = ranges.stream()
                .filter(range -> range.isInRange(seed))
                .findAny().map(range -> range.getInRange(seed));


        // then
        assertThat(inRange).isPresent().hasValue(50L);
    }

    String testInput() {
        return """
                45 77 23
                81 45 19
                68 64 13""";
    }

    String seedToSoil() {
        return """
                50 98 2
                52 50 48""";
    }

    String soilToFertilizer() {
        return """
                0 15 37
                37 52 2
                39 0 15""";
    }

    String fertilizerToWater() {
        return """
                49 53 8
                0 11 42
                42 0 7
                57 7 4""";
    }

    String waterToLight() {
        return """
                88 18 7
                18 25 70""";
    }

    String lightToTemperature() {
        return """
                45 77 23
                81 45 19
                68 64 13""";
    }

    String temperatureToHumidity() {
        return """
                0 69 1
                1 0 69""";
    }

    String humidityToLocation() {
        return """
                60 56 37
                56 93 4""";
    }

}
