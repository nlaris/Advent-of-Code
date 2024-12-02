package AoC2024;

import common.Runner;

public class Runner2024 extends Runner {

    public Runner2024() {
        days.put(1, new Day1());
        days.put(2, new Day2());
    }

    @Override
    protected int getYear() {
        return 2024;
    }
}
