package years.AoC2021;

import common.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Day12 implements Day {

    private final HashMap<String, ArrayList<String>> mappings = new HashMap<>();

    public void run(ArrayList<String> input) throws IOException {
        for (String line : input) {
            final String[] points = line.split("-");
            addMapping(points[0], points[1]);
            addMapping(points[1], points[0]);
        }
        System.out.println("Part 1: " + checkPaths("start", new HashMap<>(), 1));
        System.out.println("Part 2: " + checkPaths("start", new HashMap<>(), 2));
    }

    private int checkPaths(final String p,
                           final HashMap<String, Integer> smallCavesChecked,
                           final int smallCaveLimit) {
        if (p.equals("end")) {
            return 1;
        }
        if (Character.isLowerCase(p.charAt(0))) {
            if (smallCavesChecked.values().stream().anyMatch(x -> x == smallCaveLimit) && smallCavesChecked.containsKey(p)) {
                return 0;
            }
            smallCavesChecked.compute(p, (key, val) -> (val == null) ? 1 : val + 1);
        }
        return mappings.get(p).stream().mapToInt(x -> checkPaths(x, new HashMap<>(smallCavesChecked), smallCaveLimit)).sum();
    }

    private void addMapping(final String from, final String to) {
        if (to.equals("start")) return;
        mappings.computeIfAbsent(from, k -> new ArrayList<>());
        mappings.get(from).add(to);
    }
}
