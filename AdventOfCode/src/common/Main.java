package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        runLatestDay();
    }

    private static void runDay(final int year, final int day) {
        try (BufferedReader reader = new BufferedReader(new FileReader("inputs/" + year + "/day" + day + ".txt"))) {
            Class<?> dayClass = Class.forName("years.AoC" + year + ".Day" + day);
            Object dayInstance = dayClass.getDeclaredConstructor().newInstance();
            System.out.println(year + " Day " + day);
            ArrayList<String> input = Utils.getInput(reader);
            long time = System.currentTimeMillis();
            dayClass.getMethod("run", ArrayList.class).invoke(dayInstance, input);
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

    private static void runAllDays() {
        File[] years = new File("src/years").listFiles();
        if (years != null) {
            // Sort years to run them in order
            Arrays.sort(years, (a, b) -> {
                int yearA = Integer.parseInt(a.getName().replace("AoC", ""));
                int yearB = Integer.parseInt(b.getName().replace("AoC", ""));
                return Integer.compare(yearA, yearB);
            });
            
            for (File yearDirectory : years) {
                int year = Integer.parseInt(yearDirectory.getName().replace("AoC", ""));
                File[] days = yearDirectory.listFiles();
                if (days != null) {
                    // Sort days to run them in order
                    Arrays.sort(days, (a, b) -> {
                        int dayA = Integer.parseInt(a.getName().replaceAll("\\D", ""));
                        int dayB = Integer.parseInt(b.getName().replaceAll("\\D", ""));
                        return Integer.compare(dayA, dayB);
                    });
                    
                    for (File dayFile : days) {
                        if (dayFile.getName().startsWith("Day") && dayFile.getName().endsWith(".java")) {
                            int day = Integer.parseInt(dayFile.getName().replaceAll("\\D", ""));
                            runDay(year, day);
                        }
                    }
                }
            }
        }
    }
}
