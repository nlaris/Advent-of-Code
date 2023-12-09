import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://adventofcode.com/2023/day/9
public class Day9 {

    private static final String INPUT_FILE = "inputs/day9.txt";

    private static int part1Sum = 0;
    private static int part2Sum = 0;

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line = br.readLine();
            while (line != null) {
                getExtrapolatedValues(Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).boxed().toList());
                line = br.readLine();
            }
            System.out.println("Part 1: " + part1Sum);
            System.out.println("Part 2: " + part2Sum);
        }
    }

    private static void getExtrapolatedValues(List<Integer> history) {
        ArrayList<ArrayList<Integer>> sequences = new ArrayList<>();
        sequences.add(new ArrayList<>(history));
        while(sequences.get(0).stream().anyMatch(x -> x != 0)) {
            ArrayList<Integer> diffs = new ArrayList<>();
            for (int i = 0; i < sequences.get(0).size() - 1; i++) {
                diffs.add(sequences.get(0).get(i + 1) - sequences.get(0).get(i));
            }
            sequences.add(0, diffs);
        }
        sequences.get(0).add(0);
        for (int i = 1; i < sequences.size(); i++) {
            final int lastIndex = sequences.get(i).size() - 1;
            sequences.get(i).add(sequences.get(i).get(lastIndex) + sequences.get(i - 1).get(lastIndex));
            sequences.get(i).add(0, sequences.get(i).get(0) - sequences.get(i - 1).get(0));
        }
        part1Sum += sequences.get(sequences.size() - 1).get(sequences.get(sequences.size() - 1).size() - 1);
        part2Sum += sequences.get(sequences.size() - 1).get(0);
    }
}
