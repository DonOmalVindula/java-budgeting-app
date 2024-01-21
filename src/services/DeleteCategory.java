package services;

import utils.DeleteRecord;

import java.io.*;
import java.util.Scanner;

public class DeleteCategory {
    // 7 - Delete a Category
    public static void deleteCategory(File categoryFile, Scanner scanner) {
        DeleteRecord.deleteRecord(categoryFile, scanner, "category", "Category");
    }
}
