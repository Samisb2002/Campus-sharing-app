// Donation.java
public class Donation extends Product {
    private String pickupLocation;

    public Donation(int productId, AuthenticatedStudent owner, String productName, String productDesc, String pickupLocation) {
        super(productId, owner, productName, productDesc);
        this.pickupLocation = pickupLocation;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    @Override
    public void viewDetails() {
        System.out.println("Donation Details:");
        System.out.println("ID: " + productId);
        System.out.println("Name: " + productName);
        System.out.println("Description: " + productDesc);
        System.out.println("Pickup Location: " + pickupLocation);
        System.out.println("Available: " + isAvailable);
    }

    @Override
    public String toCSVString() {
        return productId + "," + owner.getUserId() + ",Donation," +
               productName + "," + productDesc + "," + pickupLocation + "," + isAvailable;
    }
}
