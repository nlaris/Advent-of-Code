package years.AoC2021;

import common.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day4 implements Day {

    private final List<Board> boards = new ArrayList<>();

    public void run(ArrayList<String> input) {
        List<Integer> turns = Arrays.stream(input.get(0).split(","))
                .map(Integer::parseInt).toList();
        int i = 2;
        while (i < input.size()) {
            final Board board = new Board();
            for (int j = 0; j < 5; j++) {
                board.addRow(j, Arrays.stream(input.get(i + j).split(" ")).filter(x -> !x.isEmpty()).toArray(String[]::new));
            }
            boards.add(board);
            i += 6;
        }
        boolean winnerFound = false;
        for (int turn : turns) {
            for (int b = 0; b < boards.size(); b++) {
                Board board = boards.get(b);
                int boardScore = board.checkTurn(turn);
                if (boardScore > 0) {
                    if (!winnerFound) {
                        winnerFound = true;
                        System.out.println("Part 1: " + boardScore * turn);
                    }
                    if (boards.size() == 1) {
                        System.out.println("Part 2: " + boardScore * turn);
                    }
                    boards.remove(b);
                    b--;
                }
            }
        }
    }

    private static class Board {

        private final int[][] tiles = new int[6][6];

        public void addRow(int row, String[] numbers) {
            for (int i = 0; i < numbers.length; i++) {
                tiles[row][i] = Integer.parseInt(numbers[i]);
            }
        }

        public int checkTurn(int num) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (tiles[i][j] == num) {
                        tiles[i][j] = -1;
                        tiles[i][5]++;
                        tiles[5][j]++;
                        if (tiles[i][5] == 5 || tiles[5][j] == 5) {
                            return getUnmarkedNumSum();
                        }
                        return 0;
                    }
                }
            }
            return 0;
        }

        private int getUnmarkedNumSum() {
            int sum = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (tiles[i][j] > -1) {
                        sum += tiles[i][j];
                    }
                }
            }
            return sum;
        }
    }
}
