package adventOfCode2024.day3;

import adventOfCode2024.abstractClasses.AbstractFileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 extends AbstractFileReader {
    private List<String> mulCalls = new ArrayList<>();
    private boolean allowMul = true;

    public Day3(String filePath) {
        super(filePath);
    }

    public static void run() {
        String filePath = "./src/adventOfCode2024/resources/inputDay3.txt";
        Day3 reader = new Day3(filePath);
        reader.readFile();
        reader.multipliedResult();
    }

    @Override
    protected void processFile(BufferedReader reader) throws IOException {
        Pattern pattern = Pattern.compile("do\\(\\)|don't\\(\\)|mul\\(\\d+,\\d+\\)");
        String line;
        while ((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                String match = matcher.group();
                if ("don't()".equals(match)) {
                    allowMul = false;
                } else if ("do()".equals(match)) {
                    allowMul = true;
                } else if (allowMul) {
                    mulCalls.add(match);
                }
            }
        }
    }

    private void multipliedResult() {
        int totalSum = 0;
        for (String input : mulCalls) {
            totalSum += parseAndMultiply(input);
        }
        System.out.println("Total sum of multiplications: " + totalSum);
    }

    private static int parseAndMultiply(String input) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);
        
        int product = 1;
        while(matcher.find()) {
            product *= Integer.parseInt(matcher.group());
        }
        
        return product;
    }
}
