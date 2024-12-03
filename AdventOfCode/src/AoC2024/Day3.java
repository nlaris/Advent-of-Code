package AoC2024;

import common.Day;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 implements Day {
    @Override
    public void run(BufferedReader reader) throws IOException {
        int part1Sum = 0, part2Sum = 0;
        String line;
        boolean startEnabled = true;
        while ((line = reader.readLine()) != null) {
            Matcher matcher = Pattern.compile("mul\\((\\d+),(\\d+)\\)").matcher(line);
            while(matcher.find()) {
                part1Sum += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
            }
            line = startEnabled ? "do()" + line : "don't()" + line;
            startEnabled = line.lastIndexOf("don't()") < line.lastIndexOf("do()");
            line += "do()";
            line = line.replaceAll("don't\\(\\)(.*?)do\\(\\)", "do\\(\\)");
            matcher = Pattern.compile("mul\\((\\d+),(\\d+)\\)").matcher(line);
            while(matcher.find()) {
                part2Sum += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
            }
        }
        System.out.println("Part 1: " + part1Sum);
        System.out.println("Part 2: " + part2Sum);
    }
}
