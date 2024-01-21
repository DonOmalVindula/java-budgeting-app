package services;

import java.io.*;
import java.util.Scanner;

public class DeleteCategory {
    // 3 - Delete a Category
    public static void deleteCategory(File categoryFile, Scanner scanner) {
        System.out.print("Enter the category ID to delete: ");

        try {
            int categoryId = scanner.nextInt();
            File tempFile = new File("data/temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(categoryFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                String[] dataArray = trimmedLine.split(",");
                if (Integer.parseInt(dataArray[0]) == categoryId) {
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }

            writer.close();
            reader.close();
            // Delete the original file
            categoryFile.delete();

            // Rename the new file to the filename the original file had.
            boolean successful = tempFile.renameTo(categoryFile);
            if (successful) {
                System.out.println("Category deleted successfully");
            } else {
                System.out.println("Category not deleted");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
