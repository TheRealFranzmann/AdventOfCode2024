package adventOfCode2024.day6;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

import adventOfCode2024.abstractClasses.AbstractFileReader;

public class Day6 extends AbstractFileReader {

    private char[][] grid;
    private Set<String> obsSet = new HashSet<>();
    private int xBound = 0;
    private int yBound = 0;
    private int startX, startY;

    public Day6(String filePath) {
        super(filePath);
    }

    public static void run() {
        String filePath = "./src/adventOfCode2024/resources/inputDay6.txt";
        Day6 reader = new Day6(filePath);
        reader.readFile();

        System.out.println("Starting simulation...");
        int part1 = reader.simulatePart1().size();
        System.out.println("Part 1: " + part1 + " unique positions visited.");

        int part2 = reader.simulatePart2();
        System.out.println("Part 2: " + part2 + " obstructions cause a loop.");
    }

    @Override
    protected void processFile(BufferedReader reader) throws IOException {
        List<List<Character>> gridList = new ArrayList<>();
        String line;
        int row = 0;

        while ((line = reader.readLine()) != null) {
            List<Character> rowList = new ArrayList<>();
            for (int col = 0; col < line.length(); col++) {
                char c = line.charAt(col);
                rowList.add(c);

                if (c == '#') {
                    obsSet.add(col + "," + row);
                } else if (c == '^') {
                    startX = col;
                    startY = row;
                }
            }
            gridList.add(rowList);
            row++;
        }

        yBound = gridList.size();
        xBound = gridList.get(0).size();
        grid = new char[yBound][xBound];
        for (int i = 0; i < yBound; i++) {
            for (int j = 0; j < xBound; j++) {
                grid[i][j] = gridList.get(i).get(j);
            }
        }
    }

    private Set<String> simulatePart1() {
        Set<String> visited = new HashSet<>();
        int[] directionSetX = {0, 1, 0, -1};
        int[] directionSetY = {-1, 0, 1, 0};
        int directionIndex = 0;

        int currX = startX, currY = startY;

        while (currX >= 0 && currX < xBound && currY >= 0 && currY < yBound) {
            visited.add(currX + "," + currY);

            int nextX = currX + directionSetX[directionIndex];
            int nextY = currY + directionSetY[directionIndex];

            if (obsSet.contains(nextX + "," + nextY)) {
                directionIndex = (directionIndex + 1) % 4;
            } else {
                currX = nextX;
                currY = nextY;
            }
        }

        System.out.println("Visited positions: " + visited);
        return visited;
    }

    private int simulatePart2() {
        int obstructions = 0;
        int[] directionSetX = {0, 1, 0, -1};
        int[] directionSetY = {-1, 0, 1, 0};

        // Get visited positions from Part 1
        Set<String> visited = simulatePart1();

        for (String potentialObs : visited) {
            // Skip the starting position
            if (potentialObs.equals(startX + "," + startY)) {
                continue;
            }

            // Create a temporary obstruction set with the potential obstruction
            Set<String> tempObsSet = new HashSet<>(obsSet);
            tempObsSet.add(potentialObs);

            int currX = startX, currY = startY;
            int directionIndex = 0;
            Set<String> tempVisited = new HashSet<>();

            boolean foundLoop = false;

            // Simulate guard movement with the new obstruction
            while (currX >= 0 && currX < xBound && currY >= 0 && currY < yBound) {
                int nextX = currX + directionSetX[directionIndex];
                int nextY = currY + directionSetY[directionIndex];

                if (tempObsSet.contains(nextX + "," + nextY)) {
                    String turnKey = currX + "," + currY + "," + directionSetX[directionIndex] + "," + directionSetY[directionIndex];

                    if (tempVisited.contains(turnKey)) {
                        foundLoop = true;
                        break;
                    }

                    tempVisited.add(turnKey);
                    directionIndex = (directionIndex + 1) % 4;
                } else {
                    currX = nextX;
                    currY = nextY;
                }
            }

            if (foundLoop) {
                obstructions++;
            }
        }

        return obstructions;
    }

}
