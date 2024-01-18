package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ShowCategories {
    // 8 - View all categories
    public static void showCategories(File categoryFile) {
        // Get category
        System.out.println("models.Category ID\t\tmodels.Category Name\t\tBudgeted Amount");
        System.out.println("------------------------------------------------------------------");

        try (
                // Read the categories from the file categoryFile
                Scanner myReader = new Scanner(categoryFile)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                // Split the data into an array
                String[] dataArray = data.split(",");
                System.out.println(dataArray[0] + "\t\t\t\t\t\t" + dataArray[1] + "\t\t\t\t" + dataArray[2]);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
