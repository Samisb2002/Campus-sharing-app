import java.time.LocalDate;
import java.util.Scanner;

public class CampusSharingApp {

    public static void main(String[] args) {
        UserManager userManager = UserManager.getInstance();
        ProductCatalog productCatalog = new ProductCatalog();

        // Load initial data from CSV files
        userManager.loadUsersFromCSV("users.csv");
        productCatalog.loadProductsFromCSV("products.csv", userManager);

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        AuthenticatedStudent currentUser = null;

        while (!exit) {
            System.out.println("\n========== Campus Sharing App ==========");

            if (currentUser == null) {
                // When user is not logged in
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1: // Register
                        System.out.print("Enter your name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter your email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter your password: ");
                        String password = scanner.nextLine();
                        userManager.signup(name, email, password);
                        break;

                    case 2: // Login
                        System.out.print("Enter your email: ");
                        String loginEmail = scanner.nextLine();
                        System.out.print("Enter your password: ");
                        String loginPassword = scanner.nextLine();
                        AuthenticatedStudent loggedInUser =
                            userManager.login(loginEmail, loginPassword);
                        if (loggedInUser != null) {
                            currentUser = loggedInUser;
                            currentUser.setProductCatalog(productCatalog);
                            currentUser.loadUserProducts();
                            productCatalog.registerObserver(currentUser);
                            System.out.println("Welcome, " +
                                               currentUser.getUserName() +
                                               "!");
                        }
                        break;

                    case 0: // Exit
                        exit = true;
                        System.out.println("Thank you for using Campus " +
                                           "Sharing App!");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try " +
                                           "again.");
                }
            } else {
                // When user is logged in
                System.out.println("1. Post a Product");
                System.out.println("2. View Available Products");
                System.out.println("3. Request a Product");
                System.out.println("4. View Profile");
                System.out.println("5. Logout");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1: // Post a Product
                        System.out.println("\nChoose Product Type: ");
                        System.out.println("1. Service");
                        System.out.println("2. Loan");
                        System.out.println("3. Donation");
                        System.out.print("Your choice: ");
                        int productType = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        System.out.print("Enter Product Name: ");
                        String productName = scanner.nextLine();
                        System.out.print("Enter Product Description: ");
                        String productDesc = scanner.nextLine();

                        switch (productType) {
                            case 1: // Service
                            System.out.print("Enter Service Duration (in days): ");
                            int duration = scanner.nextInt();
                            scanner.nextLine(); 
                            currentUser.postProduct(productCatalog.generateNewProductId(),
                                productName, productDesc,
                                "Service", duration);
                                
                            break;

                            case 2: // Loan
                            System.out.print("Enter Return Date (YYYY-MM-DD): ");
                            String returnDateStr = scanner.nextLine();
                            LocalDate returnDate = LocalDate.parse(returnDateStr);
                            currentUser.postProduct(productCatalog.generateNewProductId(), 
                                productName, productDesc,
                                "Loan",returnDate);
                            break;

                            case 3: // Donation
                            System.out.print("Enter Pickup Location: ");
                            String pickupLocation = scanner.nextLine();
                            currentUser.postProduct(productCatalog.generateNewProductId(), 
                                productName, productDesc,
                                "Donation", pickupLocation);
                            break;

                            default:
                                System.out.println("Invalid product type.");
                        }
                        break;

                    case 2: // View Available Products
                        productCatalog.viewAvailableProducts();
                        break;

                    case 3: // Request a Product
                        System.out.print("Enter the name of the product " +
                                         "to request: ");
                        String requestedProductName = scanner.nextLine();
                        currentUser.requestProduct(requestedProductName);
                        break;

                    case 4: // View Profile
                        currentUser.getProfile().displayProfile();
                        break;

                    case 5: // Logout
                        productCatalog.removeObserver(currentUser);
                        userManager.saveUsersToCSV();
                        currentUser = null;
                        System.out.println("You have logged out.");
                        break;

                    case 0: // Exit
                        userManager.saveUsersToCSV();
                        exit = true;
                        System.out.println("Thank you for using Campus " +
                                           "Sharing App!");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try " +
                                           "again.");
                }
            }
        }
        scanner.close();
    }
}
