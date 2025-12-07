package years.AoC2024;

import common.Day;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Day10 implements Day {
    private ArrayList<int[]> map;
    private ArrayList<String> checked;
    private int part1Sum = 0, part2Sum = 0;
    int[][] directions = {
            {0, -1},
            {1, 0},
            {0, 1},
            {-1, 0}
    };

    public void run(ArrayList<String> input) {
        map = input.stream()
                .map(line -> line.chars().map(Character::getNumericValue).toArray())
                .collect(Collectors.toCollection(ArrayList::new));
        for (int r = 0; r < map.size(); r++) {
            int[] row = map.get(r);
            for (int c = 0; c < row.length; c++) {
                checked = new ArrayList<>();
                getTrailScore(0, r, c);
            }
        }
        System.out.println("Part 1: " + part1Sum);
        System.out.println("Part 2: " + part2Sum);
    }

    private void getTrailScore(int target, int r, int c) {
        if (map.get(r)[c] != target) return;
        if (map.get(r)[c] == 9) {
            part2Sum++;
            if (checked.contains(r + " " + c)) return;
            part1Sum++;
            checked.add(r + " " + c);
            return;
        }
        for (int i = 0; i < 4; i++) {
            int[] d = directions[i];
            if (getPoint(r + d[0], c + d[1]) == target + 1) {
                getTrailScore(target + 1, r + d[0], c + d[1]);
            }
        }
    }

    private int getPoint(int r, int c) {
        if (r < 0 || r >= map.size() || c < 0 || c >= map.get(0).length) return - 1;
        return map.get(r)[c];
    }
}
