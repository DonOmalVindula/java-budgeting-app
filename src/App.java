import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
        System.out.println("0 - Exit");

        try (Scanner scanner = new Scanner(System.in)) {
            int userInput = scanner.nextInt();
            boolean isContinue = true;

            switch (userInput) {
                case 1:
                    System.out.println("********** Add a new transaction **********");
                    enterNewTransaction(categoryFile, transactionFile, scanner);
                    break;
                case 2:
                    System.out.println("********** Edit a transaction **********");
                    editTransaction(categoryFile, transactionFile, scanner);
                    break;
                case 3:
                    System.out.println("********** Delete a transaction **********");
                    deleteTransaction(transactionFile, scanner);
                    break;
                case 4:
                    System.out.println("********** View all transactions **********");
                    showTransactions(transactionFile);
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
                case 0:
                    System.out.println("Exit - Thank you for using the application");
                    isContinue = false;
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }

            if (!isContinue) {
                return;
            }

            System.out.println("Do you want to continue? (Y/N)");
            String continueInput = scanner.next();

            System.out.println(continueInput);

            if (continueInput.equals("Y") || continueInput.equals("y")) {
                System.out.println("*********************************************************");
                showMainMenu();
            } else {
                System.out.println("Thank you for using the application");
            }

            scanner.close();
        }
    }

    public static Transaction generateTransactionViaUserInput(File categoryFile, File transactionFile, Scanner scanner) {
        try {
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

            if (type == TransactionType.EXPENSE) {
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

            // Find the last transaction ID
            int transactionId = 0;
            FileInputStream fis = new FileInputStream(transactionFile);
            byte[] byteArray = new byte[(int) transactionFile.length()];
            fis.read(byteArray);
            String data = new String(byteArray);
            String[] stringArray = data.split("\n");

            transactionId = stringArray.length + 1;
            fis.close();

            // Create a new transaction
            Transaction transaction = new Transaction(transactionId, amount, type, note, isRecurring, recurringDay, categoryId);

            return transaction;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 1 - Add a new transaction
    public static void enterNewTransaction(File categoryFile, File transactionFile, Scanner scanner) {

        Transaction transaction = generateTransactionViaUserInput(categoryFile, transactionFile, scanner);

        // Write the transaction to the file transactionFile
        try (FileWriter transcationWriter = new FileWriter(transactionFile, true)) {
            transcationWriter.write(transaction.getTransactionData() + "\n");
            transcationWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 2 - Edit a transaction
    public static void editTransaction(File categoryFile, File transactionFile, Scanner scanner) {
        System.out.print("Enter the transaction ID to be edited: ");
        try {
            int transactionId = scanner.nextInt();
            File tempFile = new File("data/temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(transactionFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                String[] dataArray = trimmedLine.split(",");
                if (Integer.parseInt(dataArray[0]) == transactionId) {
                    Transaction transaction = generateTransactionViaUserInput(categoryFile, transactionFile, scanner);
                    writer.write(transaction.getTransactionData() + System.getProperty("line.separator"));
                } else {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
            }

            writer.close();
            reader.close();

            // Delete the original file
            transactionFile.delete();

            // Rename the new file to the filename the original file had.
            boolean successful = tempFile.renameTo(transactionFile);
            if (successful) {
                System.out.println("Transaction edited successfully");
            } else {
                System.out.println("Transaction not edited");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3 - Delete a transaction
    public static void deleteTransaction(File transactionFile, Scanner scanner) {
        System.out.print("Enter the transaction ID to delete: ");

        try {
            int transactionId = scanner.nextInt();
            File tempFile = new File("data/temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(transactionFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                String[] dataArray = trimmedLine.split(",");
                if (Integer.parseInt(dataArray[0]) == transactionId) {
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }

            writer.close();
            reader.close();
            // Delete the original file
            transactionFile.delete();

            // Rename the new file to the filename the original file had.
            boolean successful = tempFile.renameTo(transactionFile);
            if (successful) {
                System.out.println("Transaction deleted successfully");
            } else {
                System.out.println("Transaction not deleted");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 4 - View all transactions
    public static void showTransactions(File transactionFile) {
        // Get transaction
        System.out.println("Transaction ID\t\tDate\t\t\tType\t\tAmount\t\tNote\t\tIs Recurring\t\tRecurring Days\t\tCategory ID");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");

        // Read the transactions from the file transactionFile
        try (Scanner myReader = new Scanner(transactionFile)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                // Split the data into an array
                String[] dataArray = data.split(",");
                System.out.println(dataArray[0] + "\t\t\t" + dataArray[1] + "\t\t" + 
                dataArray[2] + "\t\t" + dataArray[3] + "\t\t" + dataArray[4] + "\t\t" + dataArray[5] + "\t\t\t" + dataArray[6] + "\t\t\t" + dataArray[7]);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 8 - View all categories
    public static void showCategories(File categoryFile) {
        // Get category
        System.out.println("Category ID\t\tCategory Name\t\tBudgeted Amount");
        System.out.println("------------------------------------------------------------------");

        // Read the categories from the file categoryFile
        try (Scanner myReader = new Scanner(categoryFile)) {
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
