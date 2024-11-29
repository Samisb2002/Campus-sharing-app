// Item.java
public class Item extends Product {

    // Attribut spécifique à l'objet Item : la quantité de l'article
    private int quantity;

    // Constructeur pour initialiser les propriétés d'un item
    public Item(int productId, AuthenticatedStudent owner, String productName, String productDesc, int quantity) {
        // Appel au constructeur de la classe parente Product pour initialiser les propriétés communes
        super(productId, owner, productName, productDesc);
        this.quantity = quantity;  // Initialisation de la quantité
    }

    // Getter pour obtenir la quantité de l'item
    public int getQuantity() {
        return quantity;
    }

    // Setter pour modifier la quantité de l'item
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Méthode pour afficher les détails d'un item
    @Override
    public void viewDetails() {
        // Affichage des informations de l'item
        System.out.println("Item Details:");
        System.out.println("Product ID: " + getProductId());
        System.out.println("Owner: " + getOwner().getUserName());
        System.out.println("Name: " + getName());
        System.out.println("Description: " + getDescription());
        System.out.println("Quantity: " + quantity);
    }

    // Méthode pour convertir l'item en une chaîne formatée en CSV
    @Override
    public String toCSVString() {
        // Retourne les données de l'item sous forme d'une ligne CSV
        return getProductId() + "," + getOwner().getUserId() + ",Item," +
                getName() + "," + getDescription() + "," + quantity + "," + isAvailable();
    }
}
