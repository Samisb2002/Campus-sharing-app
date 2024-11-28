// Donation.java
public class Donation extends Product {

    // Attribut spécifique à la donation
    private String pickupLocation;

    // Constructeur pour initialiser les propriétés d'une donation
    public Donation(int productId, AuthenticatedStudent owner, String productName, String productDesc, String pickupLocation) {
        // Appel au constructeur de la classe parent (Product) pour initialiser les propriétés communes
        super(productId, owner, productName, productDesc);
        this.pickupLocation = pickupLocation;  // Initialisation de la localisation de la collecte
    }

    // Getter pour obtenir la localisation de la collecte
    public String getPickupLocation() {
        return pickupLocation;
    }

    // Méthode pour afficher les détails d'une donation
    @Override
    public void viewDetails() {
        // Affichage des informations de la donation
        System.out.println("Donation Details:");
        System.out.println("ID: " + productId);
        System.out.println("Name: " + productName);
        System.out.println("Description: " + productDesc);
        System.out.println("Pickup Location: " + pickupLocation);
        System.out.println("Available: " + isAvailable);
    }

    // Méthode pour convertir la donation en une chaîne formatée en CSV
    @Override
    public String toCSVString() {
        // Retourne les données de la donation sous forme d'une ligne CSV
        return productId + "," + owner.getUserId() + ",Donation," +
               productName + "," + productDesc + "," + pickupLocation + "," + isAvailable;
    }
}
