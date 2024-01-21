package services;

import utils.DeleteRecord;

import java.io.*;
import java.util.Scanner;

public class DeleteTransaction {
    // 3 - Delete a transaction
    public static void deleteTransaction(File transactionFile, Scanner scanner) {
        DeleteRecord.deleteRecord(transactionFile, scanner, "transaction", "Transaction");
    }
}
