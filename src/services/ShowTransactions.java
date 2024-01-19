package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ShowTransactions {
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
                System.out.println(STR."\{dataArray[0]}\t\t\t\{dataArray[1]}\t\t\{dataArray[2]}\t\t\{dataArray[3]}\t\t\{dataArray[4]}\t\t\{dataArray[5]}\t\t\t\{dataArray[6]}\t\t\t\{dataArray[7]}");
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
