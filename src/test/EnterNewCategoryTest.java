package test;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import services.EnterNewCategory;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class EnterNewCategoryTest {

    private File categoryFile;
    private Scanner mockScanner;

    @BeforeEach
    public void setUp() throws IOException {
        // Create temporary file for testing
        categoryFile = File.createTempFile("categories", ".txt");

        // Mock Scanner for user input
        mockScanner = Mockito.mock(Scanner.class);
        Mockito.when(mockScanner.next()).thenReturn("Food");
        Mockito.when(mockScanner.nextDouble()).thenReturn(500.0);
    }

    @AfterEach
    public void tearDown() {
        categoryFile.delete();
    }

    @Test
    public void testEnterNewCategory() throws IOException {
        // Call the method to test
        EnterNewCategory.enterNewCategory(categoryFile, mockScanner);

        // Verify the category is added to the file
        StringBuilder fileContents = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(categoryFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContents.append(line).append(System.lineSeparator());
            }
        }

        String expectedOutput = "7,Food,500.0" + System.lineSeparator(); // Adjust based on the actual format of Category.getCategoryData()
        assertEquals(expectedOutput, fileContents.toString(), "The category data should be written to the file");
    }
}
