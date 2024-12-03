package years.AoC2023;

import common.Day;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

// https://adventofcode.com/2023/day/7
public class Day7 implements Day {

    private final HashMap<Character, Integer> cardValues = new HashMap<>() {{
        put('T', 10);
        put('J', 11);
        put('Q', 12);
        put('K', 13);
        put('A', 14);
    }};

    private final ArrayList<String> hands = new ArrayList<>();
    private final HashMap<String, Long> bids = new HashMap<>();
    private boolean part2 = false;

    private static final int HIGH_CARD_SCORE = 1;
    private static final int ONE_PAIR_SCORE = 2;
    private static final int TWO_PAIR_SCORE = 3;
    private static final int THREE_OF_A_KIND_SCORE = 4;
    private static final int FULL_HOUSE_SCORE = 5;
    private static final int FOUR_OF_A_KIND_SCORE = 6;
    private static final int FIVE_OF_A_KIND_SCORE = 7;

    @Override
    public void run(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] hand = line.split(" ");
            hands.add(hand[0]);
            bids.put(hand[0], Long.parseLong(hand[1]));
        }
        System.out.println("Part 1: " + getTotalWinnings(false));
        System.out.println("Part 2: " + getTotalWinnings(true));
    }

    private int getTotalWinnings(boolean _part2) {
        part2 = _part2;
        hands.sort(handComparator);
        int sum = 0;
        for (String hand : hands) sum += bids.get(hand) * (hands.indexOf(hand) + 1);
        return sum;
    }

    private final Comparator<String> handComparator = (o1, o2) -> {
        int compare = Integer.compare(getHandScore(o1), getHandScore(o2));
        if (compare != 0) return compare;
        for (int i = 0; i < o1.length(); i++) {
            int charCompare = Integer.compare(getCardValue(o1.charAt(i)), getCardValue(o2.charAt(i)));
            if (charCompare != 0) return charCompare;
        }
        return 0;
    };

    private int getCardValue(char c) {
        if (part2 && c == 'J') return 1;
        return Character.isDigit(c) ? Character.getNumericValue(c) : cardValues.get(c);
    }

    private int getHandScore(String hand) {
        int[] cardCounts = new int[15];
        int highestGroup = 0;
        for (char c : hand.toCharArray()) {
            int count = ++cardCounts[getCardValue(c)];
            if (c != 'J') highestGroup = Math.max(count, highestGroup);
        }
        boolean hasTriple = false, hasPair = false;
        for (int i = 2; i < cardCounts.length; i++) {
            if (cardCounts[i] == highestGroup) {
                cardCounts[i] += cardCounts[1];
                cardCounts[1] = 0;
            }
            if (cardCounts[i] == 2) {
                if (hasTriple) return FULL_HOUSE_SCORE;
                if (hasPair) return TWO_PAIR_SCORE;
                hasPair = true;
            }
            if (cardCounts[i] == 3) {
                if (hasPair) return FULL_HOUSE_SCORE;
                hasTriple = true;
            }
            if (cardCounts[i] == 4) return FOUR_OF_A_KIND_SCORE;
            if (cardCounts[i] == 5) return FIVE_OF_A_KIND_SCORE;
        }
        if (hasTriple) return THREE_OF_A_KIND_SCORE;
        if (hasPair) return ONE_PAIR_SCORE;
        return HIGH_CARD_SCORE;
    }
}