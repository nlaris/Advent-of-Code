package AoC2024;

import common.Day;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

// https://adventofcode.com/2024/day/2
public class Day2 implements Day {

    @Override
    public void run(BufferedReader reader) throws IOException {
        int part1Sum = 0, part2Sum = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            ArrayList<Integer> levels = Arrays.stream(line.split("\\s+"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toCollection(ArrayList::new));
            part1Sum += reportValid(levels, false) ? 1 : 0;
            part2Sum += reportValid(levels, true) ? 1 : 0;
        }
        System.out.println("Part 1: " + part1Sum);
        System.out.println("Part 2: " + part2Sum);
    }

    private static boolean reportValid(ArrayList<Integer> levels, boolean dampen) {
        boolean increasing = levels.get(1) >  levels.get(0);
        for (int i = 1; i < levels.size(); i++) {
            int diff = levels.get(i) - levels.get(i - 1);
            if (diff == 0 || Math.abs(diff) > 3 || diff > 0 ^ increasing) {
                if (dampen) {
                    return reportValid(removeElement(levels, i), false) || // This will cover the diff == 0 cases, and some other cases
                            reportValid(removeElement(levels, i - 1), false) || // This will cover remaining diff > 3 cases, and some !direction cases
                            i >= 2 && reportValid(removeElement(levels, i - 2), false); // This will cover remaining !direction cases
                }
                return false;
            }
        }
        return true;
    }

    public static ArrayList<Integer> removeElement(ArrayList<Integer> levels, int indexToRemove) {
        ArrayList<Integer> newList = new ArrayList<>(levels);
        newList.remove(indexToRemove);
        return newList;
    }
}
