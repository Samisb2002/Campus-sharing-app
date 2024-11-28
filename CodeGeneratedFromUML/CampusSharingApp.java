// CampusSharingApp.java
import java.time.LocalDate;
import java.util.Scanner;

public class CampusSharingApp {

    public static void main(String[] args) {
        // Initialisation des gestionnaires d'utilisateurs et du catalogue de produits
        UserManager userManager = UserManager.getInstance();
        ProductCatalog productCatalog = ProductCatalog.getInstance();

        // Chargement des utilisateurs et des produits depuis les fichiers CSV
        userManager.loadUsersFromCSV("users.csv");
        productCatalog.loadProductsFromCSV("products.csv", userManager);

        // Initialisation du scanner pour les entrées utilisateur
        Scanner scanner = new Scanner(System.in);
        boolean exit = false; // Contrôle de la boucle principale
        AuthenticatedStudent currentUser = null; // Utilisateur authentifié actuel

        // Boucle principale de l'application
        while (!exit) {
            System.out.println("\n========== Campus Sharing App ==========");

            // Si aucun utilisateur n'est connecté, proposer des options d'inscription ou de connexion
            if (currentUser == null) {
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consommer le retour à la ligne

                switch (choice) {
                    case 1:
                        // Inscription d'un nouvel utilisateur
                        System.out.print("Enter your name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter your email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter your password: ");
                        String password = scanner.nextLine();
                        userManager.signup(name, email, password); // Inscription de l'utilisateur
                        break;

                    case 2:
                        // Connexion d'un utilisateur existant
                        System.out.print("Enter your email: ");
                        String loginEmail = scanner.nextLine();
                        System.out.print("Enter your password: ");
                        String loginPassword = scanner.nextLine();
                        AuthenticatedStudent loggedInUser =
                            userManager.login(loginEmail, loginPassword);
                        if (loggedInUser != null) {
                            // Connexion réussie
                            currentUser = loggedInUser;
                            currentUser.loadUserProducts(); // Charger les produits de l'utilisateur
                            productCatalog.registerObserver(currentUser); // Enregistrer l'utilisateur comme observateur
                            System.out.println("Welcome, " +
                                               currentUser.getUserName() + "!");
                        }
                        break;

                    case 0:
                        // Quitter l'application
                        exit = true;
                        System.out.println("Thank you for using Campus Sharing App!");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                // Menu principal pour l'utilisateur connecté
                System.out.println("1. Post a Product");
                System.out.println("2. View Available Products");
                System.out.println("3. Request a Product");
                System.out.println("4. View Profile");
                System.out.println("5. Logout");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consommer le retour à la ligne

                switch (choice) {
                    case 1:
                        // Publier un produit
                        System.out.println("\nChoose Product Type: ");
                        System.out.println("1. Service");
                        System.out.println("2. Loan");
                        System.out.println("3. Donation");
                        System.out.print("Your choice: ");
                        int productType = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Enter Product Name: ");
                        String productName = scanner.nextLine();
                        System.out.print("Enter Product Description: ");
                        String productDesc = scanner.nextLine();

                        // Créer et publier un produit selon le type choisi
                        switch (productType) {
                            case 1:
                                System.out.print("Enter Service Duration (in days): ");
                                int duration = scanner.nextInt();
                                scanner.nextLine(); // Consommer le retour à la ligne
                                currentUser.postProduct(productCatalog.generateNewProductId(),
                                    productName, productDesc, "Service", duration);
                                break;

                            case 2:
                                System.out.print("Enter Return Date (YYYY-MM-DD): ");
                                String returnDateStr = scanner.nextLine();
                                LocalDate returnDate = LocalDate.parse(returnDateStr);
                                currentUser.postProduct(productCatalog.generateNewProductId(),
                                    productName, productDesc, "Loan", returnDate);
                                break;

                            case 3:
                                System.out.print("Enter Pickup Location: ");
                                String pickupLocation = scanner.nextLine();
                                currentUser.postProduct(productCatalog.generateNewProductId(),
                                    productName, productDesc, "Donation", pickupLocation);
                                break;

                            default:
                                System.out.println("Invalid product type.");
                        }
                        break;

                    case 2:
                        // Voir les produits disponibles
                        productCatalog.viewAvailableProducts();
                        break;

                    case 3:
                        // Demander un produit
                        System.out.print("Enter the name of the product to request: ");
                        String requestedProductName = scanner.nextLine();
                        currentUser.requestProduct(requestedProductName);
                        break;

                    case 4:
                        // Voir le profil de l'utilisateur
                        currentUser.getProfile().displayProfile();
                        break;

                    case 5:
                        // Se déconnecter
                        productCatalog.removeObserver(currentUser); // Retirer l'observateur
                        userManager.saveUsersToCSV(); // Sauvegarder les utilisateurs
                        currentUser = null;
                        System.out.println("You have logged out.");
                        break;

                    case 0:
                        // Quitter l'application
                        userManager.saveUsersToCSV(); // Sauvegarder les utilisateurs avant de quitter
                        exit = true;
                        System.out.println("Thank you for using Campus Sharing App!");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
        scanner.close(); // Fermer le scanner pour libérer la ressource
    }
}
