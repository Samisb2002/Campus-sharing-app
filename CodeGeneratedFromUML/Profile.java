public class Profile {

    private AuthenticatedStudent owner;  // Propriétaire du profil, un étudiant authentifié

    // Constructeur qui prend un AuthenticatedStudent en paramètre
    public Profile(AuthenticatedStudent owner) {
        this.owner = owner;
    }

    // Méthode pour afficher le profil de l'utilisateur
    public void displayProfile() {
        // Affichage des informations générales du profil
        System.out.println("\n===== Profile =====");
        System.out.println("Name: " + owner.getUserName());  // Affiche le nom de l'utilisateur
        System.out.println("Email: " + owner.getUserEmail());  // Affiche l'email de l'utilisateur
        System.out.println("Banana Score: " + owner.scoreManager.getScore());  // Affiche le score Banana (un score associé à l'utilisateur)

        // Affichage des produits publiés par l'utilisateur
        System.out.println("\n-- Posted Products --");
        if (owner.postedProducts.isEmpty()) {  // Si l'utilisateur n'a pas de produits publiés
            System.out.println("You have not posted any products.");
        } else {  // Sinon, affiche les produits publiés
            for (Product product : owner.postedProducts) {
                System.out.println("- " + product.getName() + " (" + product.getClass().getSimpleName() + ")");
            }
        }

        // Affichage des produits demandés par l'utilisateur
        System.out.println("\n-- Requested Products --");
        if (owner.requestedProducts.isEmpty()) {  // Si l'utilisateur n'a pas demandé de produits
            System.out.println("You have not requested any products.");
        } else {  // Sinon, affiche les produits demandés
            for (Product product : owner.requestedProducts) {
                System.out.println("- " + product.getName() + " (" + product.getClass().getSimpleName() + ")");
            }
        }
        System.out.println("=======================");
    }
}
