import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthenticatedStudent extends User implements Observer {
    public ScoreManager scoreManager;
    private Profile profile;
    public List<Product> postedProducts = new ArrayList<>();
    public List<Product> requestedProducts = new ArrayList<>();
    private ProductCatalog productCatalog;

    private String postedProductsFile = "user_posted_products.csv";
    private String requestedProductsFile = "user_requested_products.csv";

    public AuthenticatedStudent(Integer userId, String name, String email,
                                String password) {
        super(userId, name, email, password);
        this.profile = new Profile(this);
        this.scoreManager = new ScoreManager();
    }

    public void setProductCatalog(ProductCatalog catalog) {
        this.productCatalog = catalog;
    }

    public Profile getProfile() {
        return profile;
    }

    public void loadUserProducts() {
        // Load posted products
        try (BufferedReader br = new BufferedReader(
                new FileReader(postedProductsFile))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                if (firstLine) {
                    firstLine = false; // Skip header
                    continue;
                }
                String[] fields = line.split(",");
                if (fields.length == 2) {
                    int userId = Integer.parseInt(fields[0].trim());
                    int productId = Integer.parseInt(fields[1].trim());
                    if (userId == this.userId) {
                        Product product = productCatalog.getProductById(
                            productId);
                        if (product != null) {
                            postedProducts.add(product);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading posted products: " +
                               e.getMessage());
        }

        // Load requested products
        try (BufferedReader br = new BufferedReader(
                new FileReader(requestedProductsFile))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                if (firstLine) {
                    firstLine = false; // Skip header
                    continue;
                }
                String[] fields = line.split(",");
                if (fields.length == 2) {
                    int userId = Integer.parseInt(fields[0].trim());
                    int productId = Integer.parseInt(fields[1].trim());
                    if (userId == this.userId) {
                        Product product = productCatalog.getProductById(
                            productId);
                        if (product != null) {
                            requestedProducts.add(product);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading requested products: " +
                               e.getMessage());
        }
    }

    public void requestProduct(String productName) {
        Product requestedProduct = null;
        for (Product product : productCatalog.getProducts()) {
            if (product.getName().equalsIgnoreCase(productName)) {
                requestedProduct = product;
                break;
            }
        }

        if (requestedProduct != null) {
            if (this.scoreManager.getScore() > 0) {
                productCatalog.updateProductAvailability(requestedProduct,
                                                         false);
                requestedProducts.add(requestedProduct);
                CSVUtils.appendUserProductToCSV(requestedProductsFile,
                    this.userId, requestedProduct.getProductId());
                this.scoreManager.decrementScore();
                System.out.println("You have successfully requested the " +
                                   "product: " + requestedProduct.getName());
                System.out.println("Your new Banana Score: " +
                                   this.scoreManager.getScore());
            } else {
                System.out.println("You do not have enough Banana Score to " +
                                   "request a product.");
            }
        } else {
            System.out.println("Product not found or not available.");
        }
    }

    public void postProduct(int productId,String productName, String productDesc, String type, Object... additionalArgs) {
    
        Product product = ProductFactory.createProduct(type,productId, this,productName, productDesc, additionalArgs);

        if (product != null) {
            System.out.println("Product created and posted: " + product.getName() + " by " + this.getUserName());
            postedProducts.add(product);
            CSVUtils.appendProductToCSV("products.csv", product);
            CSVUtils.appendUserProductToCSV(postedProductsFile, this.userId, product.getProductId());
            this.scoreManager.incrementScore();
            System.out.println("Your Banana Score has increased!");
            System.out.println("Your new Banana Score: " + this.scoreManager.getScore());
        } else {
            System.out.println("Failed to create the product. Please check the type and input parameters.");
        }
    }

    @Override
    public void update() {
        System.out.println("\n[Notification] Dear " + this.getUserName() +
                           ", the product catalog has been updated!");
    }
}
