package AoC2021;

import common.Runner;

public class Runner2021 extends Runner {

    public Runner2021() {
        days.put(4, new Day4());
        days.put(5, new Day5());
        days.put(6, new Day6());
        days.put(7, new Day7());
        days.put(8, new Day8());
        days.put(9, new Day9());
        days.put(10, new Day10());
        days.put(11, new Day11());
        days.put(12, new Day12());
        days.put(13, new Day13());
        days.put(14, new Day14());
        days.put(15, new Day15());
        days.put(16, new Day16());
    }

    @Override
    protected int getYear() {
        return 2021;
    }
}
