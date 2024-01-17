import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
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
                    System.out.println("Edit a transaction");
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
                    break;
            }
        }

    }

    // 1 - Add a new transaction
    public static void enterNewTransaction(File categoryFile, File transactionFile) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Get Expense or Income
            System.out.println("Please enter the type: ");
            System.out.println("1. Expense");
            System.out.println("2. Income");
            int typeInput = scanner.nextInt();
            TransactionType type = TransactionType.EXPENSE;
            if (typeInput == 1) {
                type = TransactionType.EXPENSE;
            } else if (typeInput == 2) {
                type = TransactionType.INCOME;
            } else {
                System.out.println("Invalid input");
            }

            // Get amount
            System.out.println("Please enter the amount: ");
            double amount = scanner.nextDouble();

            // Get category
            showCategories(categoryFile);

            System.out.println("Please enter the category ID: ");
            int categoryId = scanner.nextInt();

            // Get note
            System.out.println("Please enter the note: ");
            String note = scanner.next();

            System.out.println("Please enter if the transaction is recurring: ");
            System.out.println("1. Yes");
            System.out.println("2. No");
            int isRecurringInput = scanner.nextInt();

            boolean isRecurring = false;
            if (isRecurringInput == 1) {
                isRecurring = true;
            } else if (isRecurringInput == 2) {
                isRecurring = false;
            } else {
                System.out.println("Invalid input");
            }

            int recurringDay = 0;

            if (isRecurring) {
                System.out.println("Please enter the recurring occurance in days: ");
                recurringDay = scanner.nextInt();
            }

            // Create a new transaction
            Transaction transaction = new Transaction(amount, type, note, isRecurring, recurringDay, categoryId);

            try (// Write the transaction to the file transactionFile
                    FileWriter transcationWriter = new FileWriter(transactionFile, true)) {
                transcationWriter.write(transaction.getTransactionData());
                transcationWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 8 - View all categories
    public static void showCategories(File categoryFile) {
        // Get category
        System.out.println("Category ID\t\tCategory Name\t\tBudgeted Amount");
        System.out.println("------------------------------------------------------------------");

        try (
                // Read the categories from the file categoryFile
                Scanner myReader = new Scanner(categoryFile)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                // Split the data into an array
                String[] dataArray = data.split(",");
                System.out.println(dataArray[0] + "\t\t\t" + dataArray[1] + "\t\t" + dataArray[2]);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
