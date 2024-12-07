package adventOfCode2024.day1;

import adventOfCode2024.abstractClasses.AbstractFileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day1 extends AbstractFileReader {

    private List<Integer> leftColumn = new ArrayList<>();
    private List<Integer> rightColumn = new ArrayList<>();

    public Day1(String filePath) {
        super(filePath);
    }

    public static void run() {
        String filePath = "./src/adventOfCode2024/resources/inputDay1.txt";
        Day1 reader = new Day1(filePath);
        reader.readFile();

        int totalDistance = calculateTotalDistance(reader.leftColumn, reader.rightColumn);
        System.out.println("Total Distance Between Lists: " + totalDistance);

        int similarityScore = getSimilarityScore(reader.leftColumn, reader.rightColumn);
        System.out.println("Similarity Score: " + similarityScore);
    }

    @Override
    protected void processFile(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\s+");
            if (parts.length == 2) {
                leftColumn.add(Integer.parseInt(parts[0]));
                rightColumn.add(Integer.parseInt(parts[1]));
            }
        }
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

    public static int calculateTotalDistance(List<Integer> leftColumn, List<Integer> rightColumn) {
        Collections.sort(leftColumn);
        Collections.sort(rightColumn);

        int totalDistance = 0;
        int size = Math.min(leftColumn.size(), rightColumn.size());

        for (int i = 0; i < size; i++) {
            totalDistance += Math.abs(leftColumn.get(i) - rightColumn.get(i));
        }

        return totalDistance;
    }
}
