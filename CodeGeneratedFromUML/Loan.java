import java.time.LocalDate;

public class Loan extends Product {
    private LocalDate returnDate;

    public Loan(int productId, AuthenticatedStudent owner,
                String productName, String productDesc,
                LocalDate returnDate) {
        super(productId, owner, productName, productDesc);
        if (returnDate == null || returnDate.isBefore(postedDate)) {
            throw new IllegalArgumentException("Return date must not be null " +
                                               "and must be after the " +
                                               "posted date.");
        }
        this.returnDate = returnDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        if (returnDate == null || returnDate.isBefore(this.postedDate)) {
            throw new IllegalArgumentException("Return date must not be null " +
                                               "and must be after the " +
                                               "posted date.");
        }
        this.returnDate = returnDate;
    }

    @Override
    public void viewDetails() {
        System.out.println("Loan Details:");
        System.out.println("Product ID: " + this.productId);
        System.out.println("Owner: " + this.owner.getUserName());
        System.out.println("Loan Name: " + this.productName);
        System.out.println("Description: " + this.productDesc);
        System.out.println("Posted Date: " + this.postedDate);
        System.out.println("Date de Retour: " + this.returnDate);
        System.out.println("Available: " + this.isAvailable);
    }

    @Override
    public String toString() {
        return "Loan [productId=" + productId + ", owner=" +
               owner.getUserName() + ", productName=" + productName +
               ", productDesc=" + productDesc + ", postedDate=" +
               postedDate + ", returnDate=" + returnDate + "]";
    }
}
