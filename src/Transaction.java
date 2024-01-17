import java.text.SimpleDateFormat;
import java.util.Date;

enum TransactionType {
    INCOME,
    EXPENSE
}

public class Transaction {
    private int id = 0;
    private double amount;
    private TransactionType type;
    private String note;
    private boolean isRecurring;
    private int recurringDay;
    private int category;
    private Date date;

    public Transaction(double amount, TransactionType type, String note, boolean isRecurring, int recurringDay, int categoryId) {
        id++;
        this.amount = amount;
        this.type = type;
        this.note = note;
        this.isRecurring = isRecurring;
        this.recurringDay = recurringDay;
        this.category = categoryId;
        this.date = new Date();
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public String getNote() {
        return note;
    }
    

    public boolean isRecurring() {
        return isRecurring;
    }

    public int getRecurringDay() {
        return recurringDay;
    }

    public int getCategory() {
        return category;
    }

    public Date getDate() {
        return date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionData() {
        // Format date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

        return formatter.format(this.date) + "," + this.type.toString() + "," + this.amount + "," +
        this.note + "," + this.isRecurring + "," + Integer.toString(this.recurringDay) + "," + Integer.toString(this.category) + "\n";
    }
}
