package years.AoC2025;

import java.io.BufferedReader;
import java.io.IOException;

import common.Day;

public class Day3 implements Day {

  @Override
  public void run(BufferedReader reader) throws IOException {
    String line;
    long part1Sum = 0, part2Sum = 0;
    while ((line = reader.readLine()) != null) {
      part1Sum += getJoltage(line, 0, 2, "");
      part2Sum += getJoltage(line, 0, 12, "");
    }
    System.out.println("Part 1: " + part1Sum);
    System.out.println("Part 2: " + part2Sum);
  }

  private static long getJoltage(String bank, int start, int endOffset, String joltage) {
    if (endOffset == 0) {
      return Long.parseLong(joltage);
    }
    int index = -1, value = 10;
    while (index == -1) {
      index = bank.substring(start, bank.length() - endOffset + 1).indexOf(String.valueOf(--value));
    }
    joltage += value;
    return getJoltage(bank, start + index + 1, endOffset - 1, joltage);
  }
}
