package common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        final String inputPath = "inputs/" + getYear() + "/day" + day + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
            days.get(day).run(reader);
        }
        System.out.println((System.currentTimeMillis() - time) + " ms\n");
    }

    protected void runAllDays() throws IOException {
        for (Integer i : days.keySet()) {
            runDay(i);
        }
    }
}
