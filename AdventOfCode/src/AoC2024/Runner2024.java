package AoC2024;

import common.Runner;

public class Runner2024 extends Runner {

    public Runner2024() {
        days.put(1, new Day1());
    }

    @Override
    protected int getYear() {
        return 2024;
    }
}
