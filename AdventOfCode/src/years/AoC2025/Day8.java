package years.AoC2025;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import common.Day;

public class Day8 implements Day {

  HashMap<Box[], Double> connections = new HashMap<>();
  private int NUM_CONNECTIONS = 1000;

  @Override
  public void run(ArrayList<String> input) {
    ArrayList<Box> boxes = new ArrayList<>();
    long part1 = 1, part2 = 0;
    for (String line : input) {
      Integer[] coords = java.util.Arrays.stream(line.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
      boxes.add(new Box(coords[0], coords[1], coords[2]));
    }
    for (int i = 0; i < boxes.size() - 1; i++) {
      for (int j = i + 1; j < boxes.size(); j++) {
        Box[] pair = new Box[] { boxes.get(i), boxes.get(j) };
        connections.put(pair, getDistance(boxes.get(i), boxes.get(j)));
      }
    }
    ArrayList<HashSet<Box>> circuits = new ArrayList<>();
    ArrayList<Box[]> sortedConnections = connections.entrySet().stream()
      .sorted(java.util.Map.Entry.comparingByValue())
      .map(java.util.Map.Entry::getKey)
      .collect(java.util.stream.Collectors.toCollection(ArrayList::new));


    int i = 0;
    while (true) {
      Box[] connectionBoxes = sortedConnections.get(i);
      Box boxA = connectionBoxes[0];
      Box boxB = connectionBoxes[1];
      int indexA = getIndexOfCircuit(boxA, circuits);
      int indexB = getIndexOfCircuit(boxB, circuits);
      if (indexA == -1 && indexB == -1) {
        HashSet<Box> circuit = new HashSet<>();
        circuit.add(boxA);
        circuit.add(boxB);
        circuits.add(circuit);
      } else if (indexA == -1) {
        circuits.get(indexB).add(boxA);
      } else if (indexB == -1) {
        circuits.get(indexA).add(boxB);
      } else if (indexA != indexB) {
        circuits.get(indexA).addAll(circuits.get(indexB));
        circuits.remove(indexB);
      }
      i++;
      if (i == NUM_CONNECTIONS) {
        circuits.sort((a, b) -> Integer.compare(b.size(), a.size()));
        for (int j = 0; j < 3; j++) {
          part1 *= circuits.get(j).size();
        }
      }
      if (circuits.get(0).size() == input.size()) {
        part2 = boxA.x * boxB.x;
        break;
      }
    }
    System.out.println("Part 1: " + part1);
    System.out.println("Part 1: " + part2);
  }

  private int getIndexOfCircuit(Box box, ArrayList<HashSet<Box>> circuits) {
    for (int i = 0; i < circuits.size(); i++) {
      if (circuits.get(i).contains(box)) return i;
    }
    return -1;
  }

  private double getDistance(Box a, Box b) {
    double dx = a.x - b.x;
    double dy = a.y - b.y;
    double dz = a.z - b.z;
    return Math.sqrt(dx * dx + dy * dy + dz * dz);
  }
}

class Box {
  public long x;
  public long y;
  public long z;
  
  public Box(long x, long y, long z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
}