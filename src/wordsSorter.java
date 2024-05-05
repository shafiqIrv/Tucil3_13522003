import java.io.*;
import java.util.*;

public class wordsSorter {
    public static void main(String[] args) {

        String inputFile = "./test/words.txt";
        String outputFile = "sorted_words.txt";

        List<String> words = readWordsFromFile4(inputFile);

        Collections.sort(words, Comparator.comparingInt(String::length));

        writeWordsToFile(words, outputFile);

        System.out.println("Words sorted by length and written to " + outputFile);
    }

    // read words from file
    private static List<String> readWordsFromFile(String filename) {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    private static List<String> readWordsFromFile4(String filename) {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() == 4) {
                    words.add(line.toLowerCase());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    private static void writeWordsToFile(List<String> words, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String word : words) {
                writer.write(word);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
