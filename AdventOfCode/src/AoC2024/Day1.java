package AoC2024;

import common.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Day1 implements Day {

    private final ArrayList<Integer> list1 = new ArrayList<>();
    private final ArrayList<Integer> list2 = new ArrayList<>();
    private final HashMap<Integer, Integer> list2Occurences = new HashMap<>();

    @Override
    public void run(String inputPath) throws IOException {
        int part1Sum = 0, part2Sum = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(inputPath))) {
            String line = br.readLine();
            while (line != null) {
                String[] vals = line.split("   ");
                int val0 = Integer.parseInt(vals[0]);
                int val1 = Integer.parseInt(vals[1]);
                addInOrder(list1, val0);
                addInOrder(list2, val1);
                list2Occurences.compute(val1, (key, value) -> (value == null) ? 1 : value + 1);
                line = br.readLine();
            }
        }
        for (int i = 0; i < list1.size(); i++) {
            part1Sum += Math.abs(list1.get(i) - list2.get(i));
            Integer mult = list2Occurences.get(list1.get(i));
            part2Sum += mult == null ? 0 : list1.get(i) * mult;
        }
        System.out.println("Part 1: " + part1Sum);
        System.out.println("Part 2: " + part2Sum);
    }

    public void addInOrder(ArrayList<Integer> list, int element) {
        int index = Collections.binarySearch(list, element);
        list.add(index < 0 ? -(index + 1) : index, element);
    }
}
