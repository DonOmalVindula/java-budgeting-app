package services;

import models.Category;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static utils.GenerateCategoryViaUserInput.generateCategoryViaUserInput;

public class EnterNewCategory {
    // 1 - Add a new category
    public static void enterNewCategory(File categoryFile, Scanner scanner) {

        Category category = generateCategoryViaUserInput(categoryFile, scanner);

        // Write the category to the file categoryFile
        try (FileWriter categoryWriter = new FileWriter(categoryFile, true)) {
            categoryWriter.write("\n"+ category.getCategoryData() );
            categoryWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}