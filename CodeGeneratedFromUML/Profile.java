public class Profile {

    private AuthenticatedStudent owner;

    public Profile(AuthenticatedStudent owner) {
        this.owner = owner;
    }

    public void displayProfile() {
        System.out.println("\n===== Profile =====");
        System.out.println("Name: " + owner.getUserName());
        System.out.println("Email: " + owner.getUserEmail());
        System.out.println("Banana Score: " + owner.scoreManager.getScore());

        System.out.println("\n-- Posted Products --");
        if (owner.postedProducts.isEmpty()) {
            System.out.println("You have not posted any products.");
        } else {
            for (Product product : owner.postedProducts) {
                System.out.println("- " + product.getName() + " (" + product.getClass().getSimpleName() + ")");
            }
        }

        System.out.println("\n-- Requested Products --");
        if (owner.requestedProducts.isEmpty()) {
            System.out.println("You have not requested any products.");
        } else {
            for (Product product : owner.requestedProducts) {
                System.out.println("- " + product.getName() + " (" + product.getClass().getSimpleName() + ")");
            }
        }
        System.out.println("=======================");
    }

}
