package years.AoC2024;

import common.Day;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Day8 implements Day {
    private final HashMap<Character, ArrayList<int[]>> mappings = new HashMap<>();
    int width, height;

    public void run(BufferedReader reader) throws IOException {
        String line;
        int r = 0;
        while ((line = reader.readLine()) != null) {
            width = line.length();
            for (int c = 0; c < line.length(); c++) {
                if (line.charAt(c) != '.') {
                    mappings.computeIfAbsent(line.charAt(c), k -> new ArrayList<>())
                            .add(new int[]{c, r});
                }
            }
            height = ++r;
        }
        System.out.println("Part 1: " + getAntinodes(false));
        System.out.println("Part 2: " + getAntinodes(true));
    }

    private int getAntinodes(boolean part2) {
        final ArrayList<int[]> antinodes = new ArrayList<>();
        for (Character c : mappings.keySet()) {
            ArrayList<int[]> spots = mappings.get(c);
            for (int i = 0; i < spots.size(); i++) {
                int[] a = spots.get(i);
                for (int j = i + 1; j < spots.size(); j++) {
                    int[] b = spots.get(j);
                    int count = part2 ? 0 : 1;
                    while (addIfValid(antinodes, b[0] + count * (b[0] - a[0]), b[1] + count * (b[1] - a[1])) && part2) {
                        count++;
                    }
                    count = part2 ? 0 : 1;
                    while (addIfValid(antinodes, a[0] + count * (a[0] - b[0]), a[1] + count * (a[1] - b[1])) && part2) {
                        count++;
                    }
                }
            }
        }
        return antinodes.size();
    }

    private boolean addIfValid(ArrayList<int[]> antinodes, int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) return false;
        if (antinodes.stream().noneMatch(arr -> Arrays.equals(arr, new int[]{x, y}))) {
            antinodes.add(new int[]{x, y});
        }
        return true;
    }
}
