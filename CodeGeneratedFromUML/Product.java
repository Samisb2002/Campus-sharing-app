// Product.java
import java.time.LocalDate;

public abstract class Product {
    // ID unique du produit
    protected int productId;
    
    // Propriétaire du produit (étudiant authentifié)
    protected AuthenticatedStudent owner;
    
    // Nom du produit
    protected String productName;
    
    // Description du produit
    protected String productDesc;
    
    // Date de publication du produit
    protected LocalDate postedDate;
    
    // Statut de disponibilité du produit
    protected boolean isAvailable;

    // Constructeur de la classe Product
    public Product(int productId, AuthenticatedStudent owner,
                   String productName, String productDesc) {
        // Validation des entrées : ID, propriétaire, nom et description du produit
        if (productId <= 0)
            throw new IllegalArgumentException("Product ID must be positive.");
        if (owner == null)
            throw new IllegalArgumentException("Owner cannot be null.");
        if (productName == null || productName.isEmpty())
            throw new IllegalArgumentException("Product name cannot be null or empty.");
        if (productDesc == null || productDesc.isEmpty())
            throw new IllegalArgumentException("Product description cannot be null or empty.");

        // Initialisation des variables d'instance
        this.productId = productId;
        this.owner = owner;
        this.productName = productName;
        this.productDesc = productDesc;
        this.postedDate = LocalDate.now();  // Date de publication = date actuelle
        this.isAvailable = true;  // Par défaut, le produit est disponible
    }

    // Getter pour obtenir l'ID du produit
    public int getProductId() {
        return this.productId;
    }

    // Getter pour obtenir le nom du produit
    public String getName() {
        return this.productName;
    }

    // Getter pour obtenir la description du produit
    public String getDescription() {
        return this.productDesc;
    }

    // Getter pour obtenir la date de publication
    public LocalDate getDate() {
        return this.postedDate;
    }

    // Getter pour obtenir le propriétaire du produit
    public AuthenticatedStudent getOwner() {
        return this.owner;
    }

    // Getter pour vérifier si le produit est disponible
    public boolean isAvailable() {
        return isAvailable;
    }

    // Setter pour modifier la disponibilité du produit
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    // Méthode abstraite pour afficher les détails du produit (implémentation spécifique dans les sous-classes)
    public abstract void viewDetails();

    // Méthode abstraite pour convertir le produit en chaîne CSV (implémentation spécifique dans les sous-classes)
    public abstract String toCSVString();
}
