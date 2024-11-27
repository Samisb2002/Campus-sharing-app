// Loan.java
import java.time.LocalDate;

public class Loan extends Product {
    private LocalDate returnDate;

    public Loan(int productId, AuthenticatedStudent owner, String productName, String productDesc, LocalDate returnDate) {
        super(productId, owner, productName, productDesc);
        this.returnDate = returnDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    @Override
    public void viewDetails() {
        System.out.println("Loan Details:");
        System.out.println("ID: " + productId);
        System.out.println("Name: " + productName);
        System.out.println("Description: " + productDesc);
        System.out.println("Return Date: " + returnDate);
        System.out.println("Available: " + isAvailable);
    }

    @Override
    public String toCSVString() {
        return productId + "," + owner.getUserId() + ",Loan," +
               productName + "," + productDesc + "," + returnDate + "," + isAvailable;
    }
}
