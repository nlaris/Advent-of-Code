package years.AoC2024;

import common.Day;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day9 implements Day {
    @Override
    public void run(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        System.out.println("Part 1: " + part1(line));
        System.out.println("Part 2: " + part2(line));
    }

    private long part1(String line) {
        long part1Sum = 0;
        int lastId = line.length() / 2, index = 0, endMarker = 1;
        int endCount = Character.getNumericValue(line.charAt(line.length() - endMarker));
        for (int i = 0; i <= line.length() - endMarker; i++) {
            int num = Character.getNumericValue(line.charAt(i));
            int j = 0, id = i / 2;
            if (i % 2 == 0) {
                while (j < num && (lastId > id || j < endCount)) {
                    part1Sum += (long) index * id;
                    j++;
                    index++;
                }
            } else {
                while (j < num && lastId >= id) {
                    part1Sum += (long) index * lastId;
                    if (--endCount == 0) {
                        lastId--;
                        endMarker += 2;
                        endCount = Character.getNumericValue(line.charAt(line.length() - endMarker));
                    }
                    j++;
                    index++;
                }
            }
        }
        return part1Sum;
    }

    private long part2(String line) {
        long sum = 0;
        int index = 0;
        ArrayList<Integer> pendingFiles = IntStream.iterate(0, n -> n <= line.length() / 2, n -> n + 1)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0; i < line.length() && pendingFiles.size() > 0; i++) {
            int num = Character.getNumericValue(line.charAt(i));
            int j = 0;
            if (i % 2 == 0) {
                int id = i / 2;
                if (pendingFiles.contains(id)) {
                    while (j < num) {
                        sum += (long) index * (id);
                        j++;
                        index++;
                    }
                    pendingFiles.remove(Integer.valueOf(id));
                } else {
                    index += num;
                }
            } else {
                int endMarker = pendingFiles.get(pendingFiles.size() - 1) * 2;
                while (j < num && endMarker >= 0) {
                    while (endMarker >= 0 && (!pendingFiles.contains(endMarker / 2) || (num - j) < Character.getNumericValue(line.charAt(endMarker)))) {
                        endMarker -= 2;
                    }
                    if (endMarker >= 0) {
                        pendingFiles.remove(Integer.valueOf(endMarker / 2));
                        int endCount = Character.getNumericValue(line.charAt(endMarker)) + j;
                        while (j < endCount) {
                            sum += (long) index * (endMarker / 2);
                            j++;
                            index++;
                        }
                    }
                }
                index += (num - j);
            }
        }
        return sum;
    }
}
