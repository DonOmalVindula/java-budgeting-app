package test;

import org.junit.jupiter.api.*;
import services.ShowTransactions;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShowTransactionsTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private File transactionFile;

    @BeforeEach
    public void setUp() throws IOException {
        // Redirecting System.out to capture console output
        System.setOut(new PrintStream(outContent));

        // Create a temporary file for testing
        transactionFile = File.createTempFile("transactions", ".txt");

        // Write some initial data to the transaction file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(transactionFile))) {
            writer.write("1,2023-01-01,EXPENSE,100.0,Test Note,false,0,1\n");
            writer.write("2,2023-01-02,INCOME,200.0,Another Note,true,30,2\n");
        }
    }

    @AfterEach
    public void restoreStreams() {
        // Restore the original System.out
        System.setOut(originalOut);
        transactionFile.delete();
    }

    @Test
    public void testShowTransactions() {
        // Call the method to test
        ShowTransactions.showTransactions(transactionFile);

        // Verify the output
        String output = outContent.toString();
        assertTrue(output.contains("Transaction ID\t\tDate\t\t\tType\t\tAmount\t\tNote\t\tIs Recurring\t\tRecurring Days\t\tCategory ID"));
        assertTrue(output.contains("1\t\t\t2023-01-01\t\tEXPENSE\t\t100.0\t\tTest Note\t\tfalse\t\t\t0\t\t\t1"));
        assertTrue(output.contains("2\t\t\t2023-01-02\t\tINCOME\t\t200.0\t\tAnother Note\t\ttrue\t\t\t30\t\t\t2"));
    }
}
