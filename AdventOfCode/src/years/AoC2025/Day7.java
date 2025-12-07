package years.AoC2025;

import java.util.ArrayList;
import java.util.HashMap;

import common.Day;

public class Day7 implements Day {

  private HashMap<String, Long> timelines = new HashMap<>();
  private HashMap<String, Long> collisions = new HashMap<>();

  @Override
  public void run(ArrayList<String> input) {
    int start = input.get(0).indexOf('S');
    System.out.println("Part 1: " + getNumCollisions(input, start, 1));
    System.out.println("Part 2: " + getNumTimelines(input, start, 1));
  }

  private long getNumCollisions(ArrayList<String> input, int beamIndex, int row) {
    String key = beamIndex + "|" + row;
    if (row == input.size() - 1 || collisions.containsKey(key)) {
      return 0;
    }
    if (input.get(row).charAt(beamIndex) == '^') {
      collisions.put(key, 1 + getNumCollisions(input, beamIndex - 1, row + 1) + getNumCollisions(input, beamIndex + 1, row + 1));
    } else {
      collisions.put(key, getNumCollisions(input, beamIndex, row + 1));
    }
    return collisions.get(key);
  }

  private long getNumTimelines(ArrayList<String> input, int beamIndex, int row) {
    if (row == input.size() - 1) {
      return 1;
    }
    String key = beamIndex + "|" + row;
    if (!timelines.containsKey(key)) {
      if (input.get(row).charAt(beamIndex) == '^') {
        timelines.put(key, getNumTimelines(input, beamIndex - 1, row + 1) + getNumTimelines(input, beamIndex + 1, row + 1));
      } else {
        timelines.put(key, getNumTimelines(input, beamIndex, row + 1));
      }
    }
    return timelines.get(key);
  }
}
