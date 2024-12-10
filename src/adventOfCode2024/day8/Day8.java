package adventOfCode2024.day8;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import adventOfCode2024.abstractClasses.AbstractFileReader;

public class Day8 extends AbstractFileReader {

    private char[][] grid;
    private Map<Character, List<Coordinate>> antennaMap;

    public Day8(String filePath) {
        super(filePath);
    }

    @Override
    protected void processFile(BufferedReader reader) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        parseMap(lines);
    }

    private void parseMap(List<String> lines) {
        int height = lines.size();
        int width = lines.get(0).length();
        grid = new char[height][width];
        antennaMap = new HashMap<>();

        for (int i = 0; i < height; i++) {
            String line = lines.get(i);  // Correctly declared here
            for (int j = 0; j < width; j++) {
                char ch = line.charAt(j);
                grid[i][j] = ch;
                if (ch != '.') {
                    antennaMap.computeIfAbsent(ch, k -> new ArrayList<>()).add(new Coordinate(j, i));
                }
            }
        }
    }

    private Set<Coordinate> calculateAntinodes() {
        Set<Coordinate> uniqueAntinodes = new HashSet<>();
        antennaMap.forEach((frequency, antennas) -> {
            for (int i = 0; i < antennas.size(); i++) {
                for (int j = i + 1; j < antennas.size(); j++) {
                    Coordinate start = antennas.get(i);
                    Coordinate end = antennas.get(j);
                    Coordinate vector = new Coordinate(end.col - start.col, end.row - start.row);
                    markLine(start, vector, uniqueAntinodes);
                    markLine(start, new Coordinate(-vector.col, -vector.row), uniqueAntinodes);
                }
            }
        });
        return uniqueAntinodes;
    }

    private void markLine(Coordinate start, Coordinate vector, Set<Coordinate> antinodes) {
        Coordinate current = new Coordinate(start.col, start.row);
        while (isInGrid(current)) {
            antinodes.add(new Coordinate(current.col, current.row));
            current = new Coordinate(current.col + vector.col, current.row + vector.row);
        }
    }

    private boolean isInGrid(Coordinate coord) {
        return coord.col >= 0 && coord.col < grid[0].length && coord.row >= 0 && coord.row < grid.length;
    }

    public static void run() {
        String filePath = "./src/adventOfCode2024/resources/inputDay8.txt";
        Day8 reader = new Day8(filePath);
        reader.readFile();
        Set<Coordinate> antinodeLocations = reader.calculateAntinodes();
        System.out.println("Number of unique antinode locations: " + antinodeLocations.size());
    }

    private static class Coordinate {
        int col, row;

        Coordinate(int col, int row) {
            this.col = col;
            this.row = row;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Coordinate that = (Coordinate) obj;
            return col == that.col && row == that.row;
        }

        @Override
        public int hashCode() {
            return Objects.hash(col, row);
        }
    }
}
