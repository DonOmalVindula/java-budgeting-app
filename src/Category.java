import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Category {
    private int id = 0;
    private String name;
    private double budgetAmount;

    public Category(String name, double budgetAmount) {
        id++;
        this.name = name;
        this.budgetAmount = budgetAmount;
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
}
