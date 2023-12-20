package AoC2021;

import common.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

// https://adventofcode.com/2021/day/12
public class Day12 implements Day {

    private final HashMap<String, ArrayList<String>> mappings = new HashMap<>();

    public void run(final String inputPath) throws IOException {
        readInput(inputPath);
        System.out.println("Part 1: " + checkPaths("start", new HashMap<>(), 1));
        System.out.println("Part 2: " + checkPaths("start", new HashMap<>(), 2));
    }

    private int checkPaths(final String p,
                                   final HashMap<String,Integer> smallCavesChecked,
                                   final int smallCaveLimit) {
        if (p.equals("end")) {
            return 1;
        }
        if (Character.isLowerCase(p.charAt(0))) {
            if (smallCavesChecked.values().stream().anyMatch(x -> x == smallCaveLimit) && smallCavesChecked.containsKey(p)) {
                return 0;
            }
            smallCavesChecked.compute(p, (key, val) -> (val == null) ? 1 : val + 1);
        }
        return mappings.get(p).stream().mapToInt(x -> checkPaths(x, new HashMap<>(smallCavesChecked),smallCaveLimit)).sum();
    }

    private void readInput(final String inputPath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(inputPath))) {
            String line = br.readLine();
            while (line != null) {
                final String[] points = line.split("-");
                addMapping(points[0], points[1]);
                addMapping(points[1], points[0]);
                line = br.readLine();
            }
        }
    }

    private void addMapping(final String from, final String to) {
        if (to.equals("start")) return;
        mappings.computeIfAbsent(from, k -> new ArrayList<>());
        mappings.get(from).add(to);
    }
}
