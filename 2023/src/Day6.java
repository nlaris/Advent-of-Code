import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// https://adventofcode.com/2023/day/6
public class Day6 {

    private static final String INPUT_FILE = "day6.txt";

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            final String timeLine = br.readLine().replaceAll("[^-?0-9]+", " ").trim();
            final String distanceLine = br.readLine().replaceAll("[^-?0-9]+", " ").trim();
            List<Integer> part1Times = Arrays.stream(timeLine.split(" ")).mapToInt(Integer::parseInt).boxed().toList();
            List<Integer> part1Distances = Arrays.stream(distanceLine.split(" ")).mapToInt(Integer::parseInt).boxed().toList();
            long part2Time = Long.parseLong(timeLine.replaceAll(" ", ""));
            long part2Distance = Long.parseLong(distanceLine.replaceAll(" ", ""));
            int part1Product = 1;
            for (int i = 0; i < part1Times.size(); i++) {
                part1Product *= getTotalSolutions(part1Times.get(i), part1Distances.get(i));
            }
            System.out.println("Part 1: " + part1Product);
            System.out.println("Part 2: " + getTotalSolutions(part2Time, part2Distance));
        }
    }

    private static long getTotalSolutions(long time, long record) {
        long seconds = 1;
        while (seconds < time) {
            if (seconds * (time - seconds) > record) return time - (2 * seconds) + 1;
            seconds++;
        }
        return 0;
    }
}
