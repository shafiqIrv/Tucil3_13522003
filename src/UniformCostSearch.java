import java.util.*;

public class UniformCostSearch extends WordLadder {
    public static List<String> ucs(String start, String end) {
        // Jarak dari root sampai current, akan diambil yang paling kecil pada tiap
        Map<String, Integer> costSoFar = new HashMap<>();
        Map<String, String> cameFrom = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(costSoFar::get));
        queue.add(start);
        costSoFar.put(start, 0);
        Integer count = 0;

        // Iterasi sampai queue kosong/ketemu
        while (!queue.isEmpty()) {
            count++;
            String current = queue.poll();
            // Kondisi Ketemu
            if (current.equals(end)) {
                List<String> path = reconstructPath(cameFrom, current);
                path.addFirst(count.toString());
                return path;
            }
            // Kondisi lanjut
            for (String next : wordMap.getOrDefault(current, Collections.emptyList())) {
                int newCost = costSoFar.get(current) + 1;
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    cameFrom.put(next, current);
                    queue.add(next);
                }
            }
        }
        // Kondisi ga ketemu sama sekali
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

}
