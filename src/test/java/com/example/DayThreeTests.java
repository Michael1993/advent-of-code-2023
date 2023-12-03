package com.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayThreeTests {

    @Test
    void basic() {
        // given
        String input = DayThree.demoInput();

        // when
        int solution = DayThree.solve(input);

        // then
        assertThat(solution).isEqualTo(4361);
    }

    @Test
    void complex() {
        // given
        String input = DayThree.demoInput();

        // when
        int solution = DayThree.solveComplex(input);

        // then
        assertThat(solution).isEqualTo(467835);
    }

}
