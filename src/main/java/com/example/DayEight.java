package com.example;

import java.util.Arrays;
import java.util.List;

public class DayEight {

    public static long solve(String instructions, String desertMap) {
        System.out.println(desertMap.lines().count());
        List<String> instructionList = Arrays.stream(instructions.split("")).toList();
        List<Node> nodes = parseDesertMap(desertMap);
        Node start = nodes.stream().filter(Node::isStartingNode).findFirst().orElseThrow();
        int j = nodes.indexOf(start);
        long numberOfSteps = 0;
        for (int i = 0; !nodes.get(j).isFinalNode(); i = (i+1) % instructionList.size()) {
            var instruction = instructionList.get(i);
            j = nodes.get(j).go(instruction);
            numberOfSteps++;
        }
        return numberOfSteps;
    }

    public record Node(String value, int left, int right) {
        public int go(String instruction) {
            return instruction.equals("L") ? left : right;
        }

        public boolean isStartingNode() {
            return value.equals("AAA");
        }

        public boolean isFinalNode() {
            return value.equals("ZZZ");
        }
    }

    static List<Node> parseDesertMap(String desertMap) {
        List<String> nodeNames = desertMap.lines().map(s -> s.substring(0, 3)).toList();
        return desertMap.lines()
                .map(DayEight::splitLine)
                .map(mapping -> new Node(mapping[0], nodeNames.indexOf(mapping[1]), nodeNames.indexOf(mapping[2])))
                .toList();
    }

    static String[] splitLine(String line) {
        return line.replaceAll("=", ",").replaceAll("[\\s+()]", "").split(",");
    }

}
