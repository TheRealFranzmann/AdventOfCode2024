package adventOfCode2024.day7;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adventOfCode2024.abstractClasses.AbstractFileReader;

public class Day7 extends AbstractFileReader {

    private List<String> lines = new ArrayList<>();

    public Day7(String filePath) {
        super(filePath);
    }

    public static void run() {
        String filePath = "./src/adventOfCode2024/resources/inputDay7.txt";
        Day7 reader = new Day7(filePath);
        reader.readFile();
        reader.solve();
    }

    @Override
    protected void processFile(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
    }

    private void solve() {
        long sum1 = 0;
        long sum2 = 0;
        for (String line : lines) {
            String[] parts = line.split(": ");
            long target = Long.parseLong(parts[0]);
            List<Long> numbers = parseNumbers(parts[1].split(" "));
            
            if (isPossible(numbers, 1, numbers.get(0), target, false)) {
                sum1 += target;
            }
            if (isPossible(numbers, 1, numbers.get(0), target, true)) {
                sum2 += target;
            }
        }

        System.out.println("Part 1: " + sum1); // Sum without considering concatenation
        System.out.println("Part 2: " + sum2); // Sum considering concatenation
    }

    private List<Long> parseNumbers(String[] parts) {
        List<Long> numbers = new ArrayList<>();
        for (String part : parts) {
            numbers.add(Long.parseLong(part));
        }
        return numbers;
    }

    private boolean isPossible(List<Long> numbers, int index, long currentResult, long target, boolean considerConcat) {
        if (index == numbers.size()) {
            return currentResult == target;
        }
        long num = numbers.get(index);
        boolean result = isPossible(numbers, index + 1, currentResult + num, target, considerConcat)
                || isPossible(numbers, index + 1, currentResult * num, target, considerConcat);
        if (considerConcat) {
            result = result || isPossible(numbers, index + 1, append(currentResult, num), target, considerConcat);
        }
        return result;
    }

    private long append(long n1, long n2) {
        long second = n2;
        while (second > 0) {
            second /= 10;
            n1 *= 10;
        }
        return n1 + n2;
    }
}
