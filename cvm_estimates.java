import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class cvm_estimates {
    public static void main(String[] args) {
        List<String> buffer = new ArrayList<>();
        int invProbability = 1;

        try (BufferedReader br = new BufferedReader(new FileReader("randomnumber2.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                int item = Integer.parseInt(line.trim());

                if (buffer.contains(Integer.toString(item))) {
                    if (!check(invProbability)) {
                        buffer.remove(Integer.toString(item));
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
                            buffer.add(Integer.toString(item));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            Scanner scnr = new Scanner(new File("Shakespeare_data.csv"));
            File hamlet = new File();
            while (scnr.hasNextLine){
                String[] line = scnr.nextLine().split();
                System.out.println(line);
                for(int i = 4 ; i < line.length; i++){

                }
            }
        }

        int result = invProbability * buffer.size();
        System.out.println("Result: " + result);
    }

    private static boolean check(int invProbability) {
        Random random = new Random();
        return random.nextInt(invProbability) == 0;
    }

    private static boolean isBufferFull(List<String> buffer) {
        int bufferSizeLimit = 10;
        return buffer.size() >= bufferSizeLimit;
    }
}
