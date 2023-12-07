import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// https://adventofcode.com/2023/day/3
public class Day3 {

    private static final String INPUT_FILE = "day3.txt";
    private static final ArrayList<String> engine = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line = br.readLine();
            while (line != null) {
                engine.add(line);
                line = br.readLine();
            }
        }
        System.out.println("Part 1: " + getEngineSum());
        System.out.println("Part 2: " + getGearRatioSum());
    }

    private static int getEngineSum() {
        int sum = 0;
        for (int i = 0; i < engine.size(); i++) {
            String row = engine.get(i);
            for (int j = 0; j < row.length(); j++) {
                char c = row.charAt(j);
                if (!Character.isLetterOrDigit(c) && c != '.') {
                    sum += getSurroundingNumbers(i, j).stream().mapToInt(Integer::intValue).sum();
                }
            }
        }
        return sum;
    }

    private static int getGearRatioSum() {
        int sum = 0;
        for (int i = 0; i < engine.size(); i++) {
            String row = engine.get(i);
            for (int j = 0; j < row.length(); j++) {
                char c = row.charAt(j);
                if (c == '*') {
                    sum += getGearRatio(i, j);
                }
            }
        }
        return sum;
    }

    private static int getGearRatio(int i, int j) {
        ArrayList<Integer> surroundingNumbers = getSurroundingNumbers(i, j);
        return surroundingNumbers.size() != 2 ? 0 :
                surroundingNumbers.get(0) * surroundingNumbers.get(1);
    }

    private static ArrayList<Integer> getSurroundingNumbers(int i, int j) {
        final ArrayList<Integer> numbers = new ArrayList<>();
        if (isValidNumber(i - 1, j)) {
            numbers.add(getRestOfNumber(i - 1, j));
        } else {
            if (isValidNumber(i - 1, j - 1)) numbers.add(getRestOfNumber(i - 1, j - 1));
            if (isValidNumber(i - 1, j + 1)) numbers.add(getRestOfNumber(i - 1, j + 1));
        }
        if (isValidNumber(i + 1, j)) {
            numbers.add(getRestOfNumber(i + 1, j));
        } else {
            if (isValidNumber(i + 1, j - 1)) numbers.add(getRestOfNumber(i + 1, j - 1));
            if (isValidNumber(i + 1, j + 1)) numbers.add(getRestOfNumber(i + 1, j + 1));
        }
        if (isValidNumber(i, j - 1)) numbers.add(getRestOfNumber(i, j - 1));
        if (isValidNumber(i, j + 1)) numbers.add(getRestOfNumber(i, j + 1));
        return numbers;
    }

    private static int getRestOfNumber(int i, int j) {
        int start = j, end = j;
        while (isValidNumber(i, start - 1)) start--;
        while (isValidNumber(i, end + 1)) end++;
        return Integer.parseInt(engine.get(i).substring(start, end + 1));
    }

    private static boolean isValidNumber(int i, int j) {
        return i >= 0 && i < engine.size() &&
                j >= 0 && j < engine.get(0).length() &&
                Character.isDigit(engine.get(i).charAt(j));
    }
}
