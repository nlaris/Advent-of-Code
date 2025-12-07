package years.AoC2025;

import java.io.IOException;
import java.util.ArrayList;

import common.Day;

public class Day3 implements Day {
  @Override
  public void run(ArrayList<String> input) throws IOException {
    long part1Sum = 0, part2Sum = 0;
    for (String line : input) {
      part1Sum += getJoltage(line, 2, "");
      part2Sum += getJoltage(line, 12, "");
    }
    System.out.println("Part 1: " + part1Sum);
    System.out.println("Part 2: " + part2Sum);
  }

  private static long getJoltage(String bank, int endOffset, String joltage) {
    if (endOffset == 0) {
      return Long.parseLong(joltage);
    }
    int index = -1, value = 10;
    while (index == -1) {
      index = bank.substring(0, bank.length() - endOffset + 1).indexOf(String.valueOf(--value));
    }
    return getJoltage(bank.substring(index + 1), endOffset - 1, joltage + value);
  }
}
