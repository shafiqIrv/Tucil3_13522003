
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class WordLadder {
    // Membaca file tree.txt dan membuat map dari string ke list of string
    protected static Map<String, List<String>> wordMap = createWordMap("test/tree.txt");

    private static Map<String, List<String>> createWordMap(String filename) {
        Map<String, List<String>> result = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" ");
                String root = words[0];
                List<String> children = Arrays.asList(Arrays.copyOfRange(words, 1, words.length));
                result.put(root, children);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // Mengecek apakah string valid (tidaka ada kareakter selain huruf)
    public static Boolean isStringValid(String word) {
        return wordMap.containsKey(word);
    }

}
