// Service.java
public class Service extends Product {
    private int duration;

    public Service(int productId, AuthenticatedStudent owner, String productName, String productDesc, int duration) {
        super(productId, owner, productName, productDesc);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public void viewDetails() {
        System.out.println("Service Details:");
        System.out.println("ID: " + productId);
        System.out.println("Name: " + productName);
        System.out.println("Description: " + productDesc);
        System.out.println("Duration: " + duration + " days");
        System.out.println("Available: " + isAvailable);
    }

    @Override
    public String toCSVString() {
        return productId + "," + owner.getUserId() + ",Service," +
               productName + "," + productDesc + "," + duration + "," + isAvailable;
    }
}
