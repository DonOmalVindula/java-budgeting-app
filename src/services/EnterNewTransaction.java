package services;

import models.Transaction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static utils.GenerateTransactionViaUserInput.generateTransactionViaUserInput;

public class EnterNewTransaction {
    // 1 - Add a new transaction
    public static void enterNewTransaction(File categoryFile, File transactionFile, Scanner scanner) {

        Transaction transaction = generateTransactionViaUserInput(categoryFile, transactionFile, scanner, 0);

        // Write the transaction to the file transactionFile
        try (FileWriter transcationWriter = new FileWriter(transactionFile, true)) {
            transcationWriter.write(transaction.getTransactionData() + "\n");
            transcationWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
