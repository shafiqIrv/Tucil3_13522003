import java.util.*;

class AStar extends WordLadder {
    // Fungsi untuk mencari jumlah karakter yang berbeda antara dua string, akan digunakan sebagai nilai h(n)
    public static Integer getDiffChar(String curr, String target) {
        int count = 0;
        for (int i = 0; i < curr.length(); i++) {
            if (curr.charAt(i) != target.charAt(i)) {
                count++;
            }
        }
        return count;

    }

    public static List<String> astar(String start, String end) {
        // Jarak dari root sampai current, akan diambil yang paling kecil pada tiap node
        Map<String, Integer> costSoFar = new HashMap<>();
        Map<String, Integer> costTotal = new HashMap<>();
        Map<String, String> cameFrom = new HashMap<>();
        // Mengurut dari costTotal yaitu g(n) + h(n)
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingInt(costTotal::get));
        queue.add(start);
        costSoFar.put(start, 0);
        costTotal.put(start, getDiffChar(start, end));
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
                int newCostTotal = newCost + getDiffChar(next, end);
                if (!costSoFar.containsKey(next) || newCostTotal < costTotal.get(next)) {
                    costSoFar.put(next, newCost);
                    costTotal.put(next, newCostTotal);
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
    // Membentuk path dari start sampai end
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
