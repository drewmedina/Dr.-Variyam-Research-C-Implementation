<<<<<<< Updated upstream
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
=======
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.awt.SystemColor.info;
>>>>>>> Stashed changes

public class cvm_estimates {
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        cvmWithInt("random_numbers2");
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
    }
    private static void cvmWithInt(String fileName){
            List<String> buffer = new ArrayList<>();
            Set<String> set = new HashSet<String>();
            int invProbability = 1;

            try (BufferedReader br = new BufferedReader(new FileReader(fileName + ".txt"))) {
                String line;
                while ((line = br.readLine()) != null) {

                    int item = Integer.parseInt(line.trim());
                    set.add(Integer.toString(item));
                    if (buffer.contains(Integer.toString(item))) {
                        if (!check(invProbability)) {
                            buffer.remove(Integer.toString(item));
                        }
                    } else {
                        if (check(invProbability)) {
                            if (isBufferFull(buffer)) {
                                invProbability = getInvProbability(buffer, invProbability);
                            } else {
                                buffer.add(Integer.toString(item));
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            int result = invProbability * buffer.size();
            System.out.println("Result: " + result);
            System.out.println("Set: " + set.size());
        }
        private static void cvmWithString(String fileName){
            List<String> buffer = new ArrayList<>();
            Set<String> set = new HashSet<String>();
            int invProbability = 1;

            try (BufferedReader br = new BufferedReader(new FileReader(fileName + ".txt"))) {
                String line;
                while ((line = br.readLine()) != null) {

                    String trimmed = line.trim();
                    set.add(trimmed);
                    if (buffer.contains(trimmed)) {
                        if (!check(invProbability)) {
                            buffer.remove(trimmed);
                        }
                    } else {
                        if (check(invProbability)) {
                            if (isBufferFull(buffer)) {
                                invProbability = getInvProbability(buffer, invProbability);
                            } else {
                                buffer.add(trimmed);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            int result = invProbability * buffer.size();
            System.out.println("Result: " + result);
            System.out.println("Set: " + set.size());
        }

<<<<<<< Updated upstream
        try{
            Scanner scnr = new Scanner(new File("Shakespeare_data.csv"));
//            File hamlet = new File("");
            while (scnr.hasNextLine()){
                String[] line = scnr.nextLine().split("");
                System.out.println(line);
                for(int i = 4 ; i < line.length; i++){

                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        int result = invProbability * buffer.size();
        System.out.println("Result: " + result);
=======
    private static int getInvProbability(List<String> buffer, int invProbability) {
        invProbability *= 2;
        List<String> itemsToRemove = new ArrayList<>();

        for (String bufferItem : buffer) {
            if (!check(2)) {
                itemsToRemove.add(bufferItem);
            }
        }

        buffer.removeAll(itemsToRemove);
        return invProbability;
>>>>>>> Stashed changes
    }


    private static boolean check(int invProbability) {
        Random random = new Random();
        return random.nextInt(invProbability) == 0;
    }

    private static boolean isBufferFull(List<String> buffer) {
<<<<<<< Updated upstream
        int bufferSizeLimit = 10;
=======
        int bufferSizeLimit = 1000;
>>>>>>> Stashed changes
        return buffer.size() >= bufferSizeLimit;
    }



}
