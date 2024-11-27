import java.time.LocalDate;

public class ProductFactory {
    public static Product createProduct(String type, int productId, AuthenticatedStudent owner, String productName, String productDesc, Object... additionalArgs) {
        switch (type.toLowerCase()) {
            case "service":
                // Convert the String to an int
                int duration = Integer.parseInt((String) additionalArgs[0]);
                return new Service(productId, owner, productName, productDesc, duration);

            case "donation":
                // No conversion needed, additionalArgs[0] is already a String
                String pickupLocation = (String) additionalArgs[0];
                return new Donation(productId, owner, productName, productDesc, pickupLocation);

            case "loan":
                // Parse LocalDate from String
                LocalDate returnDate = LocalDate.parse((String) additionalArgs[0]);
                return new Loan(productId, owner, productName, productDesc, returnDate);

            default:
                throw new IllegalArgumentException("Unknown product type: " + type);
        }
    }
}
