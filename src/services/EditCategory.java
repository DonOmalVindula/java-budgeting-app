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
            boolean categoryFound = false; // Flag to indicate if the category ID was found
            File tempFile = new File("data/temp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(categoryFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                String[] dataArray = trimmedLine.split(",");
                int currentCategoryId = Integer.parseInt(dataArray[0]);

                if (currentCategoryId == categoryId) {
                    Category category = generateCategoryViaUserInput(categoryFile, scanner);
                    assert category != null;
                    category.setCategoryId(categoryId); // Set the category ID without modification
                    writer.write(category.getCategoryData() + System.lineSeparator());
                    categoryFound = true; // Set the flag to true if the category is found
                } else {
                    writer.write(currentLine + System.lineSeparator());
                }
            }

            writer.close();
            reader.close();
            categoryFile.delete();

            if (categoryFound) {
                boolean successful = tempFile.renameTo(categoryFile);
                if (successful) {
                    System.out.println("Category edited successfully");
                } else {
                    System.out.println("Category not edited");
                }
            } else {
                System.out.println("Category ID not found");
                tempFile.delete(); // Delete the temporary file if category ID is not found
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
