import java.time.LocalDate;

public class Loan extends Product {

    private LocalDate returnDate;
    private Boolean isAvailable; 

    
    public Loan(int productId, AuthenticatedStudent owner, String productName, String productDesc, LocalDate returnDate) {
        super(productId, owner, productName, productDesc);
        if (returnDate == null || returnDate.isBefore(postedDate)) {
            throw new IllegalArgumentException("Return date must not be null and must be after the posted date.");
        }
        this.returnDate = returnDate;
        this.isAvailable = true;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        if (returnDate == null || returnDate.isBefore(this.postedDate)) {
            throw new IllegalArgumentException("Return date must not be null and must be after the posted date.");
        }
        this.returnDate = returnDate;
    }
    public boolean getAvailibility(){
        return this.isAvailable;
    }
    public void changeAvailibility(){
        if(this.isAvailable){
            this.isAvailable = false;
        }
        else{
            this.isAvailable = true;
        }
    }


    // public void returnLoan() {
    //     if (this.getQuantity() > 0) {
    //         this.setQuantity(this.getQuantity() - 1);
    //         System.out.println("Loan returned successfully.");
    //     } else {
    //         System.out.println("No loans to return.");
    //     }
    // }


    @Override
    public void viewDetails() {
        System.out.println("Loan Details:");
        System.out.println("Product ID: " + this.productId);
        System.out.println("Owner: " + this.owner.getUserName());
        System.out.println("Loan Name: " + this.productName); 
        System.out.println("Description: " + this.productDesc); 
        System.out.println("Posted Date: " + this.postedDate); 
        System.out.println("Return Date: " + this.returnDate);
        System.out.println("Availability: " + (this.isAvailable ? "In Stock" : "Out of Stock"));
    }

    @Override
    public String toString() {
        return "Loan [productId=" + productId + ", owner=" + owner.getUserName() + ", productName=" + productName
                + ", productDesc=" + productDesc + ", postedDate=" + postedDate +
                 ", returnDate=" + returnDate + "]";
    }
}
