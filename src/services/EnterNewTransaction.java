package services;

import models.Transaction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static utils.CountLines.countLines;
import static utils.GenerateTransactionViaUserInput.generateTransactionViaUserInput;

public class EnterNewTransaction {
    // 1 - Add a new transaction
    public static void enterNewTransaction(File categoryFile, File transactionFile) {

        Transaction transaction = generateTransactionViaUserInput(categoryFile);
        // Write the transaction to the file transactionFile
        try (FileWriter transcationWriter = new FileWriter(transactionFile, true)) {
            int transactionId = countLines(transactionFile.getPath());
            transcationWriter.write(System.lineSeparator());
            transcationWriter.write(transaction.getTransactionData(transactionId + 1));
            System.out.println("New transaction submitted successfully!");
        } catch (IOException e) {
            System.out.println("Something went wrong!");
        }
    }
}
