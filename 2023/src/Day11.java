import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// https://adventofcode.com/2023/day/11
public class Day11 {

    private static final String INPUT_FILE = "inputs/day11.txt";
    private static final ArrayList<int[]> galaxies = new ArrayList<>();

    private static boolean[] galaxyColumns;
    private static boolean[] galaxyRows;

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line = br.readLine();
            galaxyColumns = new boolean[line.length()];
            galaxyRows = new boolean[line.length()];
            int row = 0;
            while (line != null) {
                int galaxyIndex = line.indexOf('#', 0);
                while (galaxyIndex >= 0) {
                    galaxies.add(new int[] {galaxyIndex, row});
                    galaxyColumns[galaxyIndex] = true;
                    galaxyRows[row] = true;
                    galaxyIndex = line.indexOf('#', galaxyIndex + 1);
                }
                line = br.readLine();
                row++;
            }
            System.out.println("Part 1: " + getLengthSum(2));
            System.out.println("Part 2: " + getLengthSum(1000000));
        }
    }

    private static long getLengthSum(int expansionRate) {
        long sum = 0;
        for (int i = 0; i < galaxies.size() - 1; i++) {
            int[] galaxyA = galaxies.get(i);
            for (int j = i + 1; j < galaxies.size(); j++) {
                int[] galaxyB = galaxies.get(j);
                int minX = Math.min(galaxyA[0], galaxyB[0]);
                int maxX = Math.max(galaxyA[0], galaxyB[0]);
                sum += ((maxX - minX) + (galaxyB[1] - galaxyA[1]));
                for (int col = minX + 1; col < maxX; col++) {
                    if (!galaxyColumns[col]) sum += expansionRate - 1;
                }
                for (int row = galaxyA[1] + 1; row < galaxyB[1]; row++) {
                    if (!galaxyRows[row]) sum += expansionRate - 1;
                }
            }
        }
        return sum;
    }
}