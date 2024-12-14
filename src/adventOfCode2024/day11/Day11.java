package adventOfCode2024.day11;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import adventOfCode2024.abstractClasses.AbstractFileReader;

public class Day11 extends AbstractFileReader {

    private Map<Long, List<Long>> memo = new HashMap<>(); // Stores results of number transformations
    private List<Long> numbers = new ArrayList<>();
    
    public Day11(String filePath) {
        super(filePath);
    }

    @Override
    protected void processFile(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] numbersInLine = line.split("\\s+"); // Splits the line at one or more spaces
            for (String numStr : numbersInLine) {
                try {
                    long number = Long.parseLong(numStr);
                    numbers.add(number);
                } catch (NumberFormatException e) {
                    System.out.println("Failed to parse '" + numStr + "' as integer.");
                }
            }
        }
        solve();
    }
    
//    public static void run() {
////        String filePath = "./src/adventOfCode2024/resources/inputDay11.txt";
////        Day11 reader = new Day11(filePath);
////        reader.readFile();
//    	
//    }
    
    private void solve() {
        for (int iteration = 0; iteration < 75; iteration++) {
            List<Long> newNumbers = new LinkedList<>();
            for (long currentNumber : numbers) {
                newNumbers.addAll(transformNumber(currentNumber));
            }
            numbers = new LinkedList<>(newNumbers);

            if ((iteration + 1) % 10 == 0) {
                System.out.println("Iteration " + (iteration + 1) + " complete. Current number of longs: " + numbers.size());
            }
        }
        System.out.println("Final size of longs: " + numbers.size());
    }

    private List<Long> transformNumber(long number) {
        if (memo.containsKey(number)) {
            return memo.get(number);
        }

        List<Long> result = new ArrayList<>();
        if (number == 0) {
            result.add(1L);
        } else {
            String numberStr = Long.toString(number);
            if (numberStr.length() % 2 == 0) {
                int middle = numberStr.length() / 2;
                result.add(Long.parseLong(numberStr.substring(0, middle)));
                result.add(Long.parseLong(numberStr.substring(middle)));
            } else {
                result.add(number * 2024);
            }
        }

        memo.put(number, result);
        return result;
    }
    
    
    // my code didnt work (Java Heap Space) so here the solution from someone else
    private static String INPUT = "28591 78 0 3159881 4254 524155 598 1";
    
    private static int index = 1;
    private static Map<Long, Integer> ids;
    private static long[][] dp;
    
    public static void run() {
        String line = Arrays.stream(INPUT.split("\\\\s+")).toList().get(0);
        ids = new HashMap<>();
        dp = new long[5000][76];

        long total1 = 0, total2 = 0;
        long s = System.currentTimeMillis();
        for (String part : line.split(" ")) {
            long num = Long.parseLong(part);
            total1 += count(num, 25);
            total2 += count(num, 75);
        }
        System.out.println("Took: " + (System.currentTimeMillis() - s) + "ms");

        System.out.println("Part 1: " + total1);
        System.out.println("Part 2: " + total2);
    }
    private static long count(long num, int blinksLeft) {
        if (blinksLeft == 0) {
            return 1;
        }

        int id = getId(num);
        if (dp[id][blinksLeft] != 0) {
            return dp[id][blinksLeft] - 1;
        }

        long cnt = 0;
        if (num == 0) {
            cnt = count(1, blinksLeft - 1);
        } else if (digits(num) % 2 == 0) {
            int d = digits(num);
            long mod = (long) Math.pow(10, d / 2);
            long left = num / mod, right = num % mod;
            cnt += count(left, blinksLeft - 1);
            cnt += count(right, blinksLeft - 1);
        } else {
            cnt = count(num * 2024, blinksLeft - 1);
        }
        dp[id][blinksLeft] = cnt + 1;
        return cnt;
    }

    private static int getId(long num) {
        if (!ids.containsKey(num)) {
            ids.put(num, index++);
        }
        return ids.get(num);
    }

    private static int digits(long num) {
        return ((int) Math.log10(num) + 1);
    }
}
