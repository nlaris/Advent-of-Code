package years.AoC2025;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import common.Day;

public class Day2 implements Day {
  private Map<Integer, ArrayList<Integer>> divisorsCache = new HashMap<>();
  public void run(BufferedReader reader) throws IOException {
    long part1Sum = 0, part2Sum = 0;
    for (String entry : reader.readLine().split(",")) {
      Long[] ids = Arrays.stream(entry.split("-")).map(Long::parseLong).toArray(Long[]::new);
      long current = ids[0];
      while (current <= ids[1]) {
        String id = String.valueOf(current);
        ArrayList<Integer> divisors = getDivisors(id.length());
        if (divisors.size() > 0) {
          for (int divisor : divisors) {
            String check = id.replace(id.substring(0, divisor), "");
            if (check.length() == 0) {
              part2Sum += current;
              if (divisor == id.length() / 2 && id.length() % 2 == 0) {
                part1Sum += current;
              }
              break;
            }
          }
          current ++;
        } else {
          current = (long) Math.pow(10, id.length());
        }
      }
    }
    System.out.println("Part 1: " + part1Sum);
    System.out.println("Part 2: " + part2Sum);
  }

  private ArrayList<Integer> getDivisors(int number) {
    if (divisorsCache.containsKey(number)) {
      return divisorsCache.get(number);
    }
    ArrayList<Integer> divisors = new ArrayList<>();
    for (int i = number / 2; i >= 1; i--) {
      if (number % i == 0) {
        divisors.add(i);
      }
    }
    divisorsCache.put(number, divisors);
    return divisors;
  }
}
