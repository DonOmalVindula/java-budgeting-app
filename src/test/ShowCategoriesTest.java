package test;

import org.junit.jupiter.api.*;
import services.ShowCategories;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShowCategoriesTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private File categoryFile;

    @BeforeEach
    public void setUp() throws IOException {
        // Redirecting System.out to capture console output
        System.setOut(new PrintStream(outContent));

        // Create a temporary file for testing
        categoryFile = File.createTempFile("categories", ".txt");

        // Write some initial data to the category file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(categoryFile))) {
            writer.write("1,Food,500.0\n");
            writer.write("2,Transport,300.0\n");
        }
    }

    @AfterEach
    public void restoreStreams() {
        // Restore the original System.out
        System.setOut(originalOut);
        categoryFile.delete();
    }

    @Test
    public void testShowCategories() {
        // Call the method to test
        ShowCategories.showCategories(categoryFile);

        // Verify the output
        String output = outContent.toString();
        assertTrue(output.contains("Category ID\t\tCategory Name\t\tBudgeted Amount"));
        assertTrue(output.contains("1\t\t\t\t\t\tFood\t\t\t\t500.0"));
        assertTrue(output.contains("2\t\t\t\t\t\tTransport\t\t\t\t300.0"));
    }
}
