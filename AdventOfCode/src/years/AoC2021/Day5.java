package years.AoC2021;

import common.Day;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

// https://adventofcode.com/2021/day/5
public class Day5 implements Day {

    private final HashMap<String, Integer> spots = new HashMap<>();

    @Override
    public void run(BufferedReader reader) throws IOException {
        readInput(false, reader);
        System.out.println("Part 1: " + spots.entrySet().stream().filter(map -> map.getValue() > 1).toList().size());
        readInput(true, reader);
        System.out.println("Part 2: " + spots.entrySet().stream().filter(map -> map.getValue() > 1).toList().size());
    }

    private void readInput(final boolean includeDiagonals, BufferedReader reader) throws IOException {
        spots.clear();
        String line;
        while ((line = reader.readLine()) != null) {
            final String[] coords = line.split(" -> ");
            final Integer[] begin = Arrays.stream(coords[0].split(",")).map(Integer::parseInt).toArray(Integer[]::new);
            final Integer[] end = Arrays.stream(coords[1].split(",")).map(Integer::parseInt).toArray(Integer[]::new);
            final int xOffset = end[0].compareTo(begin[0]);
            final int yOffset = end[1].compareTo(begin[1]);
            if (includeDiagonals || xOffset == 0 || yOffset == 0) {
                int currentX = begin[0], currentY = begin[1];
                final String endSpot = buildIndex(end[0], end[1]);
                String currentSpot = buildIndex(currentX, currentY);
                addSpot(currentSpot);
                while (!currentSpot.equals(endSpot)) {
                    currentX += xOffset;
                    currentY += yOffset;
                    currentSpot = buildIndex(currentX, currentY);
                    addSpot(currentSpot);
                }
            }
        }
    }

    private String buildIndex(final int x, final int y) {
        return x + "," + y;
    }

    private void addSpot(final String spot) {
        spots.compute(spot, (key, val) -> (val == null) ? 1 : val + 1);
    }
}
