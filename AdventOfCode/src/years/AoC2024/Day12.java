package years.AoC2024;

import common.Day;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day12 implements Day {

    private ArrayList<String> lines;
    private final int[][] directions = {
            {0, -1},
            {1, 0},
            {0, 1},
            {-1, 0}
    };
    private final ArrayList<String> checked = new ArrayList<>();
    private final ArrayList<int[]> queue = new ArrayList<>();
    private int part1Sum = 0, part2Sum = 0;

    public void run(ArrayList<String> input) throws IOException {
        lines = new ArrayList<>(input);
        for (int r = 0; r < input.size(); r++) {
            String line = input.get(r);
            for (int c = 0; c < line.length(); c++) {
                if (!checked.contains(getKey(new int[]{c, r}))) {
                    calculateRegionPrice(c, r, input);
                }
            }
        }
        System.out.println("Part 1: " + part1Sum);
        System.out.println("Part 2: " + part2Sum);
    }

    private void calculateRegionPrice(int startCol, int startRow, ArrayList<String> input) {
        final ArrayList<int[]> fences = new ArrayList<>();
        char p = lines.get(startRow).charAt(startCol);
        queue.add(new int[]{startCol, startRow});
        checked.add(getKey(new int[]{startCol, startRow}));
        int perimeter = 0, area = 0;
        while (!queue.isEmpty()) {
            area++;
            int[] plot = queue.remove(0);
            int c = plot[0], r = plot[1];
            for (int d = 0; d < directions.length; d++) {
                int[] direction = directions[d];
                int nextC = c + direction[0], nextR = r + direction[1];
                if (needsFence(p, nextC, nextR)) {
                    perimeter++;
                    fences.add(new int[]{nextC, nextR, d});
                } else if (!checked.contains(getKey(new int[]{nextC, nextR}))) {
                    checked.add(nextC + "|" + nextR);
                    queue.add(new int[]{nextC, nextR});
                }
            }
        }
        part1Sum += area * perimeter;
        part2Sum += area * getNumSides(fences);
    }

    private int getNumSides(ArrayList<int[]> fences) {
        final ArrayList<String> checked = new ArrayList<>();
        int sides = 0;
        for (int i = 0; i < fences.size(); i++) {
            if (!checked.contains(getKey(fences.get(i)))) {
                ArrayList<int[]> fencesToCheck = new ArrayList<>();
                fencesToCheck.add(fences.get(i));
                while (!fencesToCheck.isEmpty()) {
                    int[] fence = fencesToCheck.remove(0);
                    int c = fence[0];
                    int r = fence[1];
                    int direction = fence[2];
                    if (!checked.contains(getKey(fence))) {
                        checked.add(getKey(fence));
                        ArrayList<int[]> matchingFences;
                        if (direction % 2 == 1) {
                            int minBound = r - 1, maxBound = r + 1;
                            matchingFences = fences.stream()
                                    .filter(f -> f[2] == direction
                                            && f[0] == c
                                            && (f[1] == minBound || f[1] == maxBound)
                                            && !checked.contains(getKey(f)))
                                    .collect(Collectors.toCollection(ArrayList::new));
                        } else {
                            int minBound = c - 1, maxBound = c + 1;
                            matchingFences = fences.stream()
                                    .filter(f -> f[2] == direction
                                            && f[1] == r
                                            && (f[0] == minBound || f[0] == maxBound)
                                            && !checked.contains(getKey(f)))
                                    .collect(Collectors.toCollection(ArrayList::new));
                        }
                        fencesToCheck.addAll(matchingFences);
                    }
                }
                sides++;
            }
        }
        return sides;
    }

    private boolean needsFence(char p, int c, int r) {
        if (r < 0 || r >= lines.size() || c < 0 || c >= lines.get(0).length()) return true;
        return lines.get(r).charAt(c) != p;
    }

    private String getKey(int[] key) {
        return Arrays.stream(key)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining("|"));
    }
}
