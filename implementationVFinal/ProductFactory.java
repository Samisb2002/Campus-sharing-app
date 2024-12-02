import java.time.LocalDate;

// Bien, il aurait (idéalement) fallu rajouter une interface factory afin de pouvoir accueillir de nouvelles
// factory concrètes comme celle de ce fichier.
// voir https://moodle.univ-smb.fr/pluginfile.php/111840/mod_resource/content/1/INFO732_CM2_DesignPatterns_2020.pdf pages 25-31

public class ProductFactory {
    // Méthode statique pour créer un produit en fonction de son type
    public static Product createProduct(String type, int productId, AuthenticatedStudent owner, String productName, String productDesc, Object... additionalArgs) {
        // Switch en fonction du type de produit
        switch (type.toLowerCase()) {
            case "service":
                // Création d'un produit de type Service
                int duration = Integer.parseInt((String) additionalArgs[0]); // Convertir le premier argument en int
                return new Service(productId, owner, productName, productDesc, duration);

            case "donation":
                // Création d'un produit de type Donation
                String pickupLocation = (String) additionalArgs[0]; // Récupérer le premier argument comme String
                return new Donation(productId, owner, productName, productDesc, pickupLocation);

            case "loan":
                // Création d'un produit de type Loan
                LocalDate returnDate = LocalDate.parse((String) additionalArgs[0]); // Convertir le premier argument en LocalDate
                return new Loan(productId, owner, productName, productDesc, returnDate);

            default:
                // Si le type n'est pas reconnu, lever une exception
                throw new IllegalArgumentException("Unknown product type: " + type);
        }
    }
}

