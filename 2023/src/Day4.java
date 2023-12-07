import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

// https://adventofcode.com/2023/day/4
public class Day4 {

    private static final String INPUT_FILE = "day4.txt";
    private static HashMap<Integer, Integer> ticketCount = new HashMap<>();

    public static void main(String[] args) throws IOException {
        int pointSum = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line = br.readLine();
            while (line != null) {
                pointSum += calculatePoints(line);
                line = br.readLine();
            }
        }
        System.out.println("Part 1: " + pointSum);
        System.out.println("Part 2: " + ticketCount.values().stream().mapToInt(Integer::intValue).sum());
    }

    private static int calculatePoints(String line) {
        String[] ticket = line.split("\\|");
        String[] ticketInfo = ticket[0].split(":");
        List<Integer> winningNumbers = getNumbers(Arrays.stream(ticketInfo[1].split(" ")));
        List<Integer> myWinningNumbers = getNumbers(Arrays.stream(ticket[1].split(" "))).stream().filter(winningNumbers::contains).toList();
        int gameNumber = Integer.parseInt(ticketInfo[0].replace("Card", "").trim());
        ticketCount.compute(gameNumber, (k, v) -> v == null ? 1 : v + 1);
        for (int i = 1; i <= myWinningNumbers.size(); i++) {
            ticketCount.compute(gameNumber + i, (k, v) -> v == null ? ticketCount.get(gameNumber) : v + ticketCount.get(gameNumber));
        }
        return myWinningNumbers.isEmpty() ? 0 : (int)Math.pow(2, myWinningNumbers.size() - 1);
    }

    private static ArrayList<Integer> getNumbers(Stream<String> stream) {
        final ArrayList<Integer> numbers = new ArrayList<>();
        stream.forEach(s -> {
            try {
                int d = Integer.parseInt(s);
                numbers.add(d);
            } catch (NumberFormatException ignored) {}
        });
        return numbers;
    }
}
