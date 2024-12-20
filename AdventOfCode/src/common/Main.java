package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        runLatestDay();
    }

    private static void runDay(final int year, final int day) {
        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/" + year + "/day" + day + ".txt"))) {
            Class<?> dayClass = Class.forName("years.AoC" + year + ".Day" + day);
            Object dayInstance = dayClass.getDeclaredConstructor().newInstance();
            long time = System.currentTimeMillis();
            System.out.println(year + " Day " + day);
            dayClass.getMethod("run", BufferedReader.class).invoke(dayInstance, reader);
            System.out.println((System.currentTimeMillis() - time) + " ms\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void runLatestDay() {
        File[] years = new File("src/years").listFiles();
        if (years != null) {
            File latestYearDirectory = years[years.length - 1];
            int year = Integer.parseInt(latestYearDirectory.getName().replace("AoC", ""));
            File[] days = new File(latestYearDirectory.getAbsolutePath()).listFiles();
            if (days != null) {
                int day = Arrays.stream(days)
                        .mapToInt(file -> Integer.parseInt(file.getName().replaceAll("\\D", "")))
                        .max()
                        .orElse(-1);
                runDay(year, day);
            }
        }
    }
}
