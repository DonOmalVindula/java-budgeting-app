package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ShowCategory {
    public static void showCategoryById(File categoryFile, Scanner scanner) {
        System.out.print("Enter the category ID to be viewed: ");
        int categoryId = scanner.nextInt();
        try (
                // Read the categories from the file categoryFile
                Scanner myReader = new Scanner(categoryFile)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                // Split the data into an array
                String[] dataArray = data.split(",");

                // Check if the current category ID matches the specified ID
                if (Integer.parseInt(dataArray[0]) == categoryId) {
                    System.out.println("models.Category ID\t\tmodels.Category Name\t\tBudgeted Amount");
                    System.out.println("------------------------------------------------------------------");
                    System.out.println(dataArray[0] + "\t\t\t\t\t\t" + dataArray[1] + "\t\t\t\t" + dataArray[2]);
                    return; // Exit the method after finding and displaying the category
                }
            }
            
            // If the category with the specified ID is not found
            System.out.println("Category with ID " + categoryId + " not found.");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

