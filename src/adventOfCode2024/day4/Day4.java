package adventOfCode2024.day4;

import adventOfCode2024.abstractClasses.AbstractFileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 extends AbstractFileReader {

    private char[][] grid;

    public Day4(String filePath) {
        super(filePath);
    }

    public static void run() {
        String filePath = "./src/adventOfCode2024/resources/inputDay4.txt";
        Day4 reader = new Day4(filePath);
        reader.readFile();

        String word = "XMAS";
        int totalMatches = reader.searchGrid(word);
        System.out.println("Total matches found: " + totalMatches);

        int xmasPattern = reader.findXMasPatterns();
        System.out.println("Real XMAS found: " + xmasPattern);
    }

    @Override
    protected void processFile(BufferedReader reader) throws IOException {
        List<List<Character>> gridList = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            List<Character> row = new ArrayList<>();
            for (char c : line.toCharArray()) {
                row.add(c);
            }
            if (!row.isEmpty()) {
                gridList.add(row);
            }
        }

        if (!gridList.isEmpty()) {
            int rows = gridList.size();
            int cols = gridList.get(0).size();
            grid = new char[rows][cols];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    grid[i][j] = gridList.get(i).get(j);
                }
            }
        }
    }

    private int searchGrid(String word) {
        int rows = grid.length;
        int cols = grid[0].length;
        int wordLength = word.length();
        int count = 0;

        int[][] directions = {
            {0, 1}, {0, -1}, {1, 0}, {-1, 0},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                for (int[] dir : directions) {
                    if (matchesWord(r, c, word, dir)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private boolean matchesWord(int r, int c, String word, int[] dir) {
        int rows = grid.length;
        int cols = grid[0].length;

        for (int i = 0; i < word.length(); i++) {
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

    private int findXMasPatterns() {
        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;

        for (int r = 1; r < rows - 1; r++) {
            for (int c = 1; c < cols - 1; c++) {
                if (isXmasPattern(r, c)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isXmasPattern(int r, int c) {
        if (grid[r][c] != 'A') {
            return false;
        }

        // Check for the pattern "MAS" or "SAM" in diagonal directions from the center 'A'
        return (checkPattern(r, c, -1, -1, "MAS") || checkPattern(r, c, -1, -1, "SAM")) &&
               (checkPattern(r, c, 1, 1, "MAS") || checkPattern(r, c, 1, 1, "SAM"));
    }

    private boolean checkPattern(int r, int c, int dr, int dc, String pattern) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        for (int i = 1; i <= pattern.length(); i++) {
            int nr = r + dr * i;
            int nc = c + dc * i;
            if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) return false;
            if (grid[nr][nc] != pattern.charAt(i - 1)) return false;
        }
        return true;
    }
}
