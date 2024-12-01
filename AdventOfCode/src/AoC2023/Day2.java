package AoC2023;

import common.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

// https://adventofcode.com/2023/day/2
public class Day2 implements Day {

    private final HashMap<String, Integer> maxColorCount = new HashMap<>() {{
        put("red", 12);
        put("green", 13);
        put("blue", 14);
    }};

    @Override
    public void run(BufferedReader reader) throws IOException {
        int part1Sum = 0, part2Sum = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            part1Sum += getPart1GameSum(line);
            part2Sum += getPart2GameSum(line);
        }
        System.out.println("Part 1: " + part1Sum);
        System.out.println("Part 2: " + part2Sum);
    }

    private int getPart1GameSum(String line) {
        String[] gameInfo = line.split(":");
        int gameId = Integer.parseInt(gameInfo[0].split(" ")[1]);
        String game = gameInfo[1];
        String[] rounds = game.split(";");
        for (String round : rounds) {
            String[] groups = round.split(",");
            for (String group : groups) {
                String[] groupInfo = group.split(" ");
                int num = Integer.parseInt(groupInfo[1]);
                String color = groupInfo[2];
                if (num > maxColorCount.get(color)) {
                    return 0;
                }
            }
        }
        return gameId;
    }

    private int getPart2GameSum(String line) {
        String game = line.split(":")[1];
        String[] rounds = game.split(";");
        final HashMap<String, Integer> colorCount = new HashMap<>() {{
            put("red", 0);
            put("green", 0);
            put("blue", 0);
        }};
        for (String round : rounds) {
            String[] groups = round.split(",");
            for (String group : groups) {
                String[] groupInfo = group.split(" ");
                int num = Integer.parseInt(groupInfo[1]);
                String color = groupInfo[2];
                if (num > colorCount.get(color)) {
                    colorCount.put(color, num);
                }
            }
        }
        return colorCount.get("red") * colorCount.get("blue") * colorCount.get("green");
    }
}
