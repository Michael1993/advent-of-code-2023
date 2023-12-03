package com.example;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DayOneTests {

    @Test
    void basicInput() {
        // given
        var input = """
                1abc2
                pqr3stu8vwx
                a1b2c3d4e5f
                treb7uchet""";

        // when
        int result = DayOne.solve(input);

        // then
        Assertions.assertThat(result).isEqualTo(142);
    }

    @Test
    void complexInput() {
        // given
        var input = """
                two1nine
                eightwothree
                abcone2threexyz
                xtwone3four
                4nineeightseven2
                zoneight234
                7pqrstsixteen""";

        // when
        int result = DayOne.solve(input);

        // then
        Assertions.assertThat(result).isEqualTo(281);
    }

}
