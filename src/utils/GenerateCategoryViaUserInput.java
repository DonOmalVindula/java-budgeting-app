package utils;

import models.Category;

import java.io.File;
import java.util.Scanner;


public class GenerateCategoryViaUserInput {
    public static Category generateCategoryViaUserInput(File categoryFile, Scanner scanner) {
        try {
            // Get category name
            System.out.println("Please enter the name of the category: ");
            String name = scanner.next();


            // Get budget amount
            System.out.println("Please enter the budget amount: ");
            double budgetAmount = scanner.nextDouble();

            // Create a new category
            Category category = new Category(name, budgetAmount);

            return category;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

