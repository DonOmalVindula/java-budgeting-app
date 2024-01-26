package utils;

import models.Transaction;
import models.enums.TransactionType;

import java.io.File;
import java.util.Scanner;
import java.io.FileInputStream;

import static services.ShowCategories.showCategories;

public class GenerateTransactionViaUserInput {
    public static Transaction generateTransactionViaUserInput(File categoryFile, File transactionFile, Scanner scanner, int tid) {
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
                System.out.println("Please enter the recurring occurrence in days: ");
                recurringDay = scanner.nextInt();
            }

            // Find the last transaction ID
            int transactionId = 0;

            if (tid != 0) {
                transactionId = tid;
            } else {
                FileInputStream fis = new FileInputStream(transactionFile);
                byte[] byteArray = new byte[(int) transactionFile.length()];
                fis.read(byteArray);
                String data = new String(byteArray);
                String[] stringArray = data.split("\n");

                transactionId = stringArray.length + 1;
                fis.close();
            }

            // Create a new transaction
            Transaction transaction = new Transaction(transactionId, amount, type, note, isRecurring, recurringDay, categoryId);

            return transaction;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

