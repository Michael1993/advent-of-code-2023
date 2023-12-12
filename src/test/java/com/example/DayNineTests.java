package com.example;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DayNineTests {

    @Nested
    class PartOne {
        @Test
        void testAll() {
            // given
            String input = """
                0 3 6 9 12 15
                1 3 6 10 15 21
                10 13 16 21 30 45""";

            // when
            long solution = DayNine.solve(input);

            // then
            assertThat(solution).isEqualTo(114);
        }

        @Test
        void testFirst() {
            // given
            String input = "0 3 6 9 12 15";

            // when
            long prediction = DayNine.getNext(input);

            // then
            assertThat(prediction).isEqualTo(18);
        }

        @Test
        void testSecond() {
            // given
            String input = "1 3 6 10 15 21";

            // when
            long prediction = DayNine.getNext(input);

            // then
            assertThat(prediction).isEqualTo(28);
        }

        @Test
        void testThird() {
            // given
            String input = "10 13 16 21 30 45";

            // when
            long prediction = DayNine.getNext(input);

            // then
            assertThat(prediction).isEqualTo(68);
        }
    }

    @Nested
    class PartTwo {
        @Test
        void testAll() {
            // given
            String input = """
                0 3 6 9 12 15
                1 3 6 10 15 21
                10 13 16 21 30 45""";

            // when
            long solution = DayNinePartTwo.solve(input);

            // then
            assertThat(solution).isEqualTo(2);
        }

        @Test
        void testFirst() {
            // given
            String input = "0 3 6 9 12 15";

            // when
            long prediction = DayNinePartTwo.getNext(input);

            // then
            assertThat(prediction).isEqualTo(-3);
        }

        @Test
        void testSecond() {
            // given
            String input = "1 3 6 10 15 21";

            // when
            long prediction = DayNinePartTwo.getNext(input);

            // then
            assertThat(prediction).isEqualTo(0);
        }

        @Test
        void testThird() {
            // given
            String input = "10 13 16 21 30 45";

            // when
            long prediction = DayNinePartTwo.getNext(input);

            // then
            assertThat(prediction).isEqualTo(5);
        }
    }


}
