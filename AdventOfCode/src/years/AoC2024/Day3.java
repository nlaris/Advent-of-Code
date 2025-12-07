package years.AoC2024;

import common.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 implements Day {

    public void run(ArrayList<String> input) throws IOException {
        int part1Sum = 0, part2Sum = 0;
        boolean startEnabled = true;
        for (String line : input) {
            part1Sum += getSum(line);
            line = (startEnabled ? "do()" : "don't()") + line;
            startEnabled = line.lastIndexOf("don't()") < line.lastIndexOf("do()");
            part2Sum += getSum((line + "do()").replaceAll("don't\\(\\)(.*?)do\\(\\)", ":)"));
        }
        System.out.println("Part 1: " + part1Sum);
        System.out.println("Part 2: " + part2Sum);
    }

    private int getSum(String line) {
        int sum = 0;
        Matcher matcher = Pattern.compile("mul\\((\\d+),(\\d+)\\)").matcher(line);
        while (matcher.find()) {
            sum += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
        }
        return sum;
    }
}
