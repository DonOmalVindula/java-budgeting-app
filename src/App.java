import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        showMainMenu();
    }

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

    public static Transaction generateTransactionViaUserInput(File categoryFile) {
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

            int categoryId = 0;
            if (type == TransactionType.INCOME) {
                categoryId = 0;
            } else {
                // Get category
                showCategories(categoryFile);

                System.out.println("Please enter the category ID: ");
                categoryId = scanner.nextInt();
            }

            // Get note
            System.out.println("Please enter the note: ");
            String note = scanner.next();

            boolean isRecurring = false;
            int recurringDay = 0;

            if (type == TransactionType.INCOME) {
                isRecurring = false;
                recurringDay = 0;
            } else {
                System.out.println("Please enter if the transaction is recurring: ");
                System.out.println("1. Yes");
                System.out.println("2. No");
                int isRecurringInput = scanner.nextInt();
                if (isRecurringInput == 1) {
                    isRecurring = true;
                } else if (isRecurringInput == 2) {
                    isRecurring = false;
                } else {
                    System.out.println("Invalid input");
                }
            }

            if (isRecurring) {
                System.out.println("Please enter the recurring occurance in days: ");
                recurringDay = scanner.nextInt();
            }

            // Create a new transaction
            Transaction transaction = new Transaction(amount, type, note, isRecurring, recurringDay, categoryId);

            return transaction;
        }
    }

    // 1 - Add a new transaction
    public static void enterNewTransaction(File categoryFile, File transactionFile) {

        Transaction transaction = generateTransactionViaUserInput(categoryFile);
        // Write the transaction to the file transactionFile
        try (FileWriter transcationWriter = new FileWriter(transactionFile, true)) {
            transcationWriter.write(transaction.getTransactionData());
            transcationWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 2 - Edit a transaction
    public static void editTransaction(File categoryFile, File transactionFile) {
        System.out.println("Enter the transaction ID: ");
        try (Scanner scanner = new Scanner(System.in)) {
            int transactionId = scanner.nextInt();
            StringBuffer buffer = new StringBuffer();
            boolean isFound = false;
            String fileContents = "";
            String matchedLine = "";

            // Get the transaction from the file transactionFile
            try (Scanner myReader = new Scanner(transactionFile)) {
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    // Reading lines of the file and appending them to StringBuffer
                    buffer.append(data + System.lineSeparator());
                    fileContents = buffer.toString();

                    // Split the data into an array
                    String[] dataArray = data.split(",");
                    System.out.println(dataArray[0]);
                    if (Integer.parseInt(dataArray[0]) == transactionId) {
                        isFound = true;
                        matchedLine = data;
                    }
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (isFound) {
                System.out.println("Transaction found with ID: " + transactionId);
                Transaction transaction = generateTransactionViaUserInput(categoryFile);

                // Write the transaction to the file transactionFile
                fileContents = fileContents.replaceAll(matchedLine, transaction.getTransactionData());
                try (FileWriter transcationWriter = new FileWriter(transactionFile)) {
                    transcationWriter.write(fileContents);
                    transcationWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Transaction not found");
                showMainMenu();
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
