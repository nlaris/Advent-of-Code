package years.AoC2025;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import common.Day;

public class Day5 implements Day{
  public void run(BufferedReader reader) throws IOException {
    String line;
    ArrayList<long[]> ranges = new ArrayList<long[]>();
    long part1Sum = 0, part2Sum = 0;
    while ((line = reader.readLine()) != null) {
      if (line.contains("-")) {
        long[] range = new long[] { Long.parseLong(line.split("-")[0]), Long.parseLong(line.split("-")[1]) };
        for (int i = 0; i < ranges.size(); i++) {
          if (ranges.get(i)[0] <= range[1] && range[0] <= ranges.get(i)[1]) {
            range = new long[] { Math.min(range[0], ranges.get(i)[0]), Math.max(range[1], ranges.get(i)[1]) };
            ranges.remove(i);
            i--;
          }
        }
        ranges.add(range);
      } else if (!line.isEmpty()) {
        long value = Long.parseLong(line);
        for (long[] range : ranges) {
          if (value >= range[0] && value <= range[1]) {
            part1Sum++;
            break;
          }
        }
      }
    }
    for (long[] range : ranges) {
      part2Sum += range[1] - range[0] + 1;
    }
    System.out.println("Part 1: " + part1Sum);
    System.out.println("Part 2: " + part2Sum);
  }
}
