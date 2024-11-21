

public class Item extends Product {

    private int quantity; 

    public Item(int productId, AuthenticatedStudent owner, String productName, String productDesc, int quantity) {
        super(productId, owner, productName, productDesc);
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }

    public boolean isAvailable() {
        return this.quantity > 0; 
    }

    public void updateItemDetails(String newProductName, String newProductDesc, int newQuantity) {
        if (newProductName != null && !newProductName.isEmpty()) {
            this.productName = newProductName;
        }
        if (newProductDesc != null && !newProductDesc.isEmpty()) {
            this.productDesc = newProductDesc;
        }
        if (newQuantity >= 0) {
            this.quantity = newQuantity;
        }
    }


    @Override
    public void viewDetails() {
        System.out.println("Item Details:");
        System.out.println("Item ID: " + this.productId); 
        System.out.println("Owner: " + this.owner.getUserName()); 
        System.out.println("Item Name: " + this.productName); 
        System.out.println("Description: " + this.productDesc); 
        System.out.println("Posted Date: " + this.postedDate);
        System.out.println("Quantity: " + this.quantity);
    }
}