package services;

import models.Category;

import java.io.*;
import java.util.Scanner;

import static utils.GenerateCategoryViaUserInput.generateCategoryViaUserInput;

public class EditCategory {
    // 2 - Edit a category
    public static void editCategory(File categoryFile, Scanner scanner) {
        System.out.print("Enter the category ID to be edited: ");
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
                    Category category = generateCategoryViaUserInput(categoryFile, scanner);
                    writer.write(category.getCategoryData() + System.getProperty("line.separator"));
                } else {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
            }

            writer.close();
            reader.close();

            // Delete the original file
            categoryFile.delete();

            // Rename the new file to the filename the original file had.
            boolean successful = tempFile.renameTo(categoryFile);
            if (successful) {
                System.out.println("Category edited successfully");
            } else {
                System.out.println("Category not edited");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
