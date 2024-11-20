
import java.io.*;
import java.util.*;

/**
 * 
 */
public class ProductCatalog implements Subject {

    /**
     * Default constructor
     */
    public ProductCatalog() {
    }

    /**
     * 
     */
    public List<Product> products;

    /**
     * 
     */
    public List<Observer> observers;

    /**
     * 
     */
    public void viewAvailableProducts() {
        // TODO implement here
    }

    /**
     * 
     */
    public void filterByCategory() {
        // TODO implement here
    }

    /**
     * @param product
     */
    public void addProduct(Product product) {
        // TODO implement here
    }

    /**
     * 
     */
    public void removeProduct() {
        // TODO implement here
    }

    /**
     * @param productId
     */
    public void getProductById(integer productId) {
        // TODO implement here
    }

    /**
     * @param observer
     */
    public void registerObserver(Observer observer) {
        // TODO implement Subject.registerObserver() here
    }

    /**
     * @param observer
     */
    public void removeObserver(Observer observer) {
        // TODO implement Subject.removeObserver() here
    }

    /**
     * 
     */
    public void notifyObserver() {
        // TODO implement Subject.notifyObserver() here
    }

}