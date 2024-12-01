package AoC2023;

import common.Day;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// https://adventofcode.com/2023/day/10
public class Day10 implements Day {

    private final ArrayList<String> map = new ArrayList<>();
    private final ArrayList<String> path = new ArrayList<>();
    private final HashMap<Character, int[][]> connectionCoords = new HashMap<>() {{
        put('-', new int[][]{{-1, 0}, {1, 0}});
        put('|', new int[][]{{0, 1}, {0, -1}});
        put('7', new int[][]{{-1, 0}, {0, 1}});
        put('J', new int[][]{{-1, 0}, {0, -1}});
        put('L', new int[][]{{1, 0}, {0, -1}});
        put('F', new int[][]{{1, 0}, {0, 1}});
    }};

    private int[] currentCoords;
    private int furthestSpot;

    @Override
    public void run(BufferedReader reader) throws IOException {
        String line;
        int row = 0;
        while ((line = reader.readLine()) != null) {
            if (line.contains("S")) currentCoords = new int[]{line.indexOf('S'), row};
            map.add(line);
            row++;
        }
        final char startingChar = getStartingChar();
        map.set(currentCoords[1], map.get(currentCoords[1]).replace('S', startingChar));
        findPath(startingChar);
        System.out.println("Part 1: " + furthestSpot);
        System.out.println("Part 2: " + getEnclosedLocations());
    }

    private char getStartingChar() {
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

    private void findPath(char currentChar) {
        String prevCoords = "";
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

    private int[] getNextCoords(char currentChar, String prevCoords) {
        int[][] conns = connectionCoords.get(currentChar);
        int[] nextCoords = new int[]{currentCoords[0] + conns[0][0], currentCoords[1] + conns[0][1]};
        if (translateCoord(nextCoords).equals(prevCoords)) {
            nextCoords = new int[]{currentCoords[0] + conns[1][0], currentCoords[1] + conns[1][1]};
        }
        return nextCoords;
    }

    private String translateCoord(int[] coord) {
        return coord[0] + " " + coord[1];
    }

    private int getEnclosedLocations() {
        int sum = 0;
        for (int row = 1; row < map.size() - 1; row++) {
            for (int col = 1; col < map.get(0).length() - 1; col++) {
                if (!path.contains(translateCoord(new int[]{col, row}))) sum += countPathEdges(row, col);
            }
        }
        return sum;
    }

    private int countPathEdges(int row, int col) {
        int totalEdges = 0;
        Character neededCorner = null;
        for (int r = 0; r < row; r++) {
            if (path.contains(translateCoord(new int[]{col, r}))) {
                char c = map.get(r).charAt(col);
                if (c == '-') {
                    totalEdges++;
                } else if (List.of('F', 'L', 'J', '7').contains(c)) {
                    if (neededCorner == null) {
                        neededCorner = c == '7' ? 'L' : 'J';
                    } else {
                        totalEdges += c == neededCorner ? 1 : 2;
                        neededCorner = null;
                    }
                }
            }
        }
        return totalEdges % 2;
    }
}
