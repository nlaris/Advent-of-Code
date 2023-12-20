package AoC2023;

import common.Runner;

public class Runner2023 extends Runner {

    @Override
    public void setDays() {
        days.put(1, new Day1());
        days.put(2, new Day2());
        days.put(3, new Day3());
        days.put(4, new Day4());
        days.put(5, new Day5());
        days.put(6, new Day6());
        days.put(7, new Day7());
        days.put(8, new Day8());
        days.put(9, new Day9());
        days.put(10, new Day10());
        days.put(11, new Day11());
        days.put(12, new Day12());
    }

    @Override
    public int getYear() {
        return 2023;
    }
}
