import java.util.*;

public class UniformCostSearch extends WordLadder {
    public static List<String> ucs(String start, String end) {
        Map<String, Integer> costSoFar = new HashMap<>();
        Map<String, String> cameFrom = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(costSoFar::get));
        queue.add(start);
        costSoFar.put(start, 0);
        Integer count = 0;

        while (!queue.isEmpty()) {
            count++;
            // System.out.println(costSoFar + "\n");
            String current = queue.poll();

            if (current.equals(end)) {
                List<String> path = reconstructPath(cameFrom, current);
                path.addFirst(count.toString());
                return path;
            }

            for (String next : wordMap.getOrDefault(current, Collections.emptyList())) {
                int newCost = costSoFar.get(current) + 1;
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    cameFrom.put(next, current);
                    queue.add(next);
                }
            }
        }
        List<String> path = new ArrayList<>();
        path.add(count.toString());
        return path; // Path not found
    }

    private static List<String> reconstructPath(Map<String, String> cameFrom, String current) {
        List<String> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    // public static void main(String[] args) {
    // // System.out.println(wordMap);
    // long startTime = System.currentTimeMillis();
    // List<String> path = ucs("atlases", "cabaret");
    // long endTime = System.currentTimeMillis();

    // if (path != null) {
    // for (String node : path) {
    // System.out.println(node);
    // }
    // } else {
    // System.out.println("Path not found.");
    // }

    // System.out.println("Execution time: " + (endTime - startTime) + " ms");
    // }

}
