import java.util.*;

public class BestFirstSearch extends WordLadder {

    public static Integer getDiffChar(String curr, String target) {
        int count = 0;
        for (int i = 0; i < curr.length(); i++) {
            if (curr.charAt(i) != target.charAt(i)) {
                count++;
            }
        }
        return count;

    }

    public static List<String> bfs(String start, String end) {
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
                int newCost = getDiffChar(next, end);
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

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<String> path = bfs("atlas", "kontl");
        long endTime = System.currentTimeMillis();

        if (path != null) {
            for (String node : path) {
                System.out.println(node);
            }
        } else {
            System.out.println("Path not found.");
        }

        System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }

}
