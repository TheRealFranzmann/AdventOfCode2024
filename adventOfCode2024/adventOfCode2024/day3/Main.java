package adventOfCode2024.day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
		String filePath = "./adventOfCode2024/adventOfCode2024/day3/resources/input1.txt";

        List<String> mulCalls = readMultiplicationsIntoLists(filePath);
        System.out.println("Extracted mul calls:");
        for (String mul : mulCalls) {
            System.out.println(mul);
        }
        multipliedResult(mulCalls);
    }

    public static List<String> readMultiplicationsIntoLists(String filePath) {
    	Pattern pattern = Pattern.compile("do\\(\\)|don't\\(\\)|mul\\(\\d+,\\d+\\)");
    	boolean allowMul = true;
        List<String> results = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                	String match = matcher.group();
                    if ("don't()".equals(match)) {
                        allowMul = false;
                    } else if ("do()".equals(match)) {
                        allowMul = true;
                    } else if (allowMul) {
                        results.add(match);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return results;
    }
    
    public static void multipliedResult(List<String> mulCalls) {
    	int totalSum = 0;
    	for (String input : mulCalls) {
    		Pattern pattern = Pattern.compile("\\d+");
    		Matcher matcher = pattern.matcher(input);
    		
    		List<Integer> numbers = new ArrayList<>();
    		while(matcher.find()) {
    			numbers.add(Integer.parseInt(matcher.group()));
    		}
    		
    		if (numbers.size() == 2) {
    			int result = numbers.get(0) * numbers.get(1);
    		totalSum += result;	
    		}
    	}
    	System.out.println(totalSum);
    }    	
}
