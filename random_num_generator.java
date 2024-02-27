import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomNumGenerator {
    public static void main(String[] args) {
        // Specify the file name
        String fileName = "random_numbers2.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {


            for (int i = 0; i < 1000000; i++) {
                int randomNumber = (int)(Math.random() * 1000000);
                writer.write(Integer.toString(randomNumber));
                writer.newLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
