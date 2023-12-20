package AoC2021;

import common.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// https://adventofcode.com/2021/day/13
public class Day13 implements Day {

    private final HashMap<Integer, Set<Integer>> dots = new HashMap<>();
    private final ArrayList<String> folds = new ArrayList<>();

    public void run(final String inputPath) throws IOException {
        readInput(inputPath);
        boolean firstFold = true;
        for (String fold : folds) {
            foldPaper(fold);
            if (firstFold) {
                firstFold = false;
                System.out.println("Part 1: " + dots.values().stream().mapToInt(Set::size).sum());
            }
        }
        System.out.println("Part 2: ");
        printCode();
    }

    private void foldPaper(final String fold) {
        final String[] instructions = fold.split("=");
        final int foldLoc = Integer.parseInt(instructions[1]);
        if (instructions[0].equals("x")) {
            for ( Map.Entry<Integer, Set<Integer>> entry : dots.entrySet()) {
                for (int dot : entry.getValue().stream().filter(y -> y > foldLoc).toList()) {
                    final int newDotLoc = foldLoc * 2 - dot;
                    entry.getValue().remove(dot);
                    entry.getValue().add(newDotLoc);
                }
                entry.getValue().remove(foldLoc);
            }
        } else {
            for ( Map.Entry<Integer, Set<Integer>> entry : dots.entrySet().stream().filter(x -> x.getKey() > foldLoc).toList()) {
                for (int dot : entry.getValue()) {
                    final int newDotLoc = foldLoc * 2 - entry.getKey();
                    dots.computeIfAbsent(newDotLoc, k -> new HashSet<>());
                    dots.get(newDotLoc).add(dot);
                }
                dots.remove(entry.getKey());
            }
        }
    }

    private void printCode() {
        final int xMax = Collections.max(dots.keySet());
        final int yMax = Collections.max(dots.values().stream().flatMap(Set::stream).toList());
        for (int x = 0; x <= xMax; x++) {
            for (int y = 0; y <= yMax; y++) {
                System.out.print(dots.get(x).contains(y) ? "# " : ". ");
            }
            System.out.println();
        }
    }

    private void readInput(final String inputPath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(inputPath))) {
            String line = br.readLine();
            while (!line.isEmpty()) {
                final int[] coordinates = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
                dots.computeIfAbsent(coordinates[1], k -> new HashSet<>());
                dots.get(coordinates[1]).add(coordinates[0]);
                line = br.readLine();
            }
            line = br.readLine();
            while (line != null) {
                folds.add(line.split(" ")[2]);
                line = br.readLine();
            }
        }
    }
}
