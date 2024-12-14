package adventOfCode2024.day12;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adventOfCode2024.abstractClasses.AbstractFileReader;

public class Day12 extends AbstractFileReader {

    private char[][] gardenPots;
    private boolean[][] visited;

    public Day12(String filePath) {
        super(filePath);
    }

    public static void run() {
        String filePath = "./src/adventOfCode2024/resources/inputDay12.txt";
        Day12 reader = new Day12(filePath);
        reader.readFile();
        reader.calculateFencingCosts();  // Part 1
        reader.resetVisited();            // Reset visited for Part 2
        reader.calculateBulkDiscountCosts();  // Part 2
    }

    @Override
    protected void processFile(BufferedReader reader) throws IOException {
        List<char[]> gardenPotsList = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            gardenPotsList.add(line.toCharArray());
        }

        gardenPots = gardenPotsList.toArray(new char[gardenPotsList.size()][]);
        visited = new boolean[gardenPots.length][gardenPots[0].length];
    }
    
    private void resetVisited() {
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[i].length; j++) {
                visited[i][j] = false;
            }
        }
    }
    
    private void calculateFencingCosts() {
        Map<Character, Integer> totalCostsForEachChar = new HashMap<>();
        int totalCosts = 0;

        for (int i = 0; i < gardenPots.length; i++) {
            for (int j = 0; j < gardenPots[i].length; j++) {
                if (!visited[i][j]) {
                    RegionData data = exploreRegionForPerimeter(i, j, gardenPots[i][j]);
                    int regionCost = data.area * data.perimeter;  // Use perimeter for Part 1
                    totalCostsForEachChar.merge(gardenPots[i][j], regionCost, Integer::sum);
                    totalCosts += regionCost;
                }
            }
        }

        totalCostsForEachChar.forEach((k, v) -> System.out.println("Cost for " + k + ": " + v));
        System.out.println("Total cost of all fencing: " + totalCosts);
    }

    private void calculateBulkDiscountCosts() {
        Map<Character, Integer> totalCostsForEachChar = new HashMap<>();
        int totalBulkDiscountCost = 0;

        for (int i = 0; i < gardenPots.length; i++) {
            for (int j = 0; j < gardenPots[i].length; j++) {
                if (!visited[i][j]) {
                    RegionData data = exploreRegionForSides(i, j, gardenPots[i][j]);
                    int regionBulkDiscountCost = data.area * data.sides;
                    totalCostsForEachChar.merge(gardenPots[i][j], regionBulkDiscountCost, Integer::sum);
                    totalBulkDiscountCost += regionBulkDiscountCost;
                }
            }
        }
        
        totalCostsForEachChar.forEach((k, v) -> System.out.println("Bulk discount cost for " + k + ": " + v));
        System.out.println("Total bulk discount cost of all fencing: " + totalBulkDiscountCost);
    }

    private RegionData exploreRegionForPerimeter(int i, int j, char type) {
        int area = 0;
        int perimeter = 0;
        List<int[]> stack = new ArrayList<>();
        stack.add(new int[]{i, j});

        while (!stack.isEmpty()) {
            int[] pos = stack.remove(stack.size() - 1);
            int r = pos[0];
            int c = pos[1];

            if (r < 0 || r >= gardenPots.length || c < 0 || c >= gardenPots[0].length || visited[r][c] || gardenPots[r][c] != type)
                continue;

            visited[r][c] = true;
            area++;

            perimeter += checkPerimeter(r - 1, c, type); // North
            perimeter += checkPerimeter(r + 1, c, type); // South
            perimeter += checkPerimeter(r, c - 1, type); // West
            perimeter += checkPerimeter(r, c + 1, type); // East

            addToStack(stack, r - 1, c, type);
            addToStack(stack, r + 1, c, type);
            addToStack(stack, r, c - 1, type);
            addToStack(stack, r, c + 1, type);
        }

        return new RegionData(area, perimeter);
    }

    private RegionData exploreRegionForSides(int i, int j, char type) {
        int area = 0;
        int sides = 0;
        List<int[]> stack = new ArrayList<>();
        stack.add(new int[]{i, j});

        while (!stack.isEmpty()) {
            int[] pos = stack.remove(stack.size() - 1);
            int r = pos[0];
            int c = pos[1];

            if (r < 0 || r >= gardenPots.length || c < 0 || c >= gardenPots[0].length || visited[r][c] || gardenPots[r][c] != type)
                continue;

            visited[r][c] = true;
            area++;

            sides += countSide(r - 1, c, type); // North
            sides += countSide(r + 1, c, type); // South
            sides += countSide(r, c - 1, type); // West
            sides += countSide(r, c + 1, type); // East

            addToStack(stack, r - 1, c, type);
            addToStack(stack, r + 1, c, type);
            addToStack(stack, r, c - 1, type);
            addToStack(stack, r, c + 1, type);
        }

        return new RegionData(area, sides);
    }

    private int checkPerimeter(int r, int c, char type) {
        if (r < 0 || r >= gardenPots.length || c < 0 || c >= gardenPots[0].length || gardenPots[r][c] != type) {
            return 1;
        }
        return 0;
    }

    private void addToStack(List<int[]> stack, int r, int c, char type) {
        if (r >= 0 && r < gardenPots.length && c >= 0 && c < gardenPots[0].length && !visited[r][c] && gardenPots[r][c] == type) {
            stack.add(new int[]{r, c});
        }
    }

    private int countSide(int r, int c, char type) {
        if (r < 0 || r >= gardenPots.length || c < 0 || c >= gardenPots[0].length || gardenPots[r][c] != type) {
            return 1;
        }
        return 0;
    }

    private class RegionData {
        int area, perimeter, sides;

        public RegionData(int area, int sides) {
            this.area = area;
            this.sides = sides; // Use sides for both perimeter and sides based on context
        }
    }
}
