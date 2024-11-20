import java.util.ArrayList;
import java.util.List;

public class ProductCatalog {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void showProducts() {
        for (Product product : products) {
            System.out.println("Product: " + product.getProductName() + " - " + (product.isAvailable() ? "Available" : "Unavailable"));
        }
    }

   
    public List<Product> getProducts() {
        return products;
    }
}
