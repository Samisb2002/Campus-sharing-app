import java.time.LocalDate;

public class Service extends Product {

    private int duration; 

    public Service(int productId, AuthenticatedStudent owner, String productName, String productDesc, LocalDate postedDate, int duration) {
        super(productId, owner, productName, productDesc);
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be positive.");
        }
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be positive.");
        }
        this.duration = duration;
    }

    // Updates service details
    public void updateServiceDetails(String newProductName, String newProductDesc, int newDuration) {
        if (newProductName != null && !newProductName.isEmpty()) {
            this.productName = newProductName;
        }
        if (newProductDesc != null && !newProductDesc.isEmpty()) {
            this.productDesc = newProductDesc;
        }
        if (newDuration > 0) {
            this.duration = newDuration;
        }
    }

    @Override
    public void viewDetails() {
        System.out.println("Service Details:");
        System.out.println("Service ID: " + this.productId);
        System.out.println("Owner: " + this.owner.getUserName());
        System.out.println("Service Name: " + this.productName);
        System.out.println("Description: " + this.productDesc);
        System.out.println("Posted Date: " + this.postedDate);
        System.out.println("Duration: " + this.duration + " days");
    }

    @Override
    public String toString() {
        return "Service [productId=" + this.productId + ", owner=" + this.owner.getUserName() + ", productName=" + this.productName
                + ", productDesc=" + this.productDesc + ", postedDate=" + this.postedDate + ", duration=" + this.duration + "]";
    }
}
