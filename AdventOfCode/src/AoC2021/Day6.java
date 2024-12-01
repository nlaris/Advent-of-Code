package AoC2021;

import common.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// https://adventofcode.com/2021/day/6
public class Day6 implements Day {

    private List<Integer> initialSpawns = new ArrayList<>();
    private long[] spawns = new long[9];

    @Override
    public void run(final String inputPath) throws IOException {
        readInput(inputPath);
        for (Integer spawn : initialSpawns) {
            spawns[spawn]++;
        }
        simulateDays();
    }

    private void readInput(final String inputPath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(inputPath))) {
            initialSpawns = Arrays.stream(br.readLine().split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }
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
