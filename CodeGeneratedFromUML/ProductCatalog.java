// ProductCatalog.java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductCatalog implements Subject {
    // Instance unique de ProductCatalog (Singleton)
    private static ProductCatalog instance;
    
    // Liste de tous les produits (y compris ceux qui ne sont pas disponibles)
    private List<Product> allProducts;
    
    // Liste des produits disponibles
    private List<Product> availableProducts;
    
    // Liste des observateurs
    private List<Observer> observers;

    // Constructeur privé pour implémenter le Singleton
    private ProductCatalog() {
        allProducts = new ArrayList<>();
        availableProducts = new ArrayList<>();
        observers = new ArrayList<>();
    }

    // Méthode pour obtenir l'instance unique de ProductCatalog (Singleton)
    public static synchronized ProductCatalog getInstance() {
        if (instance == null) {
            instance = new ProductCatalog();
        }
        return instance;
    }

    // Getter pour obtenir la liste des produits disponibles
    public List<Product> getProducts() {
        return availableProducts;
    }

    // Générer un nouvel ID de produit basé sur le plus grand ID existant
    public int generateNewProductId() {
        int maxId = 0;
        for (Product product : allProducts) {
            if (product.getProductId() > maxId) {
                maxId = product.getProductId();
            }
        }
        return maxId + 1;
    }

    // Charger les produits depuis un fichier CSV
    public void loadProductsFromCSV(String filename, UserManager userManager) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;
            
            // Parcourir chaque ligne du fichier CSV
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Ignorer la première ligne (en-tête)
                }
                String[] fields = line.split(",");
                
                // Vérifier qu'il y a suffisamment de champs
                if (fields.length >= 7) {
                    int productId = Integer.parseInt(fields[0]);
                    int ownerId = Integer.parseInt(fields[1]);
                    String productType = fields[2];
                    String productName = fields[3];
                    String productDesc = fields[4];
                    String specificField = fields[5];
                    boolean isAvailable = Boolean.parseBoolean(fields[6]);

                    // Récupérer le propriétaire du produit
                    AuthenticatedStudent owner = (AuthenticatedStudent)
                        userManager.getUserById(ownerId);

                    if (owner == null) {
                        continue; // Si le propriétaire est invalide, passer à la ligne suivante
                    }

                    // Créer le produit à partir de la fabrique de produits
                    Product product = ProductFactory.createProduct(
                        productType, productId, owner, productName, productDesc, specificField);
                    
                    // Ajouter le produit dans les listes appropriées
                    if (product != null) {
                        product.setAvailable(isAvailable);
                        allProducts.add(product);
                        if (isAvailable) {
                            availableProducts.add(product);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading products CSV: " +
                               e.getMessage());
        }
    }

    // Implémentation de l'interface Observer : inscrire un observateur
    @Override
    public void registerObserver(Observer observer) {
        if (!observers.contains(observer))
            observers.add(observer);
    }

    // Implémentation de l'interface Observer : retirer un observateur
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    // Implémentation de l'interface Observer : notifier tous les observateurs
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    // Ajouter un nouveau produit au catalogue
    public void addProduct(Product product) {
        allProducts.add(product);
        if (product.isAvailable()) {
            availableProducts.add(product);
        }
        notifyObservers(); // Notifier les observateurs qu'un produit a été ajouté
    }

    // Mettre à jour la disponibilité d'un produit
    public void updateProductAvailability(Product product, boolean isAvailable) {
        product.setAvailable(isAvailable);
        if (isAvailable) {
            if (!availableProducts.contains(product)) {
                availableProducts.add(product);
            }
        } else {
            availableProducts.remove(product);
        }
        // Mettre à jour le fichier CSV avec la nouvelle disponibilité
        CSVUtils.updateProductAvailabilityInCSV("products.csv",
                                                product.getProductId(),
                                                isAvailable);
        notifyObservers(); // Notifier les observateurs que la disponibilité a été mise à jour
    }

    // Filtrer les produits par catégorie (basé sur la description du produit)
    public List<Product> filterByCategory(String category) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : availableProducts) {
            if (product.getDescription().contains(category)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    // Obtenir un produit en utilisant son ID
    public Product getProductById(int productId) {
        for (Product product : allProducts) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null; // Si aucun produit avec cet ID n'est trouvé, retourner null
    }

    // Afficher la liste des produits disponibles dans la console
    public void viewAvailableProducts() {
        System.out.println("\nAvailable Products:");
        System.out.println("----------------------------------------------------------------");
        System.out.printf("%-5s %-10s %-15s %-20s %-30s%n", "ID", "Type",
                          "Owner", "Name", "Description");
        System.out.println("----------------------------------------------------------------");
        for (Product product : availableProducts) {
            String type = product.getClass().getSimpleName();
            System.out.printf("%-5d %-10s %-15s %-20s %-30s%n",
                              product.getProductId(), type,
                              product.getOwner().getUserName(),
                              product.getName(), product.getDescription());
        }
    }
}
