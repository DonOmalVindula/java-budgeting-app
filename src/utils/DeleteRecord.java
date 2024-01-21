package utils;

import java.io.*;
import java.util.Scanner;

public class DeleteRecord {
    public static void deleteRecord(File file, Scanner scanner, String entityType, String entityName) {
        System.out.print(STR."Enter the \{entityType} ID to delete: ");

        try {
            int entityId = scanner.nextInt();
            File tempFile = new File("data/temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean idFound = false;

            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                String[] dataArray = trimmedLine.split(",");
                if (Integer.parseInt(dataArray[0]) == entityId) {
                    idFound = true;
                    continue;
                }
                writer.write(currentLine + System.lineSeparator());
            }

            writer.close();
            reader.close();

            // If the ID was not found, print a message and delete the temporary file
            if (!idFound) {
                System.out.println(STR."\{entityType} ID not found");
                tempFile.delete();
                return;
            }

            // Delete the original file
            file.delete();

            // Rename the new file to the filename the original file had.
            boolean successful = tempFile.renameTo(file);
            if (successful) {
                System.out.println(STR."\{entityType} \{entityName} deleted successfully");
            } else {
                System.out.println(STR."\{entityType} \{entityName} not deleted");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
