package AoC2024;

import common.Day;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

// https://adventofcode.com/2024/day/2
public class Day2 implements Day {

    @Override
    public void run(BufferedReader reader) throws IOException {
        int part1Sum = 0, part2Sum = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            int[] levels = Arrays.stream(line.split("\\s+")).mapToInt(Integer::parseInt).toArray();
            part1Sum += reportValid(levels, false) ? 1 : 0;
            part2Sum += reportValid(levels, true) ? 1 : 0;
        }
        System.out.println("Part 1: " + part1Sum);
        System.out.println("Part 2: " + part2Sum);
    }

    private static boolean reportValid(int[] levels, boolean dampen) {
        boolean increasing = levels[1] > levels[0];
        for (int i = 1; i < levels.length; i++) {
            int diff = levels[i] - levels[i - 1];
            if (diff == 0 || Math.abs(diff) > 3 || diff > 0 ^ increasing) {
                if (dampen) {
                    return reportValid(removeElement(levels, i), false) ||
                            reportValid(removeElement(levels, i - 1), false) ||
                            i >= 2 && reportValid(removeElement(levels, i - 2), false);
                }
                return false;
            }
        }
        return true;
    }

    public static int[] removeElement(int[] array, int indexToRemove) {
        int[] result = new int[array.length - 1];
        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != indexToRemove) {
                result[j++] = array[i];
            }
        }
        return result;
    }
}
