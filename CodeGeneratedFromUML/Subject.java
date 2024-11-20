
import java.io.*;
import java.util.*;

/**
 * 
 */
public interface Subject {

    /**
     * @param observer
     */
    public void registerObserver(Observer observer);

    /**
     * @param observer
     */
    public void removeObserver(Observer observer);

    /**
     * 
     */
    public void notifyObserver();

}