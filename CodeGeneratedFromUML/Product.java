
import java.io.*;
import java.util.*;

/**
 * 
 */
public abstract class Product {

    /**
     * Default constructor
     */
    public Product() {
    }

    /**
     * 
     */
    protected Integer productId;

    /**
     * 
     */
    protected AuthenticatedStudent owner;

    /**
     * 
     */
    protected String productName;

    /**
     * 
     */
    protected String productDesc;

    /**
     * 
     */
    protected String postedDate;

    /**
     * @return
     */
    public Integer getProductId() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public String getName() {
        // TODO implement here
        return "";
    }

    /**
     * @return
     */
    public String getDescription() {
        // TODO implement here
        return "";
    }

    /**
     * @return
     */
    public Integer getDuration() {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void viewDetails() {
        // TODO implement here
    }

    /**
     * @return
     */
    public AuthenticatedStudent getOwner() {
        // TODO implement here
        return null;
    }

}