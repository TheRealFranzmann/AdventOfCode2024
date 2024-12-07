package adventOfCode2024.day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String filePath = "./adventOfCode2024/adventOfCode2024/day4/resources/input.txt";
        char[][] grid = parseInputToGrid(filePath);
        String word = "XMAS";

        int totalMatches = searchGrid(grid, word);
        System.out.println("Total matches found: " + totalMatches);

        int xmasPattern = findXMasPatterns(grid);
        System.out.println("Real XMAS found: " + xmasPattern);
    }

    public static char[][] parseInputToGrid(String fileName) {
        List<List<Character>> gridList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Character> row = new ArrayList<>();
                for (char c : line.toCharArray()) {
                        row.add(c);
                }
                if (!row.isEmpty()) {
                    gridList.add(row);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        int rows = gridList.size();
        if (rows == 0) return null;

        int cols = gridList.get(0).size();
        char[][] grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = gridList.get(i).get(j);
            }
        }

        return grid;
    }

    public static int searchGrid(char[][] grid, String word) {
        int rows = grid.length;
        int cols = grid[0].length;
        int wordLength = word.length();
        int count = 0;

        int[][] directions = {
            {0, 1},   // Right
            {0, -1},  // Left
            {1, 0},   // Down
            {-1, 0},  // Up
            {1, 1},   // Diagonal down-right
            {1, -1},  // Diagonal down-left
            {-1, 1},  // Diagonal up-right
            {-1, -1}  // Diagonal up-left
        };

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                for (int[] dir : directions) {
                    if (matchesWord(grid, r, c, word, dir)) {
                        count++;
                        System.out.println("Found " + word + " starting at (" + r + ", " + c + ") in direction (" + dir[0] + ", " + dir[1] + ")");
                    }
                }
            }
        }

        return count;
    }

    public static boolean matchesWord(char[][] grid, int r, int c, String word, int[] dir) {
        int rows = grid.length;
        int cols = grid[0].length;
        int wordLength = word.length();

        for (int i = 0; i < wordLength; i++) {
            int newRow = r + i * dir[0];
            int newCol = c + i * dir[1];

            if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols) {
                return false;
            }
            if (grid[newRow][newCol] != word.charAt(i)) {
                return false;
            }
        }

        return true;
    }
    
    public static int findXMasPatterns(char[][] grid) {
    	int rows = grid.length;
    	int cols = grid[0].length;
    	int count = 0;
    	
    	for (int r = 1; r < rows - 1; r++) {
    		for (int c = 1; c < cols - 1; c++) {
    			if (isXmasPattern(grid, r, c)) {
    				count++;
    			}
    		}
    	}
    	return count;
    }
    
    public static boolean isXmasPattern(char[][] grid, int r, int c) {
        if (grid[r][c] != 'A') {
            return false;
        }

        boolean topLeftToBottomRightMAS = checkPattern(grid, r - 1, c - 1, r + 1, c + 1, "MAS");
        boolean topLeftToBottomRightSAM = checkPattern(grid, r - 1, c - 1, r + 1, c + 1, "SAM");
        boolean topRightToBottomLeftMAS = checkPattern(grid, r - 1, c + 1, r + 1, c - 1, "MAS");
        boolean topRightToBottomLeftSAM = checkPattern(grid, r - 1, c + 1, r + 1, c - 1, "SAM");

        return (topLeftToBottomRightMAS && topRightToBottomLeftMAS) ||
               (topLeftToBottomRightMAS && topRightToBottomLeftSAM) ||
               (topLeftToBottomRightSAM && topRightToBottomLeftMAS) ||
               (topLeftToBottomRightSAM && topRightToBottomLeftSAM);
    }
    
    private static boolean checkPattern(char[][] grid, int r1, int c1, int r2, int c2, String pattern) {
        int rows = grid.length;
        int cols = grid[0].length;

        if (r1 < 0 || r2 >= rows || c1 < 0 || c2 >= cols) {
            return false; // Out of bounds
        }

        int dr = (r2 - r1) / 2; // Vertical step
        int dc = (c2 - c1) / 2; // Horizontal step

        return grid[r1][c1] == pattern.charAt(0) &&
               grid[r1 + dr][c1 + dc] == pattern.charAt(1) &&
               grid[r2][c2] == pattern.charAt(2);
    }
}
