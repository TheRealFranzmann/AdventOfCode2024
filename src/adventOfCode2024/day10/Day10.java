package adventOfCode2024.day10;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

import adventOfCode2024.abstractClasses.AbstractFileReader;

public class Day10 extends AbstractFileReader {
    private List<String> lines = new ArrayList<>();
    private static final int[][] DIFFS = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private Set<Point> topsReached;
    record Point(int i, int j) {}

    public Day10(String filePath) {
        super(filePath);
    }

    @Override
    protected void processFile(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        solve();
    }

    private void solve() {
        int sum = 0, sum2 = 0;

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
            	int num = get(lines, new Point(i, j));
            	if (num == 0) {
            		topsReached = new HashSet<>();
            		sum2 += dfs(new Point(i,j), lines);
            		sum += topsReached.size();
            	}
            }
        }

        System.out.println("Part 1: Total unique positions reached: " + sum);
        System.out.println("Part 2: Total score of all trailheads: " + sum2);
    }

    private int dfs(Point current, List<String> lines) {
    	int val = get(lines, current);
    	if (val == 9) {
    		topsReached.add(current);
    		return 1;
    	}
    	
    	int total = 0;
    	for (int[] d : DIFFS) {
    		Point neighbor = new Point(current.i + d[0], current.j +d[1]);
    		int curVal = get(lines, neighbor);
    		if (curVal < 0 || curVal > 9) continue;
    		if (curVal - val == 1) {
    			total += dfs(neighbor, lines);
    		}
    	}
    	return total;
    }


    public static void run() {
        String filePath = "./src/adventOfCode2024/resources/inputDay10.txt";
        Day10 reader = new Day10(filePath);
        reader.readFile();
    }
    
    private static int get(List<String> lines, Point p) {
        try {
            return lines.get(p.i).charAt(p.j) - '0';
        } catch (Exception e) {
            return -1;
        }
    }
}
