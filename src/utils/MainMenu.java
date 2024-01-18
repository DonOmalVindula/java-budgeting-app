package utils;

import java.io.File;
import java.util.Scanner;

import static services.EditTransaction.editTransaction;
import static services.EnterNewTransaction.enterNewTransaction;
import static services.ShowCategories.showCategories;

public class MainMenu {
    public static void showMainMenu() {
        String categoryFilePath = "data/categories.txt";
        String transactionFilePath = "data/transactions.txt";
        File categoryFile = new File(categoryFilePath);
        File transactionFile = new File(transactionFilePath);

        // Get user input
        System.out.println("Please enter what you want to do next: ");
        System.out.println("1. Add a new transaction");
        System.out.println("2. Edit a transaction");
        System.out.println("3. Delete a transaction");
        System.out.println("4. View all transactions");
        System.out.println("5. Add a new category");
        System.out.println("6. Edit a category");
        System.out.println("7. Delete a category");
        System.out.println("8. View all categories");
        System.out.println("9. View a category");

        try (Scanner scanner = new Scanner(System.in)) {
            int userInput = scanner.nextInt();

            switch (userInput) {
                case 1:
                    System.out.println("********** Add a new transaction **********");
                    enterNewTransaction(categoryFile, transactionFile);
                    break;
                case 2:
                    System.out.println("********** Edit a transaction **********");
                    editTransaction(categoryFile, transactionFile);
                    break;
                case 3:
                    System.out.println("Delete a transaction");
                    break;
                case 4:
                    System.out.println("View all transactions");
                    break;
                case 5:
                    System.out.println("Add a new category");
                    break;
                case 6:
                    System.out.println("Edit a category");
                    break;
                case 7:
                    System.out.println("Delete a category");
                    break;
                case 8:
                    System.out.println("********** View all categories **********");
                    showCategories(categoryFile);
                    break;
                case 9:
                    System.out.println("View a category");
                    break;
                default:
                    System.out.println("Invalid input");
                    showMainMenu();
                    break;
            }
        }
    }
}
