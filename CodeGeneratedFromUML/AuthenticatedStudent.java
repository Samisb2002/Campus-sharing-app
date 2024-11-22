public class AuthenticatedStudent extends User implements Observer {
    public ScoreManager scoreManager;
    private Profile profile;

    public AuthenticatedStudent(Integer userId, String name, String email, String password) {
        super(userId, name, email, password);
        this.profile = new Profile(this); 
    }

    public Profile getProfile() {
        return profile;
    }

    public void requestProduct() {
        System.out.println("Product requested by: " + this.getUserName());
    }


    public void postProduct(Product product) {
        System.out.println("Product posted: " + product.getName() + " by " + this.getUserName());
    }

    @Override
    public void update() {
        System.out.println("ProductCatalog has been updated");
    }

}