public class Donation extends Product {
    private String pickupLocation;

    public Donation(int productId, AuthenticatedStudent owner,
                    String productName, String productDesc,
                    String pickupLocation) {
        super(productId, owner, productName, productDesc);
        if (pickupLocation == null || pickupLocation.isEmpty()) {
            throw new IllegalArgumentException("Pickup location must not " +
                                               "be null or empty.");
        }
        this.pickupLocation = pickupLocation;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        if (pickupLocation == null || pickupLocation.isEmpty()) {
            throw new IllegalArgumentException("Pickup location must not " +
                                               "be null or empty.");
        }
        this.pickupLocation = pickupLocation;
    }

    @Override
    public void viewDetails() {
        System.out.println("Donation Details:");
        System.out.println("Product ID: " + this.productId);
        System.out.println("Owner: " + this.owner.getUserName());
        System.out.println("Product Name: " + this.productName);
        System.out.println("Description: " + this.productDesc);
        System.out.println("Posted Date: " + this.postedDate);
        System.out.println("Pickup Location: " + this.pickupLocation);
        System.out.println("Available: " + this.isAvailable);
    }

    @Override
    public String toString() {
        return "Donation [productId=" + productId + ", owner=" +
               owner.getUserName() + ", productName=" + productName +
               ", productDesc=" + productDesc + ", postedDate=" +
               postedDate + ", pickupLocation=" + pickupLocation + "]";
    }
}
