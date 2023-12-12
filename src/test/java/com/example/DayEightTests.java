package com.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayEightTests {
    @Test
    void simple() {
        // given
        String instructions = "RL";
        String desertMap = """
                AAA = (BBB, CCC)
                BBB = (DDD, EEE)
                CCC = (ZZZ, GGG)
                DDD = (DDD, DDD)
                EEE = (EEE, EEE)
                GGG = (GGG, GGG)
                ZZZ = (ZZZ, ZZZ)""";

        // when
        long solution = DayEight.solve(instructions, desertMap);

        // then
        assertThat(solution).isEqualTo(2);
    }

    @Test
    void repeated() {
        // given
        String instructions = "LLR";
        String desertMap = """
                AAA = (BBB, BBB)
                BBB = (AAA, ZZZ)
                ZZZ = (ZZZ, ZZZ)""";

        // when
        long solution = DayEight.solve(instructions, desertMap);

        // then
        assertThat(solution).isEqualTo(6);
    }

    @Test
    void partTwo() {
        // given
        String instructions = "LR";
        String map = """
                11A = (11B, XXX)
                11B = (XXX, 11Z)
                11Z = (11B, XXX)
                22A = (22B, XXX)
                22B = (22C, 22C)
                22C = (22Z, 22Z)
                22Z = (22B, 22B)
                XXX = (XXX, XXX)""";

        // when
        long solution = DayEightPartTwo.solve(instructions, map);

        // then
        assertThat(solution).isEqualTo(6);
    }
}
