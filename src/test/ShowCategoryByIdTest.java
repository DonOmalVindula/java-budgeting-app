package test;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import services.ShowCategoryById;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShowCategoryByIdTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private File categoryFile;
    private Scanner mockScanner;

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

        // Mock Scanner for user input
        mockScanner = Mockito.mock(Scanner.class);
        Mockito.when(mockScanner.nextInt()).thenReturn(1); // Mocking category ID to view
    }

    @AfterEach
    public void restoreStreams() {
        // Restore the original System.out
        System.setOut(originalOut);
        categoryFile.delete();
    }

    @Test
    public void testShowCategoryById() {
        // Call the method to test
        ShowCategoryById.showCategoryById(categoryFile, mockScanner);

        // Verify the output
        String output = outContent.toString();
        assertTrue(output.contains("1\t\t\t\t\t\tFood\t\t\t\t500.0"));
    }

    @Test
    public void testCategoryNotFound() {
        // Change the mocked input to an ID that doesn't exist
        Mockito.when(mockScanner.nextInt()).thenReturn(3);

        // Call the method to test
        ShowCategoryById.showCategoryById(categoryFile, mockScanner);

        // Verify the output for not found category
        String output = outContent.toString();
        assertTrue(output.contains("Category with ID 3 not found."));
    }
}

