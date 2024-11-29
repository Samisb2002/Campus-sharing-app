public class Service extends Product {
    private int duration;  // Durée du service en jours

    // Constructeur de la classe Service
    public Service(int productId, AuthenticatedStudent owner, String productName, String productDesc, int duration) {
        super(productId, owner, productName, productDesc);
        this.duration = duration;
    }

    // Méthode pour obtenir la durée du service
    public int getDuration() {
        return duration;
    }

    // Méthode pour afficher les détails du service
    @Override
    public void viewDetails() {
        System.out.println("Service Details:");
        System.out.println("ID: " + productId);
        System.out.println("Name: " + productName);
        System.out.println("Description: " + productDesc);
        System.out.println("Duration: " + duration + " days");
        System.out.println("Available: " + isAvailable);
    }

    // Méthode pour exporter les détails du service sous forme de chaîne CSV
    @Override
    public String toCSVString() {
        return productId + "," + owner.getUserId() + ",Service," +
               productName + "," + productDesc + "," + duration + "," + isAvailable;
    }
}
