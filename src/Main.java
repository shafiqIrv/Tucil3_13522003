import java.util.Scanner;
import java.util.List; // Import the List class from java.util
import java.util.ArrayList; // Import the ArrayList class from java.util

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.println("Masukkan Kata Awal:");
            String start = (scanner.nextLine()).toLowerCase();

            while (!WordLadder.isStringValid(start)) {
                System.out.println("Kata Awal yang dimasukkan tidak valid, Silahkan masukkan lagi: ");
                start = (scanner.nextLine()).toLowerCase();
            }

            System.out.println("Masukkan Kata Akhir:");
            String end = (scanner.nextLine()).toLowerCase();
            while (!WordLadder.isStringValid(end)) {
                System.out.println("Kata Akhir yang dimasukkan tidak valid, Silahkan masukkan lagi: ");
                end = (scanner.nextLine()).toLowerCase();
            }

            if (start.length() != end.length()) {
                System.out.println("Panjang kata berbeda, silahkan ulangi kembali: ");
                continue;
            }

            System.out.println("Pilih mode pencarian: ");
            System.out.println("- Greedy Best First Search (1)");
            System.out.println("- Uniform Cost Search (2)");
            System.out.println("- A-Star Search (3)");

            System.out.print("Ketik 1/2/3 : ");
            String mode = (scanner.nextLine()).toLowerCase();
            while (!(mode != "1" || mode != "2" || mode != "3")) {
                System.out.println("Masukkan mode yang valid: ");
                mode = scanner.nextLine();
            }

            List<String> result = new ArrayList<>();

            long startTime = System.currentTimeMillis();
            switch (mode) {
                case "1":
                    result = BestFirstSearch.bfs(start, end);
                    break;
                case "2":
                    result = UniformCostSearch.ucs(start, end);
                    break;
                case "3":
                    result = AStar.astar(start, end);
                    break;
            }
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            String visitednodes = result.get(0);
            result.removeFirst();

            System.out.println("==========================================");
            System.out.println("Node Visited: " + visitednodes);
            System.out.println("Execution time: " + executionTime + " ms");
            System.out.println("==========================================");

            for (String item : result) {
                System.out.println(item);
            }

            System.out.println("==========================================");
            System.out.print("Main Lagi? (y/n): ");
            String answer = (scanner.nextLine()).toLowerCase();

            if (answer.equals("n")) {
                System.out.println("=====================");
                System.out.println("=     GAME OVER     =");
                System.out.println("=====================");
                scanner.close();
                return;
            }

        }
    }
}
