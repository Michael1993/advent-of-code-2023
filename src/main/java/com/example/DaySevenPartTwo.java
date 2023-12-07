package com.example;

import com.example.DaySeven.HandValue;

import java.util.*;
import java.util.stream.Collectors;

public class DaySevenPartTwo {

    public static void main(String[] args) {
        String input = "input()";
        System.out.println(solve(input));
    }

    public static long solve(String input) {
        long sum = 0;
        int rank = 1;
        List<PokerHand> list = input.lines()
                .map(DaySevenPartTwo::parseLineAsPokerHand)
                .sorted(Comparator.comparing(PokerHand::hand))
                .peek(System.out::println)
                .toList();
        for (PokerHand pokerHand : list) {
            sum += pokerHand.bid() * rank;
            rank++;
        }
        return sum;
    }

    public static PokerHand parseLineAsPokerHand(String line) {
        String[] split = line.trim().split(" ");
        if (split.length != 2)
            throw new IllegalStateException("Incorrect poker hand format: " + line);
        var cards = Arrays.stream(split[0].split("")).map(Card::parse).toList();
        return new PokerHand(new Hand(cards), Long.parseLong(split[1]));
    }

    public record PokerHand(Hand hand, long bid) {}

    public static class Hand implements Comparable<Hand> {
        private final List<Card> cards;
        private final HandValue value;

        public Hand(List<Card> cards) {
            this.cards = cards;
            this.value = calculateValue();
        }

        public HandValue value() {
            return value;
        }

        public HandValue calculateValue() {
            Map<Integer, List<Card>> map = cards.stream().collect(Collectors.groupingBy(Enum::ordinal));
            var jokers = map.entrySet().stream()
                    .filter(entry -> entry.getValue().contains(Card.JOKER))
                    .findFirst();
            if (jokers.isPresent()) {
                if (map.size() == 1)
                    return HandValue.FIVE_OF_A_KIND; // early return for 5 jokers
                var jokerList = map.remove(jokers.get().getKey());
                map.values().stream()
                        .max(Comparator.comparingInt(List::size))
                        .ifPresent(l -> l.addAll(jokerList));
            }
            if (map.size() == 1)
                return HandValue.FIVE_OF_A_KIND;
            else if (map.size() == 5)
                return HandValue.HIGH_CARD;
            else if (map.size() == 4)
                return HandValue.ONE_PAIR;
            else if (map.size() == 2) {
                if (map.values().stream().anyMatch(c -> c.size() == 4))
                    return HandValue.FOUR_OF_A_KIND;
                else
                    return HandValue.FULL_HOUSE;
            }
            if (map.values().stream().anyMatch(c -> c.size() == 3))
                return HandValue.THREE_OF_A_KIND;
            return HandValue.TWO_PAIRS;
        }

        @Override
        public int compareTo(Hand o) {
            int handValueComparison = Integer.compare(this.value.ordinal(), o.value.ordinal());
            if (handValueComparison != 0)
                return handValueComparison;
            return compareCards(this.cards, o.cards);
        }

        private int compareCards(List<Card> cards, List<Card> other) {
            for (int i = 0; i < 5; i++) {
                if (cards.get(i).ordinal() < other.get(i).ordinal())
                    return 1;
                if (cards.get(i).ordinal() > other.get(i).ordinal())
                    return -1;
            }
            return 0;
        }

        @Override
        public String toString() {
            return cards.toString() + " -> " + value.toString();
        }
    }

    public enum Card {
        ACE,
        KING,
        QUEEN,
        TEN,
        NINE,
        EIGHT,
        SEVEN,
        SIX,
        FIVE,
        FOUR,
        THREE,
        TWO,
        JOKER;

        public static Card parse(String string) {
            return switch (string) {
                case "A" -> ACE;
                case "K" -> KING;
                case "Q" -> QUEEN;
                case "T" -> TEN;
                case "9" -> NINE;
                case "8" -> EIGHT;
                case "7" -> SEVEN;
                case "6" -> SIX;
                case "5" -> FIVE;
                case "4" -> FOUR;
                case "3" -> THREE;
                case "2" -> TWO;
                case "J" -> JOKER;
                default -> throw new IllegalStateException("Illegal input, this card does not exist: " + string);
            };
        }
    }
}
