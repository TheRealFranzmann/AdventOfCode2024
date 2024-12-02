package adventOfCode2024.day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		String filePath = "./adventOfCode2024/adventOfCode2024/day2/resources/input.txt";
		
		List<List<Integer>> inputList = readFileIntoList(filePath);
		List<Boolean> resultList = new ArrayList<>();
		
		for (List<Integer> list : inputList) {
			Boolean result = increaseDecreaseOrEven(list);
			
			if (result == true) {
				resultList.add(result);
			}
		}
		System.out.println("Number of save reports " + resultList.size());
	}

	
	public static List<List<Integer>> readFileIntoList(String filePath) {
		List<List<Integer>> rows = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split("\\s+");
				List<Integer> row = new ArrayList<>();
				for (String part : parts) {
					row.add(Integer.parseInt(part));
				}
				rows.add(row);
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		return rows;
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
	        int difference = start - next;
	        int absolute = Math.abs(difference);

	        if (absolute > upperBound || absolute < lowerBound) {
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
