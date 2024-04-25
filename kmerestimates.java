import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class kmerestimates {

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        System.out.println("Input Kmer Lengtgh");
        int k = scnr.nextInt();
        scnr.close();
        List<String> buffer = new ArrayList<>();
        Set<String> set = new HashSet<String>();
        int invProbability = 1;
        String file = processFile();
        while (scnr.hasNextLine()) {
            String line = scnr.nextLine();
            for (int i = 0; i < line.length() - k; i++) {
                String currentSequence = line.substring(i, i + k);
                // Your processing logic here
            }
        }
        for (int i = 0; i < file.length() - k; i++) {
            String currentSequence = "";
            for (int j = 0; j < k; j++) {
                currentSequence += file.charAt(i + j);
            }
            set.add(currentSequence);
            if (buffer.contains((currentSequence))) {
                if (!check(invProbability)) {
                    buffer.remove((currentSequence));
                }
            } else {
                if (check(invProbability)) {
                    if (isBufferFull(buffer)) {
                        invProbability *= 2;
                        List<String> itemsToRemove = new ArrayList<>();

                        for (String bufferItem : buffer) {
                            if (!check(invProbability)) {
                                itemsToRemove.add(bufferItem);
                            }
                        }

                        buffer.removeAll(itemsToRemove);
                    } else {
                        buffer.add((currentSequence));
                    }
                }
            }

        }
        int result = invProbability * buffer.size();
        System.out.println("Result: " + result);
        System.out.println("Expected: " + set.size());

    }

    private static boolean check(int invProbability) {
        Random random = new Random();
        return random.nextInt(invProbability) == 0;
    }

    private static boolean isBufferFull(List<String> buffer) {
        int bufferSizeLimit = 1000;
        return buffer.size() >= bufferSizeLimit;
    }

    private static String processFile() {
        StringBuilder build = new StringBuilder();
        try (Scanner fileScanner = new Scanner(new File("kmers.txt"))) {
            while (fileScanner.hasNextLine()) {
                build.append(fileScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return build.toString();
    }

}
