// Loan.java
import java.time.LocalDate;

public class Loan extends Product {

    // Attribut spécifique à l'objet Loan : la date de retour
    private LocalDate returnDate;

    // Constructeur pour initialiser les propriétés d'un prêt
    public Loan(int productId, AuthenticatedStudent owner, String productName, String productDesc, LocalDate returnDate) {
        // Appel au constructeur de la classe parente Product pour initialiser les propriétés communes
        super(productId, owner, productName, productDesc);
        this.returnDate = returnDate;  // Initialisation de la date de retour
    }

    // Getter pour obtenir la date de retour du prêt
    public LocalDate getReturnDate() {
        return returnDate;
    }

    // Méthode pour afficher les détails du prêt
    @Override
    public void viewDetails() {
        // Affichage des informations du prêt
        System.out.println("Loan Details:");
        System.out.println("ID: " + productId);
        System.out.println("Name: " + productName);
        System.out.println("Description: " + productDesc);
        System.out.println("Return Date: " + returnDate);
        System.out.println("Available: " + isAvailable);
    }

    // Méthode pour convertir le prêt en une chaîne formatée en CSV
    @Override
    public String toCSVString() {
        // Retourne les données du prêt sous forme d'une ligne CSV
        return productId + "," + owner.getUserId() + ",Loan," +
               productName + "," + productDesc + "," + returnDate + "," + isAvailable;
    }
}
