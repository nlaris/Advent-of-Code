package common;

import AoC2021.Day4;
import common.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Runner {

    protected HashMap<Integer, Day> days = new HashMap<>();

    protected abstract int getYear();

    protected void runDay(final int day) throws IOException {
        if (!days.containsKey(day)) {
            System.out.println(getYear() + " Day " + day + " hasn't been added yet.\n");
            return;
        }
        System.out.println(getYear() + " Day " + day);
        long time = System.currentTimeMillis();
        days.get(day).run("inputs/" + getYear() + "/day" + day + ".txt");
        System.out.println((System.currentTimeMillis() - time) + " ms\n");
    }
}
