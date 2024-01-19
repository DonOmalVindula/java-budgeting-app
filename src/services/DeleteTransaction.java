package services;

import java.io.*;
import java.util.Scanner;

public class DeleteTransaction {
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
}
