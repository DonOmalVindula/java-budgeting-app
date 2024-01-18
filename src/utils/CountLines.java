package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CountLines {
    public static int countLines(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int lines = 0;
            while (reader.readLine() != null) {
                lines++;
            }
            return lines;
        } catch (IOException e) {
            System.out.println("Error: Something went wrong!");
            return -1; // Return -1 to indicate an error
        }
    }
}
