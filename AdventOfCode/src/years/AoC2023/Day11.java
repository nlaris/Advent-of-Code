package years.AoC2023;

import common.Day;

import java.io.IOException;
import java.util.ArrayList;

public class Day11 implements Day {

    private final ArrayList<int[]> galaxies = new ArrayList<>();

    private boolean[] galaxyColumns;
    private boolean[] galaxyRows;

    public void run(ArrayList<String> input) throws IOException {
        galaxyColumns = new boolean[input.get(0).length()];
        galaxyRows = new boolean[input.get(0).length()];
        int row = 0;
        for (String line : input) {
            int galaxyIndex = line.indexOf('#', 0);
            while (galaxyIndex >= 0) {
                galaxies.add(new int[]{galaxyIndex, row});
                galaxyColumns[galaxyIndex] = true;
                galaxyRows[row] = true;
                galaxyIndex = line.indexOf('#', galaxyIndex + 1);
            }
            row++;
        }
        System.out.println("Part 1: " + getLengthSum(2));
        System.out.println("Part 2: " + getLengthSum(1000000));
    }

    private long getLengthSum(int expansionRate) {
        long sum = 0;
        for (int i = 0; i < galaxies.size() - 1; i++) {
            int[] galaxyA = galaxies.get(i);
            for (int j = i + 1; j < galaxies.size(); j++) {
                int[] galaxyB = galaxies.get(j);
                int minX = Math.min(galaxyA[0], galaxyB[0]);
                int maxX = Math.max(galaxyA[0], galaxyB[0]);
                sum += ((maxX - minX) + (galaxyB[1] - galaxyA[1]));
                for (int col = minX + 1; col < maxX; col++) {
                    if (!galaxyColumns[col]) sum += expansionRate - 1;
                }
                for (int row = galaxyA[1] + 1; row < galaxyB[1]; row++) {
                    if (!galaxyRows[row]) sum += expansionRate - 1;
                }
            }
        }
        return sum;
    }
}