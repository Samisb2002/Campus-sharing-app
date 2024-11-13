import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CampusSharingApp {
    private static List<User> users = new ArrayList<>();
    private static ProductCatalog catalog = new ProductCatalog();
    private static List<Service> services = new ArrayList<>();
    private static User loggedInUser = null;

    public static void main(String[] args) {
        // Initialize catalog with some fake items
        catalog.addProduct(new Product("Laptop"));
        catalog.addProduct(new Product("Textbook"));
        catalog.addProduct(new Product("Calculator"));

        // Initialize some fake services
        services.add(new Service("Tutoring"));
        services.add(new Service("Tech Support"));
        services.add(new Service("Essay Review"));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to Campus Sharing App");
            System.out.println("1. Register");
            System.out.println("2. Log in");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    register(scanner);
                    break;
                case 2:
                    login(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (loggedInUser != null) {
                userMenu(scanner);
                loggedInUser = null;
            }
        }
    }

    private static void register(Scanner scanner) {
        System.out.print("Enter username to register: ");
        String username = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists. Please try logging in.");
                return;
            }
        }

        User newUser = new AuthenticatedStudent(username);
        users.add(newUser);
        System.out.println("Registration successful. You can now log in.");
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter username to log in: ");
        String username = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                loggedInUser = user;
                System.out.println("Login successful.");
                return;
            }
        }

        System.out.println("Username not found. Please register first.");
    }

    private static void userMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nWelcome, " + loggedInUser.getUsername());
            System.out.println("1. Borrow an item");
            System.out.println("2. Offer an item");
            System.out.println("3. Get a service");
            System.out.println("4. Offer a service");
            System.out.println("5. View profile");
            System.out.println("6. Log out");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    borrowItem(scanner);
                    break;
                case 2:
                    offerItem(scanner);
                    break;
                case 3:
                    getService(scanner);
                    break;
                case 4:
                    offerService(scanner);
                    break;
                case 5:
                    viewProfile();
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void borrowItem(Scanner scanner) {
        System.out.println("Available items to borrow:");
        catalog.showProducts();

        System.out.print("Enter the name of the item to borrow: ");
        String itemName = scanner.nextLine();

        for (Product product : catalog.getProducts()) {
            if (product.getProductName().equalsIgnoreCase(itemName) && product.isAvailable()) {
                if (loggedInUser.useBanana()) {
                    product.borrow();
                    System.out.println("You have successfully borrowed the " + itemName);
                    return;
                } else {
                    System.out.println("Insufficient bananas to borrow an item.");
                    return;
                }
            }
        }
        System.out.println("Item not found or unavailable.");
    }

    private static void offerItem(Scanner scanner) {
        System.out.print("Enter the name of the item to offer: ");
        String itemName = scanner.nextLine();

        Product newProduct = new Product(itemName);
        catalog.addProduct(newProduct);
        loggedInUser.addBanana();
        System.out.println("Item offered successfully and added to catalog.");
    }

    private static void getService(Scanner scanner) {
        System.out.println("Available services:");
        for (Service service : services) {
            System.out.println("- " + service.getName());
        }

        System.out.print("Enter the name of the service to request: ");
        String serviceName = scanner.nextLine();

        for (Service service : services) {
            if (service.getName().equalsIgnoreCase(serviceName)) {
                if (loggedInUser.useBanana()) {
                    System.out.println("You have successfully requested the " + serviceName + " service.");
                    return;
                } else {
                    System.out.println("Insufficient bananas to request a service.");
                    return;
                }
            }
        }
        System.out.println("Service not found.");
    }

    private static void offerService(Scanner scanner) {
        System.out.print("Enter the name of the service to offer: ");
        String serviceName = scanner.nextLine();

        Service newService = new Service(serviceName);
        services.add(newService);
        loggedInUser.addBanana();
        System.out.println("Service offered successfully.");
    }

    private static void viewProfile() {
        System.out.println("Username: " + loggedInUser.getUsername());
        System.out.println("Bananas: " + loggedInUser.getBananas());
    }
}
