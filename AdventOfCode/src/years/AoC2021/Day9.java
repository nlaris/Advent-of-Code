package years.AoC2021;

import common.Day;

import java.util.ArrayList;
import java.util.Arrays;

public class Day9 implements Day {

    private final ArrayList<int[]> points = new ArrayList<>();
    private final ArrayList<String> checkedPoints = new ArrayList<>();
    private final ArrayList<Integer> biggestBasins = new ArrayList<>();

    public void run(ArrayList<String> input) {
        for (String line : input) {
            points.add(Arrays.stream(line.split("")).mapToInt(Integer::parseInt).toArray());
        }
        int totalRiskLevels = 0;
        for (int row = 0; row < points.size(); row++) {
            int[] rowPoints = points.get(row);
            for (int col = 0; col< rowPoints.length; col++) {
                int point = rowPoints[col];
                boolean lowPoint = (col == 0 || point < rowPoints[col-1]) &&
                        (col == rowPoints.length - 1 || point < rowPoints[col+1]) &&
                        (row == 0 || point < points.get(row-1)[col]) &&
                        (row == points.size() - 1 || point < points.get(row+1)[col]);
                if (lowPoint) {
                    totalRiskLevels += (point + 1);
                    addBasin(getBasinSize(row, col));
                    checkedPoints.clear();
                }
            }
        }
        System.out.println("Part 1: " + totalRiskLevels);
        System.out.println("Part 2: " + biggestBasins.stream().reduce(1, (total, i) -> total * i));
    }

    private int getBasinSize(final int row, final int col) {
        checkedPoints.add(row + "" + col);
        int point = points.get(row)[col];
        if (point == 9) return 0;
        if (point == 8) return 1;
        return 1 + (!checkedPoints.contains(row + "" + (col - 1)) && (col > 0 && point < points.get(row)[col - 1]) ? getBasinSize(row, col - 1) : 0) +
                   (!checkedPoints.contains(row + "" + (col + 1)) && (col < points.get(row).length - 1 && point < points.get(row)[col + 1]) ? getBasinSize(row, col + 1) : 0) +
                   (!checkedPoints.contains((row - 1) + "" + col) && (row > 0 && point < points.get(row - 1)[col]) ? getBasinSize(row - 1, col) : 0) +
                   (!checkedPoints.contains((row + 1) + "" + col) && (row < points.size() - 1 && point < points.get(row + 1)[col]) ? getBasinSize(row + 1, col) : 0);
    }

    private void addBasin(final int basinSize) {
        int index = 0;
        while (index < biggestBasins.size() && basinSize < biggestBasins.get(index)) {
            index++;
        }
        biggestBasins.add(index, basinSize);
        if (biggestBasins.size() == 4) biggestBasins.remove(3);
    }
}
