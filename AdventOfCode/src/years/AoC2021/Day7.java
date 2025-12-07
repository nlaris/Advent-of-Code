package years.AoC2021;

import common.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day7 implements Day {

    private List<Integer> inputs = new ArrayList<>();

    public void run(ArrayList<String> input) {
        inputs = Arrays.stream(input.get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        Collections.sort(inputs);
        int medianIndex = inputs.size() / 2;
        System.out.println("Part 1: " + getPart1PositionScore(inputs.get(medianIndex)));

        float averageInput = inputs.stream().reduce(0, Integer::sum).floatValue() / inputs.size();
        int aveFloorScore = getPart2PositionScore((int)Math.floor(averageInput));
        int aveCeilScore = getPart2PositionScore((int)Math.ceil(averageInput));
        System.out.println("Part 2: " + (Math.min(aveCeilScore, aveFloorScore)));
    }

    private int getPart1PositionScore(final int position) {
        return inputs.stream().reduce(0, (total, i) -> total + Math.abs(position - i));
    }

    private int getPart2PositionScore(final int position) {
        return inputs.stream().reduce(0, (total, i) -> total + getDistanceFuel(Math.abs(position - i)));
    }

    private int getDistanceFuel(final int n) {
        return (n * (n+1) / 2);
    }
}
