package test;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import services.DeleteTransaction;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class DeleteTransactionTest {

    private File transactionFile;
    private Scanner mockScanner;

    @BeforeEach
    public void setUp() throws IOException {
        // Create a temporary file for testing
        transactionFile = File.createTempFile("transactions", ".txt");

        // Write some initial data to the transaction file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(transactionFile))) {
            writer.write("1,100.0,EXPENSE,Test Note,false,0,1\n");
            writer.write("2,200.0,INCOME,Another Note,false,0,2\n");
        }

        // Mock Scanner for user input
        mockScanner = Mockito.mock(Scanner.class);
        Mockito.when(mockScanner.nextInt()).thenReturn(1); // Mocking transaction ID to delete
    }

    @AfterEach
    public void tearDown() {
        transactionFile.delete();
    }

    @Test
    public void testDeleteTransaction() throws IOException {
        // Call the method to test
        DeleteTransaction.deleteTransaction(transactionFile, mockScanner);

        // Verify that the transaction is deleted from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(transactionFile))) {
            String line;
            boolean idFound = false;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("1,")) {
                    idFound = true;
                    break;
                }
            }
            assertFalse(idFound, "Transaction with ID 1 should be deleted");
        }
    }
}
