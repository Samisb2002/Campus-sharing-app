import java.util.ArrayList;
import java.util.List;

public class AuthenticatedStudent extends User implements Observer {
    public ScoreManager scoreManager;
    private Profile profile;
    public List<Product> products= new ArrayList<>();

    public AuthenticatedStudent(Integer userId, String name, String email, String password) {
        super(userId, name, email, password);
        this.profile = new Profile(this);
    }

    public Profile getProfile() {
        return profile;
    }

    public void requestProduct(String productName) {
        System.out.println("Product requested by: " + this.getUserName());
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                System.out.println("Product found: " + product.getName());
                break;
            }
        }
    }


    public void postProduct(Product product) {
        System.out.println("Product posted: " + product.getName() + " by " + this.getUserName());
        products.add(product);
    }

    @Override
    public void update() {
        System.out.println("ProductCatalog has been updated");
    }

}