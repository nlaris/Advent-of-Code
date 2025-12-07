package years.AoC2021;

import common.Day;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Day14 implements Day {

    private final HashMap<String, Character> mappings = new HashMap<>();
    private final HashMap<Character, Long> occurrences = new HashMap<>();
    private HashMap<String, Long> codePairs = new HashMap<>();

    public void run(ArrayList<String> input) {
        final String code = input.get(0);
        occurrences.put(code.charAt(0), 1L);
        for (int c = 1; c < code.length(); c++) {
            codePairs.compute(code.charAt(c - 1) + "" + code.charAt(c), (key, val) -> val == null ? 1 : val + 1);
            occurrences.compute(code.charAt(c), (key, val) -> val == null ? 1 : val + 1);
        }
        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i);
            String[] mapping = line.split(" -> ");
            mappings.put(mapping[0], mapping[1].charAt(0));
        }
        for (int s = 1; s <= 40; s++) {
            final HashMap<String, Long> newCode = new HashMap<>(codePairs);
            for (String pair : mappings.keySet()) {
                if (codePairs.containsKey(pair)) {
                    final char insert = mappings.get(pair);
                    final long pairCount = codePairs.get(pair);
                    newCode.compute(pair.charAt(0) + "" + insert, (key, val) -> val == null ? pairCount : val + pairCount);
                    newCode.compute(insert + "" + pair.charAt(1), (key, val) -> val == null ? pairCount : val + pairCount);
                    newCode.computeIfPresent(pair, (key, val) -> val - pairCount);
                    occurrences.compute(insert, (key, val) -> val == null ? pairCount : val + pairCount);
                }
            }
            codePairs = new HashMap<>(newCode);
            if (s == 10) {
                System.out.println("Part 1: " + (Collections.max(occurrences.values()) - Collections.min(occurrences.values())));
            }
        }
        System.out.println("Part 2: " + (Collections.max(occurrences.values()) - Collections.min(occurrences.values())));
    }
}