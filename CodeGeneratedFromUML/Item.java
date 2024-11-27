public class Item extends Product {
    private int quantity;

    public Item(int productId, AuthenticatedStudent owner, String productName, String productDesc, int quantity) {
        super(productId, owner, productName, productDesc);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public void viewDetails() {
        System.out.println("Item Details:");
        System.out.println("Product ID: " + getProductId());
        System.out.println("Owner: " + getOwner().getUserName());
        System.out.println("Name: " + getName());
        System.out.println("Description: " + getDescription());
        System.out.println("Quantity: " + quantity);
    }

    @Override
    public String toCSVString() {
        return getProductId() + "," + getOwner().getUserId() + ",Item," +
                getName() + "," + getDescription() + "," + quantity + "," + isAvailable();
    }
}
