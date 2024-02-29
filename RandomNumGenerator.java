import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomNumGenerator {
    public static void main(String[] args) {
        // Specify the file name
        String fileName = "kmers.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            for (int i = 0; i < 100000000; i++) {
                int randomNumber = (int) (Math.random() * 4);
                switch (randomNumber) {
                    case 0:
                        writer.write("A");
                        break;

                    case 1:
                        writer.write("C");
                        break;

                    case 2:
                        writer.write("T");
                        break;
                    case 3:
                        writer.write("G");
                        break;
                }
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
