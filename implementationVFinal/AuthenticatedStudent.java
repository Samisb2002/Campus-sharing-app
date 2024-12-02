// AuthenticatedStudent.java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AuthenticatedStudent extends User implements Observer {
    // Attributs de la classe
    public ScoreManager scoreManager;
    private Profile profile;
    public List<Product> postedProducts = new ArrayList<>(); // Liste des produits publiés par l'utilisateur
    public List<Product> requestedProducts = new ArrayList<>(); // Liste des produits demandés par l'utilisateur

    // Fichiers de produits
    private String postedProductsFile = "user_posted_products.csv";
    private String requestedProductsFile = "user_requested_products.csv";

    // Constructeur de la classe AuthenticatedStudent
    public AuthenticatedStudent(Integer userId, String name, String email,
                                String password) {
        super(userId, name, email, password); // Appel au constructeur de la classe parente User
        this.profile = new Profile(this); // Initialisation du profil
        this.scoreManager = new ScoreManager(); // Initialisation du gestionnaire de scores
    }

    // Getter pour le profil de l'utilisateur
    public Profile getProfile() {
        return profile;
    }

    // Cette fonction aurait pu être dans la classe CSVUtils
    // Méthode pour charger les produits de l'utilisateur
    public void loadUserProducts() {
        // Charger les produits publiés
        try (BufferedReader br = new BufferedReader(
                new FileReader(postedProductsFile))) { // Lecture du fichier des produits publiés
            String line;
            boolean firstLine = true; // Pour ignorer la première ligne (en-tête)
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Ignore les lignes vides
                }
                if (firstLine) {
                    firstLine = false;
                    continue; // Ignore la première ligne (en-tête)
                }
                String[] fields = line.split(","); // Séparation des champs
                if (fields.length == 2) {
                    int userId = Integer.parseInt(fields[0].trim());
                    int productId = Integer.parseInt(fields[1].trim());
                    if (userId == this.userId) { // Vérifie si l'utilisateur correspond à celui actuel
                        Product product = CSVUtils.getProductById("products.csv", productId); // Récupère le produit par ID
                        if (product != null) {
                            postedProducts.add(product); // Ajoute le produit à la liste
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading posted products: " +
                               e.getMessage()); // Gère les erreurs de lecture du fichier
        }

        // Charger les produits demandés
        try (BufferedReader br = new BufferedReader(
                new FileReader(requestedProductsFile))) { // Lecture du fichier des produits demandés
            String line;
            boolean firstLine = true; // Pour ignorer la première ligne (en-tête)
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // Ignore les lignes vides
                }
                if (firstLine) {
                    firstLine = false;
                    continue; // Ignore la première ligne (en-tête)
                }
                String[] fields = line.split(","); // Séparation des champs
                if (fields.length == 2) {
                    int userId = Integer.parseInt(fields[0].trim());
                    int productId = Integer.parseInt(fields[1].trim());
                    if (userId == this.userId) { // Vérifie si l'utilisateur correspond à celui actuel
                        Product product = CSVUtils.getProductById("products.csv", productId); // Récupère le produit par ID
                        if (product != null) {
                            requestedProducts.add(product); // Ajoute le produit à la liste
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading requested products: " +
                               e.getMessage()); // Gère les erreurs de lecture du fichier
        }
    }

    // Cohérent avec le diagramme de séquence
    // Méthode pour demander un produit
    public void requestProduct(String productName) {
        Product requestedProduct = CSVUtils.getProductByName("products.csv", productName);
    
        if (requestedProduct != null && requestedProduct.isAvailable()) {
            if (this.scoreManager.getScore() > 0) {
                ProductCatalog.getInstance().updateProductAvailability(requestedProduct, false);
    
                requestedProducts.add(requestedProduct);
    
                
                CSVUtils.appendUserProductToCSV(requestedProductsFile, this.userId, requestedProduct.getProductId());
    
                this.scoreManager.decrementScore();
                System.out.println("You have successfully requested the product: " + requestedProduct.getName());
                System.out.println("Your new Banana Score: " + this.scoreManager.getScore());
            } else {
                System.out.println("You do not have enough Banana Score to request a product.");
            }
        } else {
            System.out.println("Product not found or not available.");
        }
    }
    

    // Méthode pour publier un produit
    public void postProduct(int productId, String productName, String productDesc, String type, Object... additionalArgs) {
        Object specificField = additionalArgs[0];
    
        // Convert non-String types to String
        if (specificField instanceof LocalDate) {
            specificField = specificField.toString(); // Convert LocalDate to String
        } else if (specificField instanceof Integer) {
            specificField = String.valueOf(specificField); // Convert Integer to String
        }
    
        // Create the product using the factory
        Product product = ProductFactory.createProduct(type, productId, this, productName, productDesc, specificField);
    
        if (product != null) {
            System.out.println("Product created and posted: " + product.getName() + " by " + this.getUserName());
            
            // Add the product to the ProductCatalog
            ProductCatalog.getInstance().addProduct(product);
    
            // Persist the product to products.csv
            CSVUtils.appendProductToCSV("products.csv", product);
    
            // Link the product to the user in the postedProductsFile
            CSVUtils.appendUserProductToCSV(postedProductsFile, this.userId, product.getProductId());           
            postedProducts.add(product);
            // Update the Banana Score
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
                           ", the product catalog has been updated!"); // Notifie l'utilisateur d'une mise à jour du catalogue
    }
}
