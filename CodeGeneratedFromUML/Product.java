// Product.java
import java.time.LocalDate;

public abstract class Product {
    protected int productId;
    protected AuthenticatedStudent owner;
    protected String productName;
    protected String productDesc;
    protected LocalDate postedDate;
    protected boolean isAvailable;

    public Product(int productId, AuthenticatedStudent owner,
                   String productName, String productDesc) {
        if (productId <= 0)
            throw new IllegalArgumentException("Product ID must be positive.");
        if (owner == null)
            throw new IllegalArgumentException("Owner cannot be null.");
        if (productName == null || productName.isEmpty())
            throw new IllegalArgumentException("Product name cannot be null or empty.");
        if (productDesc == null || productDesc.isEmpty())
            throw new IllegalArgumentException("Product description cannot be null or empty.");

        this.productId = productId;
        this.owner = owner;
        this.productName = productName;
        this.productDesc = productDesc;
        this.postedDate = LocalDate.now();
        this.isAvailable = true;
    }

    public int getProductId() {
        return this.productId;
    }

    public String getName() {
        return this.productName;
    }

    public String getDescription() {
        return this.productDesc;
    }

    public LocalDate getDate() {
        return this.postedDate;
    }

    public AuthenticatedStudent getOwner() {
        return this.owner;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public abstract void viewDetails();

    public abstract String toCSVString();
}
