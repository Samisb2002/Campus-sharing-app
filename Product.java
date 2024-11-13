public class Product {
    private String productName;
    private boolean isAvailable;

    public Product(String productName) {
        this.productName = productName;
        this.isAvailable = true;
    }

    public String getProductName() {
        return productName;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void borrow() {
        isAvailable = false;
    }

    public void returnProduct() {
        isAvailable = true;
    }
}
