package years.AoC2025;

import java.util.ArrayList;

import common.Day;

public class Day4 implements Day {
  private int part1Sum = 0, part2Sum = 0;
  @Override
  public void run(ArrayList<String> input) {
    for (int i = 0; i < input.size(); i++) {
      for (int j = 0; j < input.get(i).length(); j++) {
        checkSpot(input, i, j, false);
      }
    }
    for (int i = 0; i < input.size(); i++) {
      for (int j = 0; j < input.get(i).length(); j++) {
        checkSpot(input, i, j, true);  
      }
    }
    System.out.println("Part 1: " + part1Sum);
    System.out.println("Part 2: " + part2Sum);
  }

  private ArrayList<int[]> getNeighbors(ArrayList<String> lines, int i, int j) {
    ArrayList<int[]> neighbors = new ArrayList<>();
    for (int r = Math.max(0, i - 1); r <= Math.min(lines.size() - 1, i + 1); r++) {
      for (int c = Math.max(0, j - 1); c <= Math.min(lines.get(r).length() - 1, j + 1); c++) {
        if (lines.get(r).charAt(c) == '@' && (r != i || c != j)) {
          neighbors.add(new int[]{r, c});
        }
      }
    }
    return neighbors;
  }

  private void checkSpot(ArrayList<String> lines, int i, int j, boolean part2) {
    if (lines.get(i).charAt(j) == '@') {
      ArrayList<int[]> neighbors = getNeighbors(lines, i, j);
      if (neighbors.size() <= 3) {
        if (part2) {
          part2Sum++;
          lines.set(i, lines.get(i).substring(0, j) + '.' + lines.get(i).substring(j + 1));
          for (int[] neighbor : neighbors) {
            checkSpot(lines, neighbor[0], neighbor[1], true);
          }
        } else {
          part1Sum++;
        }
      }
    }
  }
}
