package years.AoC2024;

import common.Day;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Day4 implements Day {

    final ArrayList<String> map = new ArrayList<>();
    int[][] directions = {
            {-1, -1},
            {1, -1},
            {-1, 1},
            {1, 1},
            {-1, 0},
            {0, -1},
            {1, 0},
            {0, 1}
    };

    @Override
    public void run(BufferedReader reader) throws IOException {
        String line;
        int part1Sum = 0, part2Sum = 0;
        while ((line = reader.readLine()) != null) {
            map.add(line);
        }
        for (int r = 0; r < map.size(); r++) {
            line = map.get(r);
            for (int c = 0; c < line.length(); c++) {
                char s = line.charAt(c);
                part1Sum += s == 'X' ? findPart1(c, r) : 0;
                part2Sum += s == 'A' ? findPart2(c, r) : 0;
            }
        }
        System.out.println("Part 1: " + part1Sum);
        System.out.println("Part 2: " + part2Sum);
    }

    private long findPart1(int c, int r) {
        String[] foundWords = new String[8];
        Arrays.fill(foundWords, "X");
        for (int i = 1; i < 4; i++) {
            for (int d = 0; d < directions.length; d++) {
                int rDiff = directions[d][0] * i;
                int cDiff = directions[d][1] * i;
                foundWords[d] += findChar(c, r, rDiff, cDiff);
            }
        }
        return Arrays.stream(foundWords).filter("XMAS"::equals).count();
    }

    private long findPart2(int c, int r) {
        String[] foundWords = new String[4];
        Arrays.fill(foundWords, "");
        for (int i = -1; i <= 1; i++) {
            for (int d = 0; d < 4; d++) {
                int rDiff = directions[d][0] * i;
                int cDiff = directions[d][1] * i;
                foundWords[d] += findChar(c, r, rDiff, cDiff);
            }
        }
        return Arrays.stream(foundWords).filter("MAS"::equals).count() == 2 ? 1 : 0;
    }

    private char findChar(int c, int r, int cDiff, int rDiff) {
        try {
            return map.get(r + rDiff).charAt(c + cDiff);
        } catch  (Exception e) {
            return ' ';
        }
    }
}
