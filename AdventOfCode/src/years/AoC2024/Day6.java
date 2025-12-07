package years.AoC2024;

import common.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Day6 implements Day {

    private final ArrayList<int[]> visited = new ArrayList<>();
    int startingX;
    int startingY;
    int[][] directions = {
            {0, -1},
            {1, 0},
            {0, 1},
            {-1, 0}
    };

    public void run(ArrayList<String> input) throws IOException {
        int counter = 0;
        final ArrayList<Boolean[]> colliders = new ArrayList<>();
        for (String line : input) {
            if (line.contains("^")) {
                startingX = line.indexOf('^');
                startingY = counter;
            }
            colliders.add(line.chars()
                    .mapToObj(c -> c == '#')
                    .toArray(Boolean[]::new));
            counter++;
        }
        int part1Sum = walkPath(colliders, true), part2Sum = 0;
        for (int[] v : visited) {
            final ArrayList<Boolean[]> map = new ArrayList<>();
            for (Boolean[] array : colliders) {
                map.add(array.clone());
            }
            map.get(v[1])[v[0]] = true;
            part2Sum += walkPath(map, false) < 0 ? 1 : 0;
        }
        System.out.println("Part 1: " + part1Sum);
        System.out.println("Part 2: " + part2Sum);
    }

    private int walkPath(ArrayList<Boolean[]> colliders, boolean populate) {
        final ArrayList<String> prevStops = new ArrayList<>();
        int x = startingX, y = startingY, d = 0;
        while (true) {
            int finalX = x, finalY = y;
            if (populate && visited.stream().noneMatch(arr -> Arrays.equals(arr, new int[]{finalX, finalY}))) {
                visited.add(new int[]{x, y});
            }
            if (y + directions[d][1] >= 0 && y + directions[d][1] < colliders.size() && x + directions[d][0] >= 0 && x + directions[d][0] < colliders.get(0).length) {
                if (!colliders.get(y + directions[d][1])[x + directions[d][0]]) {
                    x += directions[d][0];
                    y += directions[d][1];
                } else {
                    if (prevStops.contains(x + " " + y + " " + d)) {
                        return -1;
                    }
                    prevStops.add(x + " " + y + " " + d);
                    d = (d + 1) % 4;
                }
            } else {
                return visited.size();
            }
        }
    }
}