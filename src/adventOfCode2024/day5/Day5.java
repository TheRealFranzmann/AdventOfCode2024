package adventOfCode2024.day5;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import adventOfCode2024.abstractClasses.AbstractFileReader;

public class Day5 extends AbstractFileReader {

    private Map<Integer, Set<Integer>> rulesMap = new HashMap<>();
    private List<List<Integer>> incorrectUpdates = new ArrayList<>();

    public Day5(String filePath) {
        super(filePath);
    }

    public static void run() {
        String filePath = "./src/adventOfCode2024/resources/inputDay5.txt";
        Day5 reader = new Day5(filePath);
        reader.readFile();
        
        int sumOfReorderedMiddlePages = reader.reorderAndSumMiddlePages();
        System.out.println("Total sum of middle page numbers from reordered updates: " + sumOfReorderedMiddlePages);
    }
    
    private int reorderAndSumMiddlePages() {
        int sum = 0;
        for (List<Integer> update : incorrectUpdates) {
            List<Integer> orderedUpdate = topologicalSort(update);
            sum += findMiddlePage(orderedUpdate);
        }
        return sum;
    }

    private List<Integer> topologicalSort(List<Integer> pages) {
        Map<Integer, Integer> inDegree = new HashMap<>();
        Map<Integer, List<Integer>> graph = new HashMap<>();
        
        for (Integer page : pages) {
            if (rulesMap.containsKey(page)) {
                for (Integer target : rulesMap.get(page)) {
                    if (pages.contains(target)) {
                        graph.computeIfAbsent(page, k -> new ArrayList<>()).add(target);
                        inDegree.put(target, inDegree.getOrDefault(target, 0) + 1);
                    }
                }
            }
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for (Integer page : pages) {
            if (inDegree.getOrDefault(page, 0) == 0) {
                queue.add(page);
            }
        }
        
        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            int current = queue.poll();
            result.add(current);
            for (int neighbor : graph.getOrDefault(current, Collections.emptyList())) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }
        
        if (result.size() == pages.size()) return result;
        else throw new RuntimeException("Cycle detected in rules, cannot sort pages");
    }
    
    @Override
    protected void processFile(BufferedReader reader) throws IOException {
        String line;
        boolean isProcessingRules = true;
        
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                isProcessingRules = false;
                continue;
            }
            
            if (isProcessingRules) {
                String[] parts = line.split("\\|");
                int before = Integer.parseInt(parts[0]);
                int after = Integer.parseInt(parts[1]);
                rulesMap.computeIfAbsent(before, k -> new HashSet<>()).add(after);
            } else {
                List<Integer> update = Arrays.stream(line.split(","))
                                             .map(Integer::parseInt)
                                             .collect(Collectors.toList());
                if (!isUpdateCorrect(update)) {
                    incorrectUpdates.add(update);
                }
            }
        }
    }
    
    private boolean isUpdateCorrect(List<Integer> update) {
        for (int i = 0; i < update.size(); i++) {
            int currentPage = update.get(i);
            if (rulesMap.containsKey(currentPage)) {
                for (int mustFollow : rulesMap.get(currentPage)) {
                    if (update.contains(mustFollow) && update.indexOf(mustFollow) < i) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private int findMiddlePage(List<Integer> update) {
        return update.get(update.size() / 2);
    }
}
