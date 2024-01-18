package services;

import models.Transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static utils.GenerateTransactionViaUserInput.generateTransactionViaUserInput;
import static utils.MainMenu.showMainMenu;

public class EditTransaction {
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
                System.out.println("models.Transaction found with ID: " + transactionId);
                Transaction transaction = generateTransactionViaUserInput(categoryFile);

                // Write the transaction to the file transactionFile
                fileContents = fileContents.replaceAll(matchedLine, transaction.getTransactionData(transactionId));
//                System.out.println("Testing....: "+ fileContents);
                try (FileWriter transcationWriter = new FileWriter(transactionFile)) {
                    transcationWriter.write(fileContents);
                    transcationWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("models.Transaction not found");
                showMainMenu();
            }
        }
    }
}
