package test;

import models.Transaction;
import models.enums.TransactionType;
import utils.GenerateTransactionViaUserInput;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GenerateTransactionViaUserInputTest {

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

    @Test
    public void testGenerateTransactionViaUserInput() {
        // Call the method to test
        Transaction transaction = GenerateTransactionViaUserInput.generateTransactionViaUserInput(categoryFile, transactionFile, mockScanner, 0);

        // Assertions
        assertNotNull(transaction, "Transaction should not be null");
        assertEquals(100.0, transaction.getAmount(), "Amount should be 100");
        assertEquals(TransactionType.EXPENSE, transaction.getType(), "Transaction type should be EXPENSE");
        assertEquals("Test Note", transaction.getNote(), "Note should be 'Test Note'");
        // Add other assertions as needed
    }
}
