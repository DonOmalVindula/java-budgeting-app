package test;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import services.EditTransaction;

import java.io.*;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditTransactionTest {

    private File categoryFile;
    private File transactionFile;
    private Scanner mockScanner;

    @BeforeEach
    public void setUp() throws IOException {
        // Create temporary files for testing
        categoryFile = File.createTempFile("categories", ".txt");
        transactionFile = File.createTempFile("transactions", ".txt");

        // Write some initial data to the transaction file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(transactionFile))) {
            writer.write("1,100.0,EXPENSE,Test Note,false,0,1\n");
            writer.write("2,200.0,INCOME,Another Note,false,0,2\n");
        }

        // Mock Scanner for user input
        mockScanner = Mockito.mock(Scanner.class);
        Mockito.when(mockScanner.nextInt()).thenReturn(1, 1); // Mocking transaction ID and transaction type (EXPENSE)
        Mockito.when(mockScanner.nextDouble()).thenReturn(150.0); // Mocking edited amount
        Mockito.when(mockScanner.next()).thenReturn("Edited Note"); // Mocking edited note
    }

    @AfterEach
    public void tearDown() {
        categoryFile.delete();
        transactionFile.delete();
    }

    @Test
    public void testEditTransaction() throws IOException {
        // Call the method to test
        EditTransaction.editTransaction(categoryFile, transactionFile, mockScanner);

        // Verify the changes in the transaction file
        try (BufferedReader reader = new BufferedReader(new FileReader(transactionFile))) {
            String firstLine = reader.readLine();
            assertTrue(firstLine.contains("150.0"), "First transaction amount should be edited to 150.0");
            assertTrue(firstLine.contains("Edited Note"), "First transaction note should be 'Edited Note'");
            String secondLine = reader.readLine();
            assertTrue(secondLine.contains("200.0"), "Second transaction should remain unchanged");
        }
    }
}
