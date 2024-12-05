package years.AoC2024;

import common.Day;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Day5 implements Day {

    private final ArrayList<int[]> rules = new ArrayList<>();
    private final ArrayList<ArrayList<Integer>> updates = new ArrayList<>();

    public void run(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            String[] vals = line.split("\\|");
            rules.add(new int[]{Integer.parseInt(vals[0]), Integer.parseInt(vals[1])});
        }
        while ((line = reader.readLine()) != null) {
            String[] vals = line.split(",");
            updates.add(Arrays.stream(vals)
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toCollection(ArrayList::new)));
        }
        int part1Sum = 0, part2Sum = 0;
        for (ArrayList<Integer> update : updates) {
            ArrayList<Integer> sortedUpdate = new ArrayList<>(update);
            sortedUpdate.sort(pageComparator);
            if (sortedUpdate.equals(update)) {
                part1Sum += update.get(update.size() / 2);
            } else {
                part2Sum += sortedUpdate.get(update.size() / 2);
            }
        }
        System.out.println("Part 1: " + part1Sum);
        System.out.println("Part 2: " + part2Sum);
    }

    private final Comparator<Integer> pageComparator = (p1, p2) -> {
        if (rules.stream().anyMatch(arr -> Arrays.equals(arr, new int[]{p1, p2}))) {
            return -1;
        } else if (rules.stream().anyMatch(arr -> Arrays.equals(arr, new int[]{p2, p1}))) {
            return 1;
        }
        return 0;
    };
}
