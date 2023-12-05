import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day5 {

    private static final String INPUT_FILE = "day5.txt";

    private static List<Long> seeds = new ArrayList<>();

    private static ArrayList<RangeMapping> seedToSoil = new ArrayList<>();
    private static ArrayList<RangeMapping> soilToFertilizer = new ArrayList<>();
    private static ArrayList<RangeMapping> fertilizerToWater = new ArrayList<>();
    private static ArrayList<RangeMapping> waterToLight = new ArrayList<>();
    private static ArrayList<RangeMapping> lightToTemperature = new ArrayList<>();
    private static ArrayList<RangeMapping> temperatureToHumidity = new ArrayList<>();
    private static ArrayList<RangeMapping> humidityToLocation = new ArrayList<>();

    private static Long locationPart1 = null;
    private static Long locationPart2 = null;

    private static ArrayList<Long> stack = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line = br.readLine();
            seeds = Arrays.stream(line.split(":")[1].trim().split(" ")).mapToLong(Long::parseLong).boxed().toList();
            br.readLine(); br.readLine(); line = br.readLine();
            ArrayList<RangeMapping> currentMap = seedToSoil;
            while (line != null) {
                if (line.isEmpty()) {
                    currentMap = getNextMapping(currentMap, false);
                    br.readLine();
                } else {
                    RangeMapping rangeMapping = new RangeMapping(Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray());
                    currentMap.add(rangeMapping);
                }
                line = br.readLine();
            }
        }
        long location = 0;
        while (locationPart1 == null || locationPart2 == null) {
            findSeedNumbers(location, humidityToLocation, location);
            location++;
        }
        System.out.println("Part 1: " + locationPart1);
        System.out.println("Part 2: " + locationPart2);
    }

    private static void findSeedNumbers(long destVal, ArrayList<RangeMapping> mapping, long location) {
        ArrayList<RangeMapping> nextMapping = getNextMapping(mapping, true);
        if (nextMapping == null) {
            if (locationPart1 == null && isValidSeed(getSourceVal(destVal, mapping), true)) locationPart1 = location;
            if (locationPart2 == null && isValidSeed(getSourceVal(destVal, mapping), false)) locationPart2 = location;
            return;
        }
        findSeedNumbers(getSourceVal(destVal, mapping), nextMapping, location);
    }

    private static boolean isValidSeed(long val, boolean part1) {
        if (part1) return seeds.contains(val);
        for (int i = 0; i < seeds.size(); i+=2) {
            if (val >= seeds.get(i) && val < (seeds.get(i) + seeds.get(i+1))) return true;
        }
        return false;
    }

    private static long getSourceVal(long val, ArrayList<RangeMapping> mapping) {
        for (RangeMapping range : mapping) {
            if (range.hasValue(val)) return range.getSourceVal(val);
        }
        return val;
    }

    private static ArrayList<RangeMapping> getNextMapping(ArrayList<RangeMapping> currentMapping, boolean reverse) {
        if (currentMapping.equals(seedToSoil)) return reverse ? null : soilToFertilizer;
        if (currentMapping.equals(soilToFertilizer)) return reverse ? seedToSoil : fertilizerToWater;
        if (currentMapping.equals(fertilizerToWater)) return reverse ? soilToFertilizer : waterToLight;
        if (currentMapping.equals(waterToLight)) return reverse ? fertilizerToWater : lightToTemperature;
        if (currentMapping.equals(lightToTemperature)) return reverse ? waterToLight : temperatureToHumidity;
        if (currentMapping.equals(temperatureToHumidity)) return reverse ? lightToTemperature : humidityToLocation;
        if (currentMapping.equals(humidityToLocation)) return reverse ? temperatureToHumidity : null;
        return null;
    }
}

class RangeMapping {
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
