import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

// https://adventofcode.com/2023/day/2
public class Day2 {

    private static final String INPUT_FILE = "day2.txt";

    private static HashMap<String, Integer> maxColorCount = new HashMap<>() {{
        put("red", 12);
        put("green", 13);
        put("blue", 14);
    }};

    public static void main(String[] args) throws IOException {
        int part1Sum = 0, part2Sum = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line = br.readLine();
            while (line != null) {
                part1Sum += getPart1GameSum(line);
                part2Sum += getPart2GameSum(line);
                line = br.readLine();
            }
        }
        System.out.println("Part 1: " +part1Sum);
        System.out.println("Part 2: " +part2Sum);
    }

    private static int getPart1GameSum(String line) {
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

    private static int getPart2GameSum(String line) {
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
