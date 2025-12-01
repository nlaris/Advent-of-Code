package years.AoC2025;

import java.io.BufferedReader;
import java.io.IOException;

import common.Day;

public class Day1 implements Day {
  public void run(BufferedReader reader) throws IOException {
    String line;
    int currentNum = 50, part1Sum = 0, part2Sum = 0;
    while ((line = reader.readLine()) != null) {
      int rotation = Integer.parseInt(line.substring(1));
      boolean left = line.charAt(0) == 'L';
      currentNum += left ? -rotation : rotation;
      if (left) {
        part2Sum += (currentNum - 100) / -100;
        if (currentNum + rotation == 0 && rotation % 100 != 0) {
          part2Sum --;
        }
      } else {
        part2Sum += (currentNum + 100) / 100 - 1;
      }
      currentNum = ((currentNum % 100) + 100) % 100;
      if (currentNum == 0) {
        part1Sum ++;
      }
    }
    System.out.println("Part 1: " + part1Sum);
    System.out.println("Part 2: " + part2Sum);
  }
}