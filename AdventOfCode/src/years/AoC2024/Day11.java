package years.AoC2024;

import common.Day;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day11 implements Day {

    private final HashMap<String, Long> mappings = new HashMap<>();

    public void run(BufferedReader reader) throws IOException {
        ArrayList<String> rocks = new ArrayList<>(Arrays.asList(reader.readLine().split(" ")));
        long part1Sum = 0, part2Sum = 0;
        for (String rock : rocks) {
            part1Sum += getScore(rock, 25);
            part2Sum += getScore(rock, 75);
        }
        System.out.println("Part 1: " + part1Sum);
        System.out.println("Part 2: " + part2Sum);
    }

    private long getScore(String r, int times) {
        if (times == 0) return 1;
        if (mappings.containsKey(r + " " + times)) {
            return mappings.get(r + " " + times);
        }
        ArrayList<String> newRocks = new ArrayList<>();
        long sum = 0;
        if (r.equals("0")) {
            newRocks.add("1");
        } else if (r.length() % 2 == 0) {
            newRocks.add(r.substring(0, r.length() / 2));
            newRocks.add(Long.parseLong(r.substring(r.length() / 2)) + "");
        } else {
            newRocks.add(Long.parseLong(r) * 2024 + "");
        }
        for (String rock : newRocks) {
            long score = getScore(rock, times - 1);
            mappings.put(rock + " " + (times - 1), score);
            sum += score;
        }
        return sum;
    }
}
