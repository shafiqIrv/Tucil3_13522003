import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class treeBuilder {

    // Atribut untuk menyimpan array string dari file
    public static ArrayList<String> Words = parse("sorted-words.txt");

    // Parser dari file sorted_words.txt
    public static ArrayList<String> parse(String filename) {
        ArrayList<String> words = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("test/" + filename));
            String line;

            while ((line = reader.readLine()) != null) {
                words.add(line);
            }

            reader.close();
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        return words;
    }

    // Parser untuk kata yang memiliki panjang 4

    // Fungsi untuk mengecek apakah 2 kata hanya berbeda 1 huruf
    public static boolean isSimilar(String word1, String word2) {
        word1 = word1.toLowerCase();
        word2 = word2.toLowerCase();
        int count = 0;
        if (word1.length() != word2.length()) {
            return false;
        }
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                count++;
            }
        }
        if (count == 1) {
            return true;
        }
        return false;
    }

    public static ArrayList<String> getSimilar(String word) {

        ArrayList<String> similarWords = new ArrayList<>();
        int i = 0;
        String currentWord = Words.get(0);

        while (i < Words.size() && currentWord.length() <= word.length()) {
            // System.out.println(currentWord);
            currentWord = Words.get(i);

            // Lewati yang panjangnya lebih kecil
            if (currentWord.length() < word.length()) {
                i++;
                continue;
            }

            if (isSimilar(word, currentWord)) {
                similarWords.add(currentWord);
            }

            i++;

        }

        return similarWords;
    }

    public static boolean isAlphaOnly(String input) {
        return input.matches("^[a-zA-Z]*$");
    }

    // Fungsi untuk mengenerate tree dari kata-kata yang mirip, akan di store dalam
    // file txt
    public static void buildTree() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("tree.txt"));
            for (String word : Words) {
                if (!isAlphaOnly(word)) {
                    continue;
                }

                ArrayList<String> similarWords = getSimilar(word);
                writer.write(word + " ");
                for (String similarWord : similarWords) {
                    if (!isAlphaOnly(similarWord)) {
                        continue;
                    }
                    writer.write(similarWord + " ");
                }
                writer.newLine();

            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        buildTree();
    }
}
