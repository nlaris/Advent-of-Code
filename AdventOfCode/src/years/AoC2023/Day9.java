package years.AoC2023;

import common.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9 implements Day {

    private int part1Sum = 0;
    private int part2Sum = 0;

    public void run(ArrayList<String> input) throws IOException {
        for (String line : input) {
            getExtrapolatedValues(Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).boxed().toList());
        }
        System.out.println("Part 1: " + part1Sum);
        System.out.println("Part 2: " + part2Sum);
    }

    private void getExtrapolatedValues(List<Integer> history) {
        ArrayList<ArrayList<Integer>> sequences = new ArrayList<>();
        sequences.add(new ArrayList<>(history));
        while (sequences.get(0).stream().anyMatch(x -> x != 0)) {
            ArrayList<Integer> diffs = new ArrayList<>();
            for (int i = 0; i < sequences.get(0).size() - 1; i++) {
                diffs.add(sequences.get(0).get(i + 1) - sequences.get(0).get(i));
            }
            sequences.add(0, diffs);
        }
        sequences.get(0).add(0);
        for (int i = 1; i < sequences.size(); i++) {
            final int lastIndex = sequences.get(i).size() - 1;
            sequences.get(i).add(sequences.get(i).get(lastIndex) + sequences.get(i - 1).get(lastIndex));
            sequences.get(i).add(0, sequences.get(i).get(0) - sequences.get(i - 1).get(0));
        }
        part1Sum += sequences.get(sequences.size() - 1).get(sequences.get(sequences.size() - 1).size() - 1);
        part2Sum += sequences.get(sequences.size() - 1).get(0);
    }
}
