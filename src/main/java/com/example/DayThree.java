package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class DayThree {

    public static void main(String[] args) {
        System.out.println(DayThree.solveComplex("input()"));
    }

    public static String demoInput() {
        return """
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..""";
    }

    public static int solve(String input) {
        List<String> lines = input.lines().toList();
        List<Symbol> symbols = getSymbols(lines);
        List<SchematicNumber> schematicNumbers = getSchematicNumbers(lines);
        return schematicNumbers.stream()
                .filter(schematicNumber -> checkSymbolAdjacency(schematicNumber, symbols))
                .mapToInt(schematicNumber -> schematicNumber.value)
                .sum();
    }

    public static int solveComplex(String input) {
        List<String> lines = input.lines().toList();
        List<Symbol> gearSymbols = getSymbols(lines).stream().filter(symbol -> symbol.value.equals("*")).toList();
        List<SchematicNumber> schematicNumbers = getSchematicNumbers(lines);
        return gearSymbols.stream()
                .map(gear -> adjacentToExactlyTwoNumbers(gear, schematicNumbers))
                .flatMap(Optional::stream)
                .mapToInt(Gear::value)
                .sum();
    }

    private static Optional<Gear> adjacentToExactlyTwoNumbers(Symbol symbol, List<SchematicNumber> numbers) {
        var adjacent = numbers.stream()
                .filter(number -> number.line + 1 >= symbol.line && number.line - 1 <= symbol.line)
                .filter(number -> number.start - 1 <= symbol.column && number.end + 1 >= symbol.column)
                .toList();
        if (adjacent.size() != 2)
            return Optional.empty();
        return Optional.of(new Gear(symbol, adjacent.get(0), adjacent.get(1)));
    }

    private static boolean checkSymbolAdjacency(SchematicNumber number, List<Symbol> symbols) {
        return symbols.stream()
                .filter(symbol -> symbol.line + 1 >= number.line && symbol.line - 1 <= number.line)
                .anyMatch(symbol -> symbol.column + 1 >= number.start && symbol.column - 1 <= number.end);
    }

    private static List<Symbol> getSymbols(List<String> lines) {
        List<Symbol> symbols = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            var matcher = Pattern.compile("[^0-9.]").matcher(lines.get(i));
            while (matcher.find()) {
                int index = matcher.end();
                String value = matcher.group();
                symbols.add(new Symbol(value, i, index - 1));
            }
        }
        return symbols;
    }

    private static List<SchematicNumber> getSchematicNumbers(List<String> lines) {
        List<SchematicNumber> schematicNumbers = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            var matcher = Pattern.compile("[0-9]+").matcher(lines.get(i));
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                int value = Integer.parseInt(matcher.group());
                schematicNumbers.add(new SchematicNumber(value, i, start, end - 1));
            }
        }
        return schematicNumbers;
    }

    record Symbol(String value, int line, int column) {}

    record SchematicNumber(int value, int line, int start, int end) {}

    record Gear(Symbol symbol, SchematicNumber first, SchematicNumber second) {
        int value() {
            return first.value * second.value;
        }
    }

}
