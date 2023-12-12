package com.example;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DayEightPartTwo {

    public static long solve(String instructions, String desertMap) {
        List<String> instructionList = Arrays.stream(instructions.split("")).toList();
        List<Node> nodes = parseDesertMap(desertMap);
        var current = nodes.stream().filter(Node::isStartingNode).toList();
        List<Long> list = new ArrayList<>();
        for (Node single : current) {
            long numberOfSteps = 0;
            int j = nodes.indexOf(single);
            for (int i = 0; !nodes.get(j).isFinalNode(); i = (i+1) % instructionList.size()) {
                var instruction = instructionList.get(i);
                j = nodes.get(j).go(instruction);
                numberOfSteps++;
            }
            list.add(numberOfSteps);
        }
        return calculateLcm(list);
    }

    private static long calculateLcm(List<Long> list) {
        long lcm = 1;
        for (long value : list) {
            lcm = (lcm * value) / BigInteger.valueOf(lcm).gcd(BigInteger.valueOf(value)).longValue();
        }
        return lcm;
    }

    public record Node(String value, int left, int right) {
        public int go(String instruction) {
            return instruction.equals("L") ? left : right;
        }

        public boolean isStartingNode() {
            return value.endsWith("A");
        }

        public boolean isFinalNode() {
            return value.endsWith("Z");
        }
    }

    static List<Node> parseDesertMap(String desertMap) {
        List<String> nodeNames = desertMap.lines().map(s -> s.substring(0, 3)).toList();
        return desertMap.lines()
                .map(DayEightPartTwo::splitLine)
                .map(mapping -> new Node(mapping[0], nodeNames.indexOf(mapping[1]), nodeNames.indexOf(mapping[2])))
                .toList();
    }

    static String[] splitLine(String line) {
        return line.replaceAll("=", ",").replaceAll("[\\s+()]", "").split(",");
    }

}
