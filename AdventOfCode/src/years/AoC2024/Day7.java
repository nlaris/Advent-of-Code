package years.AoC2024;

import common.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Day7 implements Day {
    public void run(ArrayList<String> input) throws IOException {
        long part1Sum = 0, part2Sum = 0;
        for (String line : input) {
            long target = Long.parseLong(line.split(":")[0]);
            long[] values = Arrays.stream(line.split(": ")[1].split(" "))
                    .mapToLong(Long::parseLong)
                    .toArray();
            part1Sum += getScore(target, values, false);
            part2Sum += getScore(target, values, true);
        }
        System.out.println("Part 1: " + part1Sum);
        System.out.println("Part 2: " + part2Sum);
    }

    private long getScore(long target, long[] numbers, boolean part2) {
        ArrayList<Long> results = new ArrayList<>();
        results.add(numbers[0]);
        for (int i = 1; i < numbers.length; i++) {
            ArrayList<Long> newResults = new ArrayList<>();
            for (Long num : results) {
                newResults.add(num * numbers[i]);
                newResults.add(num + numbers[i]);
                if (part2) {
                    newResults.add(Long.parseLong(num + "" + numbers[i]));
                }
            }
            results = newResults;
        }
        return results.stream().anyMatch(x -> x.equals(target)) ? target : 0;
    }
}
