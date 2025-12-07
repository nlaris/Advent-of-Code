package years.AoC2025;

import java.util.ArrayList;

import common.Day;

public class Day6 implements Day{

  @Override
  public void run(ArrayList<String> input) {
    ArrayList<ArrayList<String>> equations = new ArrayList<>();
    ArrayList<Character> operators = new ArrayList<>();
    ArrayList<Integer> spaces = new ArrayList<>();
    long part1Sum = 0, part2Sum = 0;
    for (String line : input) {
      if (line.contains("*")) {
        for (String value : line.trim().split("\\s+")) {
          operators.add(value.charAt(0));
          equations.add(new ArrayList<>());
        }
      } else {
        // Iteratively figure out where the "divider" is between numbers
        spaces = getSpaceIndices(line, spaces);
      }
    }
    for (String line : input) {
      if (line.contains("*")) break;
      // Do another pass over each row to append each number to our equations
      for (int i = 0; i <= spaces.size(); i++) {
        int firstSpace = i == 0 ? 0 : spaces.get(i - 1) + 1;
        int secondSpace = i == spaces.size() ? line.length() : spaces.get(i);
        equations.get(i).add(line.substring(firstSpace, secondSpace));
      }
    }
    for (int i = 0; i < equations.size(); i++) {
      ArrayList<String> equation = equations.get(i);
      boolean multiply = operators.get(i) == '*';
      long part1Total = multiply ? 1 : 0, part2Total = part1Total;
      // Just iterate through each number for part 1
      for (String number : equations.get(i)) {
        long value = Long.parseLong(number.replace(" ", ""));
        part1Total = multiply ? part1Total * value : part1Total + value;
      }
      // Go column by column for part 2
      for (int j = 0; j < equation.get(0).length(); j++) {
        String value = "";
        for (String number : equation) {
          value = (value + number.charAt(j)).replace(" ", "");
        }
        part2Total = multiply ? part2Total * Long.parseLong(value) : part2Total + Long.parseLong(value);
      }
      part1Sum += part1Total;
      part2Sum += part2Total;
    }
    System.out.println("Part 1: " + part1Sum);
    System.out.println("Part 2: " + part2Sum);
  }

  private ArrayList<Integer> getSpaceIndices(String line, ArrayList<Integer> existingSpaces) {
    ArrayList<Integer> spaces = new ArrayList<>();
    if (existingSpaces.isEmpty()) {
      for (int i = 0; i < line.length(); i++) {
        if (line.charAt(i) == (' ')) {
          spaces.add(i);
        }
      }
    } else {
      for (Integer i : existingSpaces) {
        if (line.charAt(i) == (' ')) {
          spaces.add(i);
        }
      }
    }
    return spaces;
  }
}
