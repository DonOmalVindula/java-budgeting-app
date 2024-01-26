package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import services.EnterNewTransaction;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnterNewTransactionTest {

    private File categoryFile;
    private File transactionFile;
    private Scanner mockScanner;

    @BeforeEach
    public void setUp() throws IOException {
        // Create temporary files for testing
        categoryFile = File.createTempFile("categories", ".txt");
        transactionFile = File.createTempFile("transactions", ".txt");

        // Mock Scanner for user input
        mockScanner = Mockito.mock(Scanner.class);
        Mockito.when(mockScanner.nextInt()).thenReturn(1, 100, 1); // Mocking multiple inputs: TransactionType.EXPENSE, amount, categoryId
        Mockito.when(mockScanner.nextDouble()).thenReturn(100.0); // Mocking amount input
        Mockito.when(mockScanner.next()).thenReturn("Test Note"); // Mocking note input
    }

    @AfterEach
    public void tearDown() {
        categoryFile.delete();
        transactionFile.delete();
    }

    @Test
    public void testEnterNewTransaction() throws IOException {
        // Call the method to test
        EnterNewTransaction.enterNewTransaction(categoryFile, transactionFile, mockScanner);

        // Verify that the transaction is correctly written to the file
        assertTrue(transactionFile.length() > 0, "Transaction file should not be empty after adding a new transaction");
        // You can add more detailed checks to verify the contents of the file
    }
}
