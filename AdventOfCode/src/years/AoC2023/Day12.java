package years.AoC2023;

import common.Day;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day12 implements Day {

    @Override
    public void run(BufferedReader reader) throws IOException {
        int part1Sum = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            part1Sum += getTotalArrangements(Arrays.stream(parts[0].replace('.', ' ').trim().split(" +")).toList(),
                    Arrays.stream(parts[1].split(",")).mapToInt(Integer::parseInt).boxed().toList());
        }
        System.out.println("Part 1: " + part1Sum);
    }

    private int getTotalArrangements(List<String> springs, List<Integer> groups) {
        if (groups.size() == 0) return springs.stream().noneMatch(s -> s.contains("#")) ? 1 : 0;
        if (springs.size() == 0) return 0;
        int sum = 0;
        final int groupSize = groups.get(0);
        boolean hasDamage = false;
        for (int i = 0; i < springs.size() && !hasDamage; i++) {
            final String spring = springs.get(i);
            hasDamage = spring.contains("#");
            if (spring.length() == groupSize) {
                sum += getTotalArrangements(springs.subList(i + 1, springs.size()), groups.subList(1, groups.size()));
                if (spring.charAt(0) == '#') return sum;
            } else if (spring.length() > groupSize) {
                for (int j = 0; j <= spring.length() - groupSize; j++) {
                    if (j + groupSize == spring.length()) {
                        sum += getTotalArrangements(springs.subList(i + 1, springs.size()), groups.subList(1, groups.size()));
                    } else if (spring.charAt(j + groupSize) != '#') {
                        String s = spring.substring(j + groupSize + 1);
                        ArrayList<String> newSprings = new ArrayList<>(springs);
                        if (i > 0) newSprings.subList(0, i).clear();
                        if (s.isEmpty()) newSprings.remove(0);
                        else newSprings.set(0, s);
                        sum += getTotalArrangements(newSprings, groups.subList(1, groups.size()));
                    }
                    if (spring.charAt(j) == '#') return sum;
                }
            }
        }
        return sum;
    }
}
