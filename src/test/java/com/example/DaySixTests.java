package com.example;

import com.example.DaySix.Race;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DaySixTests {

    @Test
    void basic() {
        // given
        String input = """
                Time:      7  15   30
                Distance:  9  40  200""";

        // when
        long solution = DaySix.solve(input);

        // then
        assertThat(solution).isEqualTo(288);
    }

    @Test
    void complex() {
        // given
        String input = """
                Time:      7  15   30
                Distance:  9  40  200""";

        // when
        long solution = DaySix.solveComplex(input);

        // then
        assertThat(solution).isEqualTo(71503);
    }

    @Test
    void complexMin() {
        // given
        String input = """
                Time:      7  15   30
                Distance:  9  40  200""";
        Race race = DaySix.parseAsSingleRace(input);

        // when
        long solution = race.minimumTimeToSurpassDistance();

        // then
        assertThat(solution).isEqualTo(14);
    }

    @Test
    void complexMax() {
        // given
        String input = """
                Time:      7  15   30
                Distance:  9  40  200""";
        Race race = DaySix.parseAsSingleRace(input);

        // when
        long solution = race.maximumTimeToSurpassDistance();

        // then
        assertThat(solution).isEqualTo(71516);
    }

    @Test
    void parseInput() {
        // given
        String input = """
                Time:      7  15   30
                Distance:  9  40  200""";

        // when
        List<Race> solution = DaySix.parseRaces(input);

        // then
        assertThat(solution)
                .hasSize(3)
                .hasSameElementsAs(List.of(
                        new Race(7, 9),
                        new Race(15, 40),
                        new Race(30, 200)
                ));
    }

    @Test
    void getMinimumTimeToSurpassDistance() {
        // given
        String input = """
                Time:      7  15   30
                Distance:  9  40  200""";
        List<Race> races = DaySix.parseRaces(input);

        // when
        List<Long> solution = races.stream()
                .map(Race::minimumTimeToSurpassDistance)
                .toList();

        // then
        assertThat(solution).hasSize(3).containsExactly(2L, 4L, 11L);
    }

    @Test
    void getMaximumTimeToSurpassDistance() {
        // given
        String input = """
                Time:      7  15   30
                Distance:  9  40  200""";
        List<Race> races = DaySix.parseRaces(input);

        // when
        List<Long> solution = races.stream()
                .map(Race::maximumTimeToSurpassDistance)
                .toList();

        // then
        assertThat(solution).hasSize(3).containsExactly(5L, 11L, 19L);
    }

    @Test
    void parseDifferent() {
        // given
        String input = """
                Time:      7  15   30
                Distance:  9  40  200""";

        // when
        Race solution = DaySix.parseAsSingleRace(input);

        // then
        assertThat(solution).isEqualTo(new Race(71530, 940200));
    }
}
