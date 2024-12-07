package adventOfCode2024.day2;

import adventOfCode2024.abstractClasses.AbstractFileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day2 extends AbstractFileReader {

    private List<List<Integer>> inputList = new ArrayList<>();

    public Day2(String filePath) {
        super(filePath);
    }

    public static void run() {
        String filePath = "./src/adventOfCode2024/resources/inputDay2.txt";
        Day2 reader = new Day2(filePath);
        reader.readFile();

        List<Boolean> resultList = new ArrayList<>();

        for (List<Integer> list : reader.inputList) {
            boolean result = increaseDecreaseOrEven(list);
            if (result) {
                resultList.add(result);
            }
        }
        System.out.println("Number of save reports " + resultList.size());
    }

    @Override
    protected void processFile(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\s+");
            List<Integer> row = new ArrayList<>();
            for (String part : parts) {
                row.add(Integer.parseInt(part));
            }
            inputList.add(row);
        }
    }

    public static boolean increaseDecreaseOrEven(List<Integer> inputList) {
        int upperBound = 3;
        int lowerBound = 1;

        if (isSafe(inputList, upperBound, lowerBound)) {
            return true;
        }

        for (int i = 0; i < inputList.size(); i++) {
            List<Integer> modifiedList = new ArrayList<>(inputList);
            modifiedList.remove(i);
            if (isSafe(modifiedList, upperBound, lowerBound)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSafe(List<Integer> list, int upperBound, int lowerBound) {
        boolean isIncreasing = true;
        boolean isDecreasing = true;

        for (int i = 0; i < list.size() - 1; i++) {
            int start = list.get(i);
            int next = list.get(i + 1);
            if (Math.abs(start - next) > upperBound || Math.abs(start - next) < lowerBound) {
                return false;
            }

            if (start > next) {
                isIncreasing = false;
            } else if (start < next) {
                isDecreasing = false;
            }

            if (!isIncreasing && !isDecreasing) {
                return false;
            }
        }
        return true;
    }
}
