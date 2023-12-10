import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// https://adventofcode.com/2023/day/10
public class Day10 {

    private static final String INPUT_FILE = "inputs/day10.txt";
    private static final ArrayList<String> map = new ArrayList<>();
    private static final ArrayList<String> path = new ArrayList<>();
    private static final HashMap<Character, int[][]> connectionCoords = new HashMap<>() {{
        put('-', new int[][]{{-1,0}, {1,0}});
        put('|', new int[][]{{0,1}, {0,-1}});
        put('7', new int[][]{{-1,0}, {0,1}});
        put('J', new int[][]{{-1,0}, {0,-1}});
        put('L', new int[][]{{1,0}, {0,-1}});
        put('F', new int[][]{{1,0}, {0,1}});
    }};

    private static int[] currentCoords;
    private static int furthestSpot;

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line = br.readLine();
            int row = 0;
            while (line != null) {
                if (line.contains("S")) currentCoords = new int[] {line.indexOf('S'), row};
                map.add(line);
                row++;
                line = br.readLine();
            }
            map.set(currentCoords[1], map.get(currentCoords[1]).replace('S', getStartingChar()));
            findPath();
            System.out.println("Part 1: " + furthestSpot);
            System.out.println("Part 2: " + getEnclosedLocations());
        }
    }

    private static void findPath() {
        String prevCoords = "";
        char currentChar = getStartingChar();
        int steps = 0;
        while (!path.contains(translateCoord(currentCoords))) {
            path.add(0, translateCoord(currentCoords));
            currentCoords = getNextCoords(currentChar, prevCoords);
            prevCoords = path.get(0);
            currentChar = map.get(currentCoords[1]).charAt(currentCoords[0]);
            steps++;
        }
        furthestSpot = steps / 2;
    }

    private static int getEnclosedLocations() {
        int sum = 0;
        for (int row = 1; row < map.size() - 1; row++) {
            for (int col = 1; col < map.get(0).length() - 1; col++) {
                if (!path.contains(translateCoord(new int[]{col, row}))) {
                    int totalEdges = 0;
                    Character neededCorner = null;
                    for (int r = 0; r < row; r++) {
                        if (path.contains(translateCoord(new int[]{col, r}))) {
                            char c = map.get(r).charAt(col);
                            if (c == '-') {
                                totalEdges++;
                            } else if (List.of('F', 'L', 'J', '7').contains(c)) {
                                if (neededCorner != null) {
                                    totalEdges += c == neededCorner ? 1 : 2;
                                    neededCorner = null;
                                } else {
                                    neededCorner = c == '7' ? 'L' : 'J';
                                }
                            }
                        }
                    }
                    sum += (totalEdges % 2);
                }
            }
        }
        return sum;
    }

    private static int[] getNextCoords(char currentChar, String prevCoords) {
        int[][] conns = connectionCoords.get(currentChar);
        int[] nextCoords = new int[] {currentCoords[0] + conns[0][0], currentCoords[1] + conns[0][1]};
        if (translateCoord(nextCoords).equals(prevCoords)) {
            nextCoords = new int[] {currentCoords[0] + conns[1][0], currentCoords[1] + conns[1][1]};
        }
        return nextCoords;
    }

    private static char getStartingChar() {
        int x = currentCoords[0];
        int y = currentCoords[1];
        boolean leftConnected = List.of('-', 'F', 'L').contains(map.get(y).charAt(x - 1));
        boolean rightConnected = List.of('-', '7', 'J').contains(map.get(y).charAt(x + 1));
        boolean downConnected = List.of('|', 'J', 'L').contains(map.get(y + 1).charAt(x));
        boolean upConnected = List.of('|', '7', 'F').contains(map.get(y - 1).charAt(x));
        if (leftConnected) {
            if (rightConnected) return '-';
            if (downConnected) return '7';
            if (upConnected) return 'J';
        }
        if (rightConnected) {
            if (downConnected) return 'F';
            if (upConnected) return 'L';
        }
        return '|';
    }

    private static String translateCoord(int[] coord) {
        return coord[0] + " " + coord[1];
    }
}
