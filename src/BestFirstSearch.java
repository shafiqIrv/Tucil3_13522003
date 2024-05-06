import java.util.*;

public class BestFirstSearch extends WordLadder {
    // Fungsi untuk mencari jumlah karakter yang berbeda antara dua string, akan
    // digunakan sebagai nilai h(n)
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
        // Jarak dari current sampai end word berdasarkan perbedaan jumlah karakter,
        // akan digunakan sebagai nilai h(n)
        Map<String, Integer> costs = new HashMap<>();
        Map<String, String> cameFrom = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(costs::get));
        queue.add(start);
        costs.put(start, 0);
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
                int newCost = getDiffChar(next, end);
                if (!costs.containsKey(next) || newCost < costs.get(next)) {
                    costs.put(next, newCost);
                    cameFrom.put(next, current);
                    queue.add(next);
                }
            }
        }
        // Kondisi ga ketemu sama sekali
        List<String> path = new ArrayList<>();
        path.add(count.toString());
        return path;
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
