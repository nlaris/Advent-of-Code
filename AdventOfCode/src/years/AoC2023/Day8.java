package years.AoC2023;

import common.Day;

import java.util.ArrayList;
import java.util.HashMap;

public class Day8 implements Day {

    private final HashMap<String, String[]> nodes = new HashMap<>();
    private final ArrayList<String> startingNodes = new ArrayList<>();

    private String instructions;

    public void run(ArrayList<String> input) {
        instructions = input.get(0);
        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i);
            final String[] vals = line.replaceAll("[^A-Z0-9]+", " ").trim().split(" ");
            nodes.put(vals[0], new String[]{vals[1], vals[2]});
            if (vals[0].endsWith("A")) startingNodes.add(vals[0]);
        }
        System.out.println("Part 1: " + getPart1Steps());
        System.out.println("Part 2: " + getPart2Steps());
    }

    private int getPart1Steps() {
        String currentNode = "AAA";
        int steps = 0;
        while (!currentNode.equals("ZZZ"))
            currentNode = nodes.get(currentNode)[instructions.charAt(steps++ % instructions.length()) == 'L' ? 0 : 1];
        return steps;
    }

    private long getPart2Steps() {
        long steps = 1;
        for (String node : startingNodes) {
            int stepsToEnd = 0;
            while (!node.endsWith("Z"))
                node = nodes.get(node)[instructions.charAt(stepsToEnd++ % instructions.length()) == 'L' ? 0 : 1];
            steps = lowestCommonMultiple(steps, stepsToEnd);
        }
        return steps;
    }

    private long lowestCommonMultiple(long a, long b) {
        long lcm = Math.max(a, b);
        while (lcm % Math.min(a, b) != 0) lcm += Math.max(a, b);
        return lcm;
    }
}
