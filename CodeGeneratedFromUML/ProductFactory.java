import java.time.LocalDate;

public class ProductFactory {
    public static Product createProduct(String type, int productId, AuthenticatedStudent owner,String productName, String productDesc, Object... args) {
        switch (type) {
            case "Service":
                // Args: duration
                return new Service(productId, owner,productName, productDesc, (int) args[0]);
            case "Donation":
                // Args: pickupLocation
                return new Donation(productId, owner,productName, productDesc, (String) args[0]);
            case "Loan":
                // Args: returnDate
                return new Loan(productId, owner,productName, productDesc, (LocalDate) args[0]);
            default:
                throw new IllegalArgumentException("Unknown product type: " + type);
        }
    }
}
