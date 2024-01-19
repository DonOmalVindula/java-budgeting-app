package services;

import models.Transaction;

import java.io.*;
import java.util.Scanner;

import static utils.GenerateTransactionViaUserInput.generateTransactionViaUserInput;

public class EditTransaction {
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
}
