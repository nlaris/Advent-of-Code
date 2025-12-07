package years.AoC2021;

import common.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6 implements Day {

    private long[] spawns = new long[9];

    public void run(ArrayList<String> input) throws IOException {
        List<Integer> initialSpawns = Arrays.stream(input.get(0).split(","))
                .map(Integer::parseInt).toList();
        for (Integer spawn : initialSpawns) {
            spawns[spawn]++;
        }
        simulateDays();
    }

    private void simulateDays() {
        for (int day = 1; day <= 256; day++) {
            long[] newSpawns = new long[9];
            for (int i = spawns.length - 1; i >= 0; i--) {
                long numSpawns = spawns[i];
                if (i == 0){
                    newSpawns[6] += numSpawns;
                    newSpawns[8] += numSpawns;
                } else {
                    newSpawns[i - 1] += numSpawns;
                }
            }
            spawns = newSpawns.clone();
            if (day == 80) {
                System.out.println("Part 1: " + Arrays.stream(spawns).sum());
            }
        }
        System.out.println("Part 2: " + Arrays.stream(spawns).sum());
    }
}
