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
        String[] foundWords = findWords(c, r, 8, 0, 3);
        return Arrays.stream(foundWords).filter("XMAS"::equals).count();
    }

    private long findPart2(int c, int r) {
        String[] foundWords = findWords(c, r, 4, -1, 1);
        return Arrays.stream(foundWords).filter("MAS"::equals).count() == 2 ? 1 : 0;
    }

    private String[] findWords(int c, int r, int count, int rangeA, int rangeB) {
        String[] foundWords = new String[count];
        Arrays.fill(foundWords, "");
        for (int i = rangeA; i <= rangeB; i++) {
            for (int d = 0; d < count; d++) {
                foundWords[d] += findChar(c, r, directions[d][0] * i, directions[d][1] * i);
            }
        }
        return foundWords;
    }

    private char findChar(int c, int r, int cDiff, int rDiff) {
        try {
            return map.get(r + rDiff).charAt(c + cDiff);
        } catch  (Exception e) {
            return ' ';
        }
    }
}
