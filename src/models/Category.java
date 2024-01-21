package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Category {
    private int id = 5;
    private String name;
    private double budgetAmount;

    public Category(String name, double budgetAmount) {
        this.id = generateNextId();
        this.name = name;
        this.budgetAmount = budgetAmount;
    }

    private int generateNextId() {
        // Read the maximum ID from the file
        int maxId = 0;
        try (Scanner scanner = new Scanner(new File("data/categories.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int currentId = Integer.parseInt(parts[0].trim());
                if (currentId > maxId) {
                    maxId = currentId;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Set the next ID as one more than the maximum ID in the file
        return maxId + 1;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public String getCategoryData() {
    return Integer.toString(id) + "," + this.name.toString() + "," + this.budgetAmount ;
    }
}
