package com.example;

import com.example.DaySeven.Card;
import com.example.DaySeven.HandValue;
import com.example.DaySeven.PokerHand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class DaySevenTests {

    @Test
    void basic() {
        // given
        String input = """
                32T3K 765
                T55J5 684
                KK677 28
                KTJJT 220
                QQQJA 483""";

        // when
        long solution = DaySeven.solve(input);

        // then
        assertThat(solution).isEqualTo(6440);
    }

    @Test
    void basicWithSameCards() {
        // given
        String input = """
                234TT 100
                KKTTJ 100
                KKTTJ 100
                QKJTT 100
                KKKKK 100""";

        // when
        long solution = DaySeven.solve(input);

        // then
        assertThat(solution).isEqualTo(100 + 200 + 300 + 300 + 400);
    }

    @ParameterizedTest
    @MethodSource("someCards")
    void parseCardTest(String in, Card card) {
        // given parameters

        // when
        Card value = Card.parse(in);

        // then
        assertThat(value).isEqualTo(card);
    }

    @Test
    void handParseTest() {
        // given
        String in = "AJJTT 1211";

        // when
        PokerHand pokerHand = DaySeven.parseLineAsPokerHand(in);

        // then
        assertThat(pokerHand.bid()).isEqualTo(1211);
        assertThat(pokerHand.hand().value()).isEqualTo(HandValue.TWO_PAIRS);
        assertThat(pokerHand.hand().cards()).hasSize(5).containsExactly(
                Card.ACE,
                Card.JACK,
                Card.JACK,
                Card.TEN,
                Card.TEN
        );
    }

    public static Stream<Arguments> someCards() {
        return Stream.of(
                Arguments.of("A", Card.ACE),
                Arguments.of("T", Card.TEN),
                Arguments.of("3", Card.THREE)

        );
    }

    @Test
    void checkValueCalculation() {
        String input = """
                32T3K 765
                T55J5 684
                KK677 28
                KTJJT 220
                QQQJA 483""";

        List<DaySevenPartTwo.PokerHand> list = input.lines().map(DaySevenPartTwo::parseLineAsPokerHand).toList();

        assertThat(list).hasSize(5)
                .satisfies(pokerHands -> {
                    assertThat(pokerHands.get(0).hand().value()).isEqualTo(HandValue.ONE_PAIR);
                    assertThat(pokerHands.get(1).hand().value()).isEqualTo(HandValue.FOUR_OF_A_KIND);
                    assertThat(pokerHands.get(2).hand().value()).isEqualTo(HandValue.TWO_PAIRS);
                    assertThat(pokerHands.get(3).hand().value()).isEqualTo(HandValue.FOUR_OF_A_KIND);
                    assertThat(pokerHands.get(4).hand().value()).isEqualTo(HandValue.FOUR_OF_A_KIND);
                });
    }

    @Test
    void checkSolving() {
        String input = """
                32T3K 765
                T55J5 684
                KK677 28
                KTJJT 220
                QQQJA 483""";

        long solution = DaySevenPartTwo.solve(input);

        assertThat(solution).isEqualTo(5905);
    }

}
