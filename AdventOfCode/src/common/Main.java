package common;

import AoC2021.Runner2021;
import AoC2023.Runner2023;

import java.io.IOException;
import java.util.HashMap;

public class Main {

    private static final HashMap<Integer, Runner> runners = new HashMap<>() {{
        put(2021, new Runner2021());
        put(2023, new Runner2023());
    }};

    public static void main(String[] args) throws IOException {
        runDay(2021, 5);
        runDay(2023, 9);
    }

    private static void runDay(final int year, final int day) throws IOException {
        runners.get(year).runDay(day);
    }
}
