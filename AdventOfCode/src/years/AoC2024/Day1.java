package years.AoC2024;

import common.Day;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Day1 implements Day {

    private final ArrayList<Integer> list1 = new ArrayList<>();
    private final ArrayList<Integer> list2 = new ArrayList<>();
    private final HashMap<Integer, Integer> list2Occurrences = new HashMap<>();

    @Override
    public void run(BufferedReader reader) throws IOException {
        int part1Sum = 0, part2Sum = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] vals = line.split("   ");
            list1.add(Integer.parseInt(vals[0]));
            list2.add(Integer.parseInt(vals[1]));
            list2Occurrences.compute(Integer.parseInt(vals[1]), (key, value) -> (value == null) ? 1 : value + 1);
        }
        Collections.sort(list1);
        Collections.sort(list2);
        for (int i = 0; i < list1.size(); i++) {
            part1Sum += Math.abs(list1.get(i) - list2.get(i));
            part2Sum += list1.get(i) * list2Occurrences.getOrDefault(list1.get(i), 0);
        }
        System.out.println("Part 1: " + part1Sum);
        System.out.println("Part 2: " + part2Sum);
    }
}
