import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

// https://adventofcode.com/2023/day/1
public class Day1 {

    private static final String INPUT_FILE = "day1.txt";

    private static HashMap<String, Integer> digits = new HashMap<>() {{
        put("one", 1);
        put("two", 2);
        put("three", 3);
        put("four", 4);
        put("five", 5);
        put("six", 6);
        put("seven", 7);
        put("eight", 8);
        put("nine", 9);
    }};

    private static int leftWordNum;
    private static int rightWordNum;

    public static void main(String[] args) throws IOException {
        int part1Sum = 0, part2Sum = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line = br.readLine();
            while (line != null) {
                part1Sum += (getPart1CalibrationValue(line));
                part2Sum += (getPart2CalibrationValue(line));
                line = br.readLine();
            }
        }
        System.out.println("Part 1: " + part1Sum);
        System.out.println("Part 2: " + part2Sum);
    }

    private static int getPart1CalibrationValue(String text) {
        int offset = 0, leftDigit = -1, rightDigit = -1;
        while (leftDigit == -1) {
            leftDigit = getDigit(leftDigit, text, offset);
            offset++;
        }
        offset = text.length() - 1;
        while (rightDigit == -1) {
            rightDigit = getDigit(rightDigit, text, offset);
            offset--;
        }
        return leftDigit * 10 + rightDigit;
    }

    private static int getPart2CalibrationValue(String text) {
        int leftWordIndex = getFirstWordIndex(text);
        int rightWordIndex = getLastWordIndex(text);
        int offset = 0, leftDigit = -1, rightDigit = -1;
        while (leftDigit == -1) {
            leftDigit = offset < leftWordIndex ? getDigit(leftDigit, text, offset) : leftWordNum;
            offset++;
        }
        offset = text.length() - 1;
        while (rightDigit == -1) {
            rightDigit = offset > rightWordIndex ? getDigit(rightDigit, text, offset) : rightWordNum;
            offset--;
        }
        return leftDigit * 10 + rightDigit;
    }

    private static int getDigit(int digit, String text, int offset) {
        char c = text.charAt(offset);
        return Character.isDigit(c) ? Character.getNumericValue(c) : digit;
    }

    private static int getLastWordIndex(String text) {
        int latestIndex = -1;
        for(String s : digits.keySet()) {
            int index = text.lastIndexOf(s);
            if (index > latestIndex) {
                latestIndex = index;
                rightWordNum = digits.get(s);
            }
        }
        return latestIndex;
    }

    private static int getFirstWordIndex(String text) {
        int earliestIndex = Integer.MAX_VALUE;
        for(String s : digits.keySet()) {
            int index = text.indexOf(s);
            if (index != -1 && index < earliestIndex) {
                earliestIndex = index;
                leftWordNum = digits.get(s);
            }
        }
        return earliestIndex;
    }
}
