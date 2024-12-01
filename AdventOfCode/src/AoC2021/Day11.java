package AoC2021;

import common.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

// https://adventofcode.com/2021/day/11
public class Day11 implements Day {

    private final ArrayList<int[]> inputs = new ArrayList<>();
    private final ArrayList<String> flashed = new ArrayList<>();
    private int stepNumber = 1;
    private int totalFlashes = 0;

    @Override
    public void run(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            inputs.add(Arrays.stream(line.split("")).mapToInt(Integer::parseInt).toArray());
        }
        while (takeStep()) {
            stepNumber++;
        }
        System.out.println("Part 1: " + totalFlashes);
        System.out.println("Part 2: " + stepNumber);
    }

    private boolean takeStep() {
        for (int row = 0; row < inputs.size(); row++) {
            for (int col = 0; col < inputs.get(row).length; col++) {
                increase(row, col);
            }
        }
        final boolean allFlashed = flashed.size() == inputs.size() * inputs.get(0).length;
        flashed.clear();
        return !allFlashed;
    }

    private void increase(final int row, final int col) {
        if (flashed.contains(row + "" + col))
            return;
        inputs.get(row)[col] = (inputs.get(row)[col] + 1) % 10;
        if (inputs.get(row)[col] == 0) {
            flashed.add(row + "" + col);
            int NUM_STEPS = 100;
            if (stepNumber <= NUM_STEPS) totalFlashes++;
            for (int r = Math.max(row - 1, 0); r <= Math.min(row + 1, inputs.size() - 1); r++) {
                for (int c = Math.max(col - 1, 0); c <= Math.min(col + 1, inputs.get(0).length - 1); c++) {
                    increase(r, c);
                }
            }
        }
    }
}
