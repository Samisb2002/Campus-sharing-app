import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductCatalog implements Subject {
    private List<Product> allProducts;
    private List<Product> availableProducts;
    private List<Observer> observers;

    public ProductCatalog() {
        allProducts = new ArrayList<>();
        availableProducts = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return availableProducts;
    }

    public int generateNewProductId() {
        int maxId = 0;
        for (Product product : allProducts) {
            if (product.getProductId() > maxId) {
                maxId = product.getProductId();
            }
        }
        return maxId + 1;
    }

    public void loadProductsFromCSV(String filename, UserManager userManager) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip header line
                    continue;
                }
                String[] fields = line.split(",");
                if (fields.length >= 7) {
                    int productId = Integer.parseInt(fields[0]);
                    int ownerId = Integer.parseInt(fields[1]);
                    String productType = fields[2];
                    String productName = fields[3];
                    String productDesc = fields[4];
                    String specificField = fields[5];
                    boolean isAvailable = Boolean.parseBoolean(fields[6]);

                    AuthenticatedStudent owner = (AuthenticatedStudent)
                        userManager.getUserById(ownerId);

                    if (owner == null) {
                        System.out.println("Owner with ID " + ownerId +
                                           " not found.");
                        continue;
                    }

                    Product product = null;
                    switch (productType.toLowerCase()) {
                        case "service":
                            int duration = Integer.parseInt(specificField);
                            product = new Service(productId, owner, productName,
                                                  productDesc,
                                                  duration);
                            break;
                        case "loan":
                            LocalDate returnDate = LocalDate.parse(
                                specificField);
                            product = new Loan(productId, owner, productName,
                                               productDesc, returnDate);
                            break;
                        case "donation":
                            String pickupLocation = specificField;
                            product = new Donation(productId, owner,
                                                   productName, productDesc,
                                                   pickupLocation);
                            break;
                        default:
                            System.out.println("Unknown product type: " +
                                               productType);
                            continue;
                    }

                    product.setAvailable(isAvailable);
                    allProducts.add(product);
                    if (isAvailable) {
                        availableProducts.add(product);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading products CSV: " +
                               e.getMessage());
        }
    }

    @Override
    public void registerObserver(Observer observer) {
        if (!observers.contains(observer))
            observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void addProduct(Product product) {
        allProducts.add(product);
        if (product.isAvailable()) {
            availableProducts.add(product);
        }
        System.out.println("Product added: " + product.getName());
        notifyObservers();
    }

    public void updateProductAvailability(Product product,
                                          boolean isAvailable) {
        product.setAvailable(isAvailable);
        if (isAvailable) {
            if (!availableProducts.contains(product)) {
                availableProducts.add(product);
            }
        } else {
            availableProducts.remove(product);
        }
        CSVUtils.updateProductAvailabilityInCSV("products.csv",
                                                product.getProductId(),
                                                isAvailable);
        notifyObservers();
    }

    public List<Product> filterByCategory(String category) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : availableProducts) {
            if (product.getDescription().contains(category)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    public Product getProductById(int productId) {
        for (Product product : allProducts) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null;
    }

    public void viewAvailableProducts() {
        System.out.println("\nAvailable Products:");
        System.out.println("------------------------------------------------" +
                           "----------------");
        System.out.printf("%-5s %-10s %-15s %-20s %-30s%n", "ID", "Type",
                          "Owner", "Name", "Description");
        System.out.println("------------------------------------------------" +
                           "----------------");
        for (Product product : availableProducts) {
            String type = product.getClass().getSimpleName();
            System.out.printf("%-5d %-10s %-15s %-20s %-30s%n",
                              product.getProductId(), type,
                              product.getOwner().getUserName(),
                              product.getName(), product.getDescription());
        }
    }
}
