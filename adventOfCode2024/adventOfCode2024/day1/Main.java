package adventOfCode2024.day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
    	String filePath = "./adventOfCode2024/adventOfCode2024/day1/resources/input.txt";

        Pair<List<Integer>, List<Integer>> result = readFileIntoColumns(filePath);

        List<Integer> leftColumn = result.getLeft();
        List<Integer> rightColumn = result.getRight();

        int totalDistance = calculateTotalDistance(leftColumn, rightColumn);

        System.out.println("Total Distance Between Lists: " + totalDistance);

        int similarityScore = getSimilarityScore(leftColumn, rightColumn);

        System.out.println("Similarity Score: " + similarityScore);
    }
    
    public static int getSimilarityScore(List<Integer> leftColumn, List<Integer> rightColumn) {
        int similarityScore = 0;

        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : rightColumn) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        for (int num : leftColumn) {
            int frequency = frequencyMap.getOrDefault(num, 0);
            similarityScore += num * frequency;
        }

        return similarityScore;
    }


    public static Pair<List<Integer>, List<Integer>> readFileIntoColumns(String filePath) {
        List<Integer> leftColumn = new ArrayList<>();
        List<Integer> rightColumn = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length == 2) {
                    leftColumn.add(Integer.parseInt(parts[0]));
                    rightColumn.add(Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Pair<>(leftColumn, rightColumn);
    }

    public static int calculateTotalDistance(List<Integer> leftColumn, List<Integer> rightColumn) {
        Collections.sort(leftColumn);
        Collections.sort(rightColumn);

        int totalDistance = 0;
        int size = Math.min(leftColumn.size(), rightColumn.size()); // Ensure we don't exceed list sizes

        for (int i = 0; i < size; i++) {
            totalDistance += Math.abs(leftColumn.get(i) - rightColumn.get(i));
        }

        return totalDistance;
    }
}

class Pair<L, R> {
    private final L left;
    private final R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }
}
