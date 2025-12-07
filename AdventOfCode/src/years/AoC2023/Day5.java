package years.AoC2023;

import common.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 implements Day {

    private List<Long> seeds = new ArrayList<>();
    private final ArrayList<ArrayList<RangeMapping>> mappings = new ArrayList<>();

    private Long locationPart1 = null;
    private Long locationPart2 = null;
    private int mappingIndex = 0;

    public void run(ArrayList<String> input) {
        seeds = Arrays.stream(input.get(0).split(":")[1].trim().split(" ")).mapToLong(Long::parseLong).boxed().toList();
        ArrayList<RangeMapping> currentMap = new ArrayList<>();
        for (int i = 3; i < input.size(); i++) {
            String line = input.get(i);
            if (line.isEmpty()) {
                mappings.add(currentMap);
                currentMap = new ArrayList<>();
                i++;
            } else {
                RangeMapping rangeMapping = new RangeMapping(Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray());
                currentMap.add(rangeMapping);
            }
        }
        mappings.add(currentMap);
        long location = 0;
        while (locationPart1 == null || locationPart2 == null) {
            mappingIndex = mappings.size() - 1;
            findSeedNumbers(location, mappings.get(mappingIndex), location);
            location++;
        }
        System.out.println("Part 1: " + locationPart1);
        System.out.println("Part 2: " + locationPart2);
    }

    private void findSeedNumbers(long destVal, ArrayList<RangeMapping> mapping, long location) {
        long sourceVal = getSourceVal(destVal, mapping);
        if (mappingIndex == 0) {
            if (locationPart1 == null && isValidSeed(sourceVal, true)) locationPart1 = location;
            if (locationPart2 == null && isValidSeed(sourceVal, false)) locationPart2 = location;
            return;
        }
        findSeedNumbers(sourceVal, mappings.get(--mappingIndex), location);
    }

    private boolean isValidSeed(long val, boolean part1) {
        if (part1) return seeds.contains(val);
        for (int i = 0; i < seeds.size(); i += 2) {
            if (val >= seeds.get(i) && val < (seeds.get(i) + seeds.get(i + 1))) return true;
        }
        return false;
    }

    private long getSourceVal(long val, ArrayList<RangeMapping> mapping) {
        for (RangeMapping range : mapping) {
            if (range.hasValue(val)) return range.getSourceVal(val);
        }
        return val;
    }

    private static class RangeMapping {
        public long destRangeStart;
        public long sourceRangeStart;
        public long rangeLength;

        public RangeMapping(long[] rangeInfo) {
            this.destRangeStart = rangeInfo[0];
            this.sourceRangeStart = rangeInfo[1];
            this.rangeLength = rangeInfo[2];
        }

        public boolean hasValue(long val) {
            return val >= destRangeStart && val < destRangeStart + rangeLength;
        }

        public long getSourceVal(long destVal) {
            return (sourceRangeStart - destRangeStart) + destVal;
        }
    }
}
