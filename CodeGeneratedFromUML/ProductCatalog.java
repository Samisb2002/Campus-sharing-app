import java.util.ArrayList;
import java.util.List;

public class ProductCatalog implements Subject {
    private List<Product> products;
    private List<Observer> observers;

    public ProductCatalog() {
        products = new ArrayList<>();
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) { 
            observer.update();
        }
    }


    public void addProduct(Product product) {
        products.add(product);
        System.out.println("Product ajouté : " + product.getName());
        notifyObservers();
    }

    public void removeProduct(Product product) {
        products.remove(product);
        System.out.println("Product supprimé : " + product.getName());
        notifyObservers();
    }


    public void viewAvailableProducts() {
        System.out.println("Liste des produits disponibles :");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public List<Product> filterByCategory(String category) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getDescription().contains(category)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    public Product getProductById(int productId) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null;
    }
}

